/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BuildTicketText;

import DAOperations.ConnectionTickets;
import DAOperations.DAO;
import Generate_Random.Generate;
import ProductSaler.Product;
import UserToDb.to_db;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import SalerStoreScraping.Store;
import SalesToDb.SalesToDb;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Struct;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import Service.* ;
import TicketToDb.Ticket;
import TicketToDb.TicketToDb;
import java.text.SimpleDateFormat;

/**
 *
 * @author l.IsSaM.l
 */
public class TicketOperations {
    
   static int numbreticket = 1 ;

   public static String buildTicket_MARJANE(Boolean Head , Boolean Tail , String salerName, String storeName, String dateTicket, Map<String, ArrayList<StructProductTicket>> ProductOfCategories) throws Exception {
       
        Map<Integer , Float>  mapOfClassTVa  = new TreeMap<Integer , Float> () ;
        int numberArticle = 0 ;
         DecimalFormat df = new DecimalFormat();
	 df.setMaximumFractionDigits(2);
        float totalTicket = 0, totalCategorie = 0;
        String Sortie_OCR = "";
        
        
        Ticket T  = new Ticket(dateTicket, DAO.getId_storeByName(storeName), Generate.Between(DAO.numberMIN() ,DAO.numberOfUser()));
        TicketToDb.add_ticket(T);
        int id_ticket = DAO.getMaxTicket();
        
        
        if ( Head )
        {
        
        String HeadSaler ="0 "+ salerName + "\n0 " + salerName + " HOLDING\n0 " +storeName + "\n";
        HeadSaler += "0 GASOIL <8.03 K-8 CTS> \n0 SUPER <9.02 K-9 CTS> \n";
        int S1 = (int) (Math.random() * 100);
        HeadSaler +="2 "+ dateTicket + " " + S1 + " " + (S1 + 12) + " " + S1 * 100+"\n";
        Sortie_OCR += HeadSaler;
        }
        String Categorie = "";
        ArrayList<StructProductTicket> listOfProduct = new ArrayList<StructProductTicket>();
        String Describe_Product = "";
        int classTVA = 0;
       // Set<Integer> setClassTva = new TreeSet<Integer>();
        // Boucler pour les categories 
        for (Map.Entry<String, ArrayList<StructProductTicket>> entry : ProductOfCategories.entrySet()) {
            totalCategorie = 0;
            Categorie = entry.getKey();
            listOfProduct = entry.getValue();
            for (StructProductTicket Prod : listOfProduct) {
                classTVA = DAO.getClassTvaByNameProduct(Prod.nameProduct);
                // hust for verify if was an promotion of qte  
                if( mapOfClassTVa.containsKey(classTVA))
                    mapOfClassTVa.put(classTVA,    mapOfClassTVa.get(classTVA) + Prod.price * Prod.qte );
                else 
                    mapOfClassTVa.put(classTVA,Prod.price * Prod.qte ) ;
                
                Prod.price = Service.round(Prod.price, 2);             
                Prod.price = Prod.price *  ( DAO.getTauxTvaByClass(classTVA)/100 )  +  Prod.price;
                Prod.price = Service.round(Prod.price, 2);
             
                            
               //  System.out.println("  PRICE   : "+Prod.price);           
                Describe_Product = "1 (" + classTVA + ")" +Prod.toString() ;
                
               
                Sortie_OCR += Describe_Product + "\n";
                totalCategorie += Prod.price * Prod.qte ;
                
                SalesToDb.addToOperations(DAO.getIdProductByName(Prod.nameProduct) , id_ticket, Prod.qte);
                numberArticle++ ;
            }
           	
            Sortie_OCR +="0 " + Categorie +" " + df.format(totalCategorie) + "\n";
            totalTicket += totalCategorie;

        }
        
     //   System.out.println(setClassTva);
        
        /*
        Ici je boucle sur les classes par Set setClassTva et à chaque class je fait appel à la fonction getTauxTvaByClass(setClassTva.get(i)) pour récuperer le taux
        */
        
         if ( Tail )
        {
        String TailSaler = "0 TOTAL  "+df.format(totalTicket) ;
        TailSaler += "\n0 DONT DROITS DE TIMBRE 0.21 \n";
        TailSaler += "0 ESPECES " + ((int) totalTicket + 50) + "\n0 DROITS DE TIMBRE 0.21 \n0 RENDU 50\n";
        TailSaler += "0 NOMBRE D'ARTICLES "+numberArticle+ "\n0 TVA POUR INFORMATION \n";
        TailSaler += "0 R.    Tx    HT     TAXE     TTC \n";
       String TvaCalculation ="" ;
       float totalHT = 0 , totalTaxe = 0 ;
       
        for ( Map.Entry<Integer , Float > entry : mapOfClassTVa.entrySet() ) 
        {
            
            int idClass = entry.getKey() ;
            float tauxTVA = DAO.getTauxTvaByClass(entry.getKey()) ;
            float HT = Service.round(entry.getValue(), 2);
            float TAXE =    Service.round( tauxTVA /100  * HT, 2)  ;
            float TTC =Service.round( HT + TAXE , 2 )  ; 
            
            TvaCalculation += "4 "+idClass + "  " + tauxTVA + "  " + HT + "  " + TAXE  + "  "+TTC + " \n"; 
            totalTaxe +=TAXE ;
            totalHT +=HT ;
         }
        TailSaler += TvaCalculation ;
        TailSaler += "0 total   " + df.format(totalHT) + " " +df.format(totalTaxe) + "   " +df.format(totalTicket);
        TailSaler += "\n0 PENSEZ A RECHARGER VOTRE CARTE INWI \n0 DECOUVREZ NOS OFFRES PROMOTIONNELLES SUR \n0 www." + salerName + ".co.ma\n";
        TailSaler += "0 "+storeName;
        TailSaler += "\n0 MERCI DE VOTRE VISITE \n0 A BIENTOT\n";
        
        Sortie_OCR += TailSaler;
        }
       //  System.out.print(Sortie_OCR);
        return Sortie_OCR.replace(",", ".") ;

    }

   public static String buildTicket_CARREFOUR(Boolean Head , Boolean Tail , String salerName, String storeName, String dateTicket, Map<String, ArrayList<StructProductTicket>> ProductOfCategories) throws Exception {
       
        Map<Integer , Float>  mapOfClassTVa  = new TreeMap<Integer , Float> () ;
        int numberArticle = 0 ;
         DecimalFormat df = new DecimalFormat();
	 df.setMaximumFractionDigits(2);
        float totalTicket = 0, totalCategorie = 0;
        String Sortie_OCR = "";
        
         Ticket T  = new Ticket(dateTicket, DAO.getId_storeByName(storeName), Generate.Between(DAO.numberMIN(),DAO.numberOfUser()));
        TicketToDb.add_ticket(T);
        int id_ticket = DAO.getMaxTicket();
        
        
        if ( Head )
        {
        String HeadSaler ="0 CARREFOUR \n0 "+storeName+" \n0 Av. Rte "+DAO.villeOf(storeName)+" \n0 I.F:3397427  R.C:78457 \n0 ICE 000221005000014 \n0 Tel 0539889300 \n";
        int S1 = (int) (Math.random() * 100);
        HeadSaler +="2 "+ dateTicket + " " + S1*10 + " " + (S1 + 12) + " " + S1 * 100+"\n";
        Sortie_OCR += HeadSaler;
        }
        String Categorie = "";
        ArrayList<StructProductTicket> listOfProduct = new ArrayList<StructProductTicket>();
        String Describe_Product = "";
        int classTVA = 0;
       // Set<Integer> setClassTva = new TreeSet<Integer>();
        // Boucler pour les categories 
        for (Map.Entry<String, ArrayList<StructProductTicket>> entry : ProductOfCategories.entrySet()) {
            totalCategorie = 0;
            Categorie = entry.getKey();
            listOfProduct = entry.getValue();
            for (StructProductTicket Prod : listOfProduct) {
                classTVA = DAO.getClassTvaByNameProduct(Prod.nameProduct);
                // hust for verify if was an promotion of qte  
                if( mapOfClassTVa.containsKey(classTVA))
                    mapOfClassTVa.put(classTVA,    mapOfClassTVa.get(classTVA) + Prod.price * Prod.qte );
                else 
                    mapOfClassTVa.put(classTVA,Prod.price * Prod.qte ) ;
                
                Prod.price = Service.round(Prod.price, 2);             
                Prod.price = Prod.price *  ( DAO.getTauxTvaByClass(classTVA)/100 )  +  Prod.price;
                Prod.price = Service.round(Prod.price, 2);
             
                            
               //  System.out.println("  PRICE   : "+Prod.price);           
                Describe_Product = "1 (" + classTVA + ")" +Prod.toString() ;
                
               
                Sortie_OCR += Describe_Product + "\n";
                totalCategorie += Prod.price * Prod.qte ;
                 SalesToDb.addToOperations(DAO.getIdProductByName(Prod.nameProduct) , id_ticket, Prod.qte);
                numberArticle++ ;
            }
           	
            Sortie_OCR +="0 " + Categorie +" " + df.format(totalCategorie) + "\n";
            
            totalTicket += totalCategorie;

        }
        
     //   System.out.println(setClassTva);
        
        /*
        Ici je boucle sur les classes par Set setClassTva et à chaque class je fait appel à la fonction getTauxTvaByClass(setClassTva.get(i)) pour récuperer le taux
        */
        
         if ( Tail )
        {
        String TailSaler = "0 TOTAL  "+df.format(totalTicket) ;
        TailSaler += "\n0 CB MANUELLE "+df.format(totalTicket);  
        TailSaler += "\n0 NOMBRE D'ARTICLES "+numberArticle+ "\n0 TVA POUR INFORMATION \n";
        TailSaler += "0 N.    Tx    HT     TAXE     TTC \n";
       String TvaCalculation ="" ;
       float totalHT = 0 , totalTaxe = 0 ;
       
        for ( Map.Entry<Integer , Float > entry : mapOfClassTVa.entrySet() ) 
        {
            
            int idClass = entry.getKey() ;
            float tauxTVA = DAO.getTauxTvaByClass(entry.getKey()) ;
            float HT = Service.round(entry.getValue(), 2);
            float TAXE =    Service.round( tauxTVA /100  * HT, 2)  ;
            float TTC =Service.round( HT + TAXE , 2 )  ; 
            
            TvaCalculation += "4 "+idClass + "  " + tauxTVA + "  " + HT + "  " + TAXE  + "  "+TTC + " \n"; 
            totalTaxe +=TAXE ;
            totalHT +=HT ;
         }
        TailSaler += TvaCalculation ;
        TailSaler += "0 total   " + df.format(totalHT) + " " +df.format(totalTaxe) + "   " +df.format(totalTicket);
        TailSaler +="\n0 \t 9071921059423 \n";
        TailSaler += "0 "+storeName;
        TailSaler += "\n0 LES PRIX BAS, LA CONFIANCE EN PLUS \n";
        TailSaler += "0 A BIENTOT";
        Sortie_OCR += TailSaler;
        }
       //  System.out.print(Sortie_OCR);
        return Sortie_OCR.replace(",", ".") ;

    }

   
   
   public static String buildTicket_CARREFOUR_1(Boolean Head ,Boolean Tail , String salerName, String storeName, String dateTicket, Map<String, ArrayList<StructProductTicket>> ProductOfCategories) throws Exception {
       
         int numberArticle = 0 ;
         DecimalFormat df = new DecimalFormat();
	 df.setMaximumFractionDigits(2);
             
          
        float totalTicket = 0, totalCategorie = 0;
         String Sortie_OCR = "";
        if ( Head )
        {
           
        String HeadSaler ="0 BIENVENUE CHEZ \n0 CARREFOUR \n";
        HeadSaler +="0 ** "+ storeName + " ** \n" ;
        HeadSaler +="0 ** BIENVENUE ** \n";
        HeadSaler +="0 LUNDI AU SAMEDI DE 8h30 au 21h \n";
        Sortie_OCR += HeadSaler;
        }
        Sortie_OCR += "DESIGNATION    P.U x QTE    MONTANT \n";
        String Categorie = "";
        ArrayList<StructProductTicket> listOfProduct = new ArrayList<StructProductTicket>();
        String Describe_Product = "";
        int classTVA = 0;
        float totalpriceTVA = 0 ;
        for (Map.Entry<String, ArrayList<StructProductTicket>> entry : ProductOfCategories.entrySet()) {
        totalCategorie = 0;
        Categorie = entry.getKey();
        listOfProduct = entry.getValue();
        for (StructProductTicket Prod : listOfProduct) {
            classTVA = DAO.getClassTvaByNameProduct(Prod.nameProduct);
            Prod.price = Service.round(Prod.price, 2);           
            Describe_Product = classTVA + " "+Prod.describeCarrefour() ;
            Sortie_OCR += Describe_Product + "\n";
            numberArticle++ ; 
            totalCategorie += Prod.price ;
            
        }   
         Sortie_OCR +="0 TOTAL " + Categorie +" " + df.format(totalCategorie) + "\n";
         totalTicket += totalCategorie;
        
        }
        
        Sortie_OCR +="0 "+numberArticle+ " ARTICLES    TOTAL A PAYER  "+df.format(totalTicket)+" \n";
        
         if ( Tail )
        {
            
        String TailSaler ="0 £ Espèces "+((int) totalTicket + 50)+ "\n";
        TailSaler += "0 £ A RENDRE  50.00- \n";
        String nbre_ticket = new Integer(numbreticket).toString() ;
        while(nbre_ticket.length() < 4)
            nbre_ticket = "0"+nbre_ticket ; 
        TailSaler += "Carte CARREFOUR 9135720000051678"+nbre_ticket+ " \n";
        TailSaler += "Grace a vos achats \n\t"+numberArticle%20 +"\nVignette(s) Offerte(s) \n";
        TailSaler += "0    9135720000051678"+nbre_ticket +"\n";
        
        int S1 = (int) (Math.random() * 100);
        TailSaler +="2 "+ dateTicket + " " + S1*100 + " " + (S1 + 12) + " " + (S1+8) * 100+ " "+ S1*10 +"\n";
        TailSaler +="0 Les prix bas \n0 La confiance en plus";
        Sortie_OCR += TailSaler;
        }
        
        return Sortie_OCR.replace(",", ".") ;
   }
   
   
   
   public static String buildTicket_BIM(Boolean Head ,Boolean Tail , String salerName, String storeName, String dateTicket, Map<String, ArrayList<StructProductTicket>> ProductOfCategories) throws Exception {
       
        Map<Integer , Float>  mapOfClassTVa  = new TreeMap<Integer , Float> () ;
        int numberArticle = 0 ;
         DecimalFormat df = new DecimalFormat();
	     df.setMaximumFractionDigits(2);
             
          
         float totalTicket = 0;
         String Sortie_OCR = "";
         
         Ticket T  = new Ticket(dateTicket, DAO.getId_storeByName(storeName), Generate.Between(DAO.numberMIN(),DAO.numberOfUser()));
        TicketToDb.add_ticket(T);
        int id_ticket = DAO.getMaxTicket();
        
        
        if ( Head )
        {
            String nbre_ticket = new Integer(numbreticket).toString() ;
        while(nbre_ticket.length() < 4)
            nbre_ticket = "0"+nbre_ticket ; 
        String HeadSaler ="0 BIM STORES \n0 Magasin "+ storeName + "\n0 Route de " + storeName + " lotissement badiaa \n0 " +DAO.villeOf(storeName) + "\n";
        HeadSaler +="2 "+ dateTicket.substring(0,10) + " N° ticket: " + nbre_ticket + "\n";
        HeadSaler +="2 TIME: "+ dateTicket.substring(11)+"\n";
        Sortie_OCR += HeadSaler;
        }
        numbreticket++ ;
        String Categorie = "";
        ArrayList<StructProductTicket> listOfProduct = new ArrayList<StructProductTicket>();
        String Describe_Product = "";
        int classTVA = 0;
        float TauxTva = 0;
        float priceTva = 0 ;
        float totalpriceTVA = 0 ;
        for (Map.Entry<String, ArrayList<StructProductTicket>> entry : ProductOfCategories.entrySet()) {
        Categorie = entry.getKey();
        listOfProduct = entry.getValue();
        for (StructProductTicket Prod : listOfProduct) {
            Describe_Product = "";
            classTVA = DAO.getClassTvaByNameProduct(Prod.nameProduct);
            Prod.price = Service.round(Prod.price, 2); 
            TauxTva =  DAO.getTauxTvaByClass(classTVA) ;
            priceTva  =  Prod.price *  (  TauxTva/100 ) ;
            Prod.price = priceTva +  Prod.price;
            Prod.price = Service.round(Prod.price, 2);
            if (Prod.qte > 1)
            {  Describe_Product = "3 "+Prod.qte + " X " + df.format(Prod.price)+"\n" ;
              Prod.price *= Prod.qte ;
            }         
            Describe_Product += "1 "+Prod.nameProduct + " "+(int)TauxTva+"% " +df.format(Prod.price);
            Sortie_OCR += Describe_Product + "\n";
            totalTicket += Prod.price ;
            totalpriceTVA += priceTva ;
            numberArticle++ ; 
             SalesToDb.addToOperations(DAO.getIdProductByName(Prod.nameProduct) , id_ticket, Prod.qte);
            
        }   
        
        }
        
         if ( Tail )
        {
        String TailSaler = "0 TOTAL "+df.format(totalTicket)+ " DH " ; 
        TailSaler += "\n0 DROITS DE TIMBRE 0.04 DH " ; 
        TailSaler += "\n0 TOTAL TTC  "+df.format(totalTicket+0.04)+ " DH " ;
        TailSaler += "\n0 Dont TVA  "+ df.format(totalpriceTVA)+ " DH";
        TailSaler += "\n0 ESPECES " + ((int) totalTicket + 50)+ "\n0 RENDU 50 ";
        TailSaler += "\n0 NOMBRE ARTICLES  "+numberArticle +"\n";
        int S1 = (int) (Math.random() * 100);
        TailSaler += "0 M:"+S1*100+ " P:"+S1*11+ " C:"+5+ " Mr.jamal\n";
        TailSaler += "0 MERCI DE VOTRE VISITE ET A BIENTOT ....\n";
        TailSaler += "0 PATENTE:29472737 Id.Fiscal:1108770 \n0 R.C.No :8633 CNSS :7764987 \n0 ICE:001527053000001";
        Sortie_OCR += TailSaler;
        }
        
        return Sortie_OCR.replace(",", ".") ;
   }

   
   
   public static void buildToManyticket(String SALERNAME ,int startYear , int endYear , int numberTicket , float ProbawithOutHead ,float ProbawithOutTail , float ProbawithOutHeadAndTail , int toDatabase , int startNbreCategorie , int endNbreCategorie , int  startNbreProduct , int  endNbreProduct  ) throws Exception
    {
        float Intervalle = ProbawithOutHead + ProbawithOutTail + ProbawithOutHeadAndTail ;
         Map<String, ArrayList<StructProductTicket>> ProductOfCategories = new HashMap<String, ArrayList<StructProductTicket>>(); 
        String[] salerName = { "MARJANE" ,"BIM" , "CARREFOUR" , "ACIMA" } ;
        String storeName ;
        int choiceCenter  ;
        String nameOfSaler = "";
        String UniqueCat;
        int nbr_prod_cat;
        LinkedList<String> list0fCat = DAO.AllCategorie();
        LinkedList<Product> liProduct = new LinkedList<Product>() ;
        ArrayList<StructProductTicket> ProdInTicket = new ArrayList<StructProductTicket>();
        int qte = 1 , J =1;
        int bc_promo = 0;
        Boolean Promo = true ;
        StructProductTicket p ;
        int nbr_categorie ;
        LinkedList<String> lis_date = Generate.getTooManyDateFormatDateTooSql(numberTicket, startYear, endYear);
                while (numberTicket -- > 0 )
        {
         //   System.out.println(" Ticket N° : "+numberTicket + " __________________________   :");
           // remove the map 
            
            
            // head 
         //   choiceCenter = Generate_Random.Generate.Between(1,4);
             choiceCenter = Service.indiceFromTable(salerName, SALERNAME) + 1 ; 
             
             if(choiceCenter == 0 )
                 System.out.println(" we haven't this saler in our data sorry ! ");
            Store store = DAO.getStoresBySaler(choiceCenter).get( Generate.Between(0,DAO.getStoresBySaler(choiceCenter).size()-1 ));
        //    nameOfSaler = salerName[choiceCenter - 1] ;
            nameOfSaler = salerName[choiceCenter - 1] ;
            //Categories and products
            nbr_categorie = Generate.Between(startNbreCategorie,endNbreCategorie);
            for (int i = 0; i < nbr_categorie; i++) {
            list0fCat = DAO.AllCategorie();
            UniqueCat = DAO.returnUniqueCat(list0fCat);
            nbr_prod_cat = Generate.Between(startNbreProduct, endNbreProduct);
            liProduct = DAO.ProdByCat(UniqueCat, nbr_prod_cat); 
            
            // Convert from product simple to product struct with qte and promotion
            
            for (Product P : liProduct)
            {
                qte = Math.random() > 0.85 ? Generate.Between(2, 10) : 1 ;
                Promo = bc_promo++ % 20 == 0 ? true : false  ;          
               p = new StructProductTicket(P.code, P.label, qte, Promo, P.price);
                 ProdInTicket.add(p);
            }
            ProductOfCategories.put(UniqueCat, Service.clone(ProdInTicket));
            ProdInTicket.clear();
            }
           
              

            float TakeProba = (float)Math.random();
            String Sotrie_OCR = "" ;
            if ( choiceCenter == 1)
            {
            if ( TakeProba > Intervalle )
            Sotrie_OCR = buildTicket_MARJANE(true , true , nameOfSaler, store.store_name, lis_date.get(numberTicket), ProductOfCategories);
            if ( TakeProba <= ProbawithOutHead )
                 Sotrie_OCR = buildTicket_MARJANE(false , true , nameOfSaler, store.store_name, lis_date.get(numberTicket), ProductOfCategories);
             if ( TakeProba < ProbawithOutTail+ProbawithOutHead && TakeProba > ProbawithOutHead )
                 Sotrie_OCR = buildTicket_MARJANE(true , false , nameOfSaler, store.store_name, lis_date.get(numberTicket), ProductOfCategories);
             if ( TakeProba <= Intervalle && TakeProba > ProbawithOutTail+ProbawithOutHead )
                 Sotrie_OCR = buildTicket_MARJANE(false , false , nameOfSaler, store.store_name, lis_date.get(numberTicket), ProductOfCategories);
           // Remove the List fom the product of this categorie And the Map
            }
             if ( choiceCenter == 2)
            {
            if ( TakeProba > Intervalle )
            Sotrie_OCR = buildTicket_BIM(true , true , nameOfSaler, store.store_name, lis_date.get(numberTicket), ProductOfCategories );
            if ( TakeProba <= ProbawithOutHead )
                 Sotrie_OCR = buildTicket_BIM(false , true , nameOfSaler, store.store_name, lis_date.get(numberTicket), ProductOfCategories);
             if ( TakeProba < ProbawithOutTail+ProbawithOutHead && TakeProba > ProbawithOutHead )
                 Sotrie_OCR = buildTicket_BIM(true , false , nameOfSaler, store.store_name, lis_date.get(numberTicket), ProductOfCategories );
             if ( TakeProba <= Intervalle && TakeProba > ProbawithOutTail+ProbawithOutHead )
                 Sotrie_OCR = buildTicket_BIM(false , false , nameOfSaler, store.store_name, lis_date.get(numberTicket), ProductOfCategories);
           // Remove the List fom the product of this categorie And the Map
            }
                if ( choiceCenter == 3)
            {
            if ( TakeProba > Intervalle )
            Sotrie_OCR = buildTicket_CARREFOUR(true , true , nameOfSaler, store.store_name, lis_date.get(numberTicket), ProductOfCategories );
            if ( TakeProba <= ProbawithOutHead )
                 Sotrie_OCR = buildTicket_CARREFOUR(false , true , nameOfSaler, store.store_name, lis_date.get(numberTicket), ProductOfCategories);
             if ( TakeProba < ProbawithOutTail+ProbawithOutHead && TakeProba > ProbawithOutHead )
                 Sotrie_OCR = buildTicket_CARREFOUR(true , false , nameOfSaler, store.store_name, lis_date.get(numberTicket), ProductOfCategories );
             if ( TakeProba <= Intervalle && TakeProba > ProbawithOutTail+ProbawithOutHead )
                 Sotrie_OCR = buildTicket_CARREFOUR(false , false , nameOfSaler, store.store_name, lis_date.get(numberTicket), ProductOfCategories);
           // Remove the List fom the product of this categorie And the Map
            }
            
            ProdInTicket.clear();
            ProductOfCategories.clear();
           
    
            
         //   System.out.println(Sotrie_OCR);
        
             if( toDatabase == 1 )
            	 DAO.InserIntoOCR(Sotrie_OCR.toUpperCase()); 
             else 
             
                
            	 createFile("TicketText_"+SALERNAME , SALERNAME+"_TICKET N° "+J,Sotrie_OCR );

            
            System.out.println("------------------------- Ticket N ° :" + J++  +" Insere ");
        }
                
               
    }
    
    
   
   
   
   
    public static void createFile(String chemin ,String nameFile , String remplie ) throws Exception
    {
    	
    	OutputStream fi = new FileOutputStream(chemin+"/"+nameFile+".txt");
    	PrintStream pr = new PrintStream(fi);
    	pr.print(remplie);
    	
    	
    }
    
    
    
    

}
