
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Date;

import org.omg.PortableInterceptor.USER_EXCEPTION;

import CategorieToDb.CategorieToDb;
import DAOperations.DAO;
import Generate_Random.Generate;
import GeneriqueProductToDb.GenericProduct;
import GeneriqueProductToDb.ProductToDb;
import PriceTracabilityToDb.Tracability;
import ProductSaler.Product;
import ProductSaler.ProductSalerToDb;
import SalerStoreScraping.StoreProprties;
import SalesToDb.SalesToDb;
import TicketToDb.Ticket;
import TicketToDb.TicketToDb;
import BuildTicketText.StructProductTicket;
import static BuildTicketText.TicketOperations.* ;
import static DAOperations.DAO.executeSql;
import Strategie.*;
import UserToDb.ExcelToJava;
import UserToDb.User;
import UserToDb.to_db;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Commande {

    public static void main(String[] args) throws Exception {

        to_db.connect();
        
         //                               ----------------------    Generate Database   -------------------

			// Read names of people and generate birthay before inserting to Database 
         // to_db.add_all_user( ExcelToJava.extractUser("Names.xlsx" , 1970 , 1993) );
        
	   // Read all categories from ticket database and insert the result to Project_ocr database 	
        // CategorieToDb.addCategories( CategorieToDb.getAllCategories() );
        
	 // Add Generique product Name with TVA class de 1 � 7  :
       // ProductToDb.addGeneriqueProduct( ProductToDb.getGeneriqueProduct(3500,1,9) ) ;
				  // 		Add Store Of Marjane 
        //StoreProprties.add_all_Store(StoreProprties.getMarjane());
			     // Add Store Of BIM 
        //StoreProprties.add_all_Store(StoreProprties.getAllcentresBIM());
			   	 // Add Store Of Carrefour 
        // StoreProprties.add_all_Store( StoreProprties.getAllcentresCarrefour() );
		   	    // Add Store Of ACIMA 
        //StoreProprties.add_all_Store(  StoreProprties.getAllcentresACIMA() );
        
	 //Add One Ticket to Db 
        //TicketToDb.add_ticket(TicketToDb.OneTicketToDb(2015, 2016, "MARJANE"));
	//Add Too Many TIckets   TicketToDb.addToManyTicket(nbre_ticket, startDate, endDate, nomSaler);
        
	   //    TicketToDb.addToManyTicket(500, 2008 , 2016 , "MARJANE");
        //  TicketToDb.addToManyTicket(500, 2008 , 2016 , "BIM");	
        //  TicketToDb.addToManyTicket(500, 2008, 2016 , "CARREFOUR");
        //   TicketToDb.addToManyTicket(500, 2008, 2016 , "ACIMA");
        
        
    // Add Product after associate all product with there categories and codes 
        //	ProductSalerToDb.addAllProduct(ProductSalerToDb.getProductAssociate());
			// To Add Just One Operation ' One sales '      ,  1 parameter is number of ticket , 2 parameter is number of categories in this ticket , 3�me parameter list of Id categories 
        //  SalesToDb.ADD_ONE_TICKET(1, 5, DAO.getIdOfCategories());
        // SalesToDb.ADD_ONE_TICKET(2, 3);
			// To add to many operations 
        // SalesToDb.addToManySales(500);
			// INSERT ONE TRACE PRICE SWITCH MANY PARAMETER AND THE THE ADDITIF VALUE TO SELLING PRICE
		  //	Tracability.InsertOneTrace(1, 2.25f, new Date().toString(), 5 , false);
        // Tracability.InsertOneTrace(1, 3.25f, new Date().toString(), 5 , false);	
			// To many tracabilities 
       //  Tracability.toManyTracability(241, 2010, 2015);
     

			// To see all Constraints
        /* select table_name,column_name,referenced_table_name,referenced_column_name 
         *     from information_schema.key_column_usage 
         *         where referenced_table_name is not null 
         *           and table_schema = 'projet_ocr' 
         *              and table_name = 'sales'
         */
        
         //                               ----------------------    Ticket Forme Texte  -------------------
        
        // To generate One Ticket with all informations we wish 
        
   /*     Map<String, ArrayList<StructProductTicket>> Map = new HashMap<String, ArrayList<StructProductTicket>>();
        String Categorie = " filet-de-boeuf ";
        ArrayList<StructProductTicket> li = new ArrayList<>();
        li.add(new StructProductTicket("0000000024600", "Lion Peanut x2", 2, false, DAO.getPriceByIdProduct(2)));
        li.add(new StructProductTicket("0000000039259", "Twix x2", 2, true, DAO.getPriceByIdProduct(3)));
        Map.put(Categorie, li);
        String date = Generate_Random.Generate.getTooManyDateFormatDateTooSql(1, 2014, 2015).get(0); 
        System.out.println( buildTicket_MARJANE(true,true,"CARREFOUR", DAO.getStoresBySaler(3).get(2).store_name, date, Map) );
     
        */
        
        
     // Build To Many Tickets Arbitrary to Database 
         
        // DAO.RenitilaiseBD();
         
     //  BuildTicketText.TicketOperations.buildToManyticket("MARJANE" , 2010 , 2015 ,1000 , 0.15f , 0.15f , 0.2f , 0 , 1 , 7 , 1 , 7);
       
        // Build To Many Tickets Marjane Arbitrary to File Text
        //  BuildTicketText.TicketOperations.buildToManyticket("MARJANE" ,2000 , 2015 , 1000 , 0.15f , 0.15f , 0.2f ,  0 , 1 , 7 , 1 , 7);
        
         // Build To Many Tickets BIM  Arbitrary to File Text
        //  BuildTicketText.TicketOperations.buildToManyticket("BIM" , 2000 , 2015 , 1000 , 0.15f , 0.15f , 0.2f ,  0 , 1 , 7 , 1 , 7);
        
         // Build To Many Tickets CARREFOUR  Arbitrary to File Text
         // BuildTicketText.TicketOperations.buildToManyticket("CARREFOUR" , 2000 , 2015 , 1000 , 0.15f , 0.15f , 0.2f ,  0 , 1 , 7 , 1 , 7);
   
        
         
     //                               ----------------------    Strategy  -------------------
        
     //  DAO.deleteAllProductbetween("2004/12/12","2017/05/12","Sour Fruit Gummies","Jelly Fish");
       
      
        
//       Buyer.achatCorrelation("2014/12/12","2016/05/12","Sour Fruit Gummies","Jelly Fish", -0.60f);
        
       
        
     // ts function allows to show for each product sold, how many sales could rélaiser
//          Service.Service.showMapInteger(DAO.SalesForEachProductSwithMonth(1));
        
       // this function allow us to show the max of sales for the month , and the product could rélaiser this 
//             DAO.getProdMaximiseSales(3);
        
       
        // this function allow us to control the max of sales for each month , and wath's thSe product we wish maximise to this month 
//             ArrayList<Integer> liOfProduct = new ArrayList<>() ;
//            liOfProduct.add(  19);    
//             liOfProduct.add(  18);   
//            Buyer.AddProductByMonth(3, liOfProduct );
          
         // this function allow to specify for each profession , and between 2 month wath's the products they buy  
     //    Buyer.setProductByProfession(2, 6, "Student ", liOfProduct);
              
//       this function allow to attribute for each sale of liste Of products , one product ( Basket analysis ) with a probailité
//         Buyer.BasketAnalysis(liOfProduct, 20 , 0.6f, 500);
        
        
           to_db.conn.close();
          
              }
        
        
        
        

}
