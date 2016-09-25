/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Strategie;

import DAOperations.DAO;
import static DAOperations.DAO.deletProductByProfession;
import static DAOperations.DAO.getAllIDticket;
import static DAOperations.DAO.getIdTicket;
import SalesToDb.SalesToDb;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author l.IsSaM.l
 */
public  class Buyer {
    
    
    public  static void achatCorrelation(String dateDebut , String dateFin , String nameProduct_1 , String nameProduct_2 , float correlation ) throws Exception
    {
        Scanner in = new Scanner(System.in);
        DAO.deleteAllProductbetween(dateDebut, dateFin , nameProduct_1 , nameProduct_2) ;
        while( correlation < -1 || correlation > 1 )
        {
            System.out.println(" Leave a correlation value that belongs to the interval [-1, 1] ");
             correlation = in.nextFloat();
        }

        int idProduct_1 = DAO.getIdProductByName(nameProduct_1);
        int idProduct_2 = DAO.getIdProductByName(nameProduct_2);
        LinkedList<Integer> li = DAO.getAllIDticket(dateDebut, dateFin);
        int lenghtOfList = li.size() ;
        int ProductIntersetion = ( int ) ( lenghtOfList * ( correlation < 0 ? ( 1 + correlation ) : correlation )  );
        int ProductNoIntersection = lenghtOfList - ProductIntersetion ;
        int i = 0 ;
        
        System.out.println(" Numbre Of all Tickets between "+dateDebut+ " AND "+dateFin +" : " +lenghtOfList);
        System.out.println(" Numbre Of ProductIntersection :"+ProductIntersetion);
         System.out.println(" Numbre Of ProductNoIntersection :"+ProductNoIntersection);
         
         System.out.println(" Id product_1 "+idProduct_1 + " id 2 : "+idProduct_2 );
            while ( ProductIntersetion-- > 0)
            {
             //   System.out.println(ProductIntersetion);
                SalesToDb.addToOperations(idProduct_1 , li.get(i) , 1);
                SalesToDb.addToOperations(idProduct_2  , li.get(i++), 1);
            }
            while ( ProductNoIntersection-- > 0)
            {
              SalesToDb.addToOperations( idProduct_1,li.get(i++) , 1);
            //  System.out.println(ProductNoIntersection);
            }
           
            System.out.println("  Numbre Of tickets Insere  : " + i );
        
        
    }
    
    
    
    public static void AddProductByMonth ( int month ,  ArrayList<Integer> liste ) throws Exception 

    {
        
        int nbre_max = DAO.getProdMaximiseSales(month); 
        
        int MAX =  nbre_max ; 
        LinkedList<Integer> li = DAO.getAllIDticket(month);
        
        int sizOflist = li.size() ;
        int i = 0 ;
        
       
           for ( Integer id_prod : liste ) 
           {
               while ( nbre_max -- > 0)
               
                                    SalesToDb.addToOperations(id_prod, li.get(i ++ % sizOflist) , 1);
               
               nbre_max =  MAX ;
        
           }
    }
    
     public static void setProductByProfession( int monthBegin , int monthEnd , String Profession , ArrayList<Integer> listOfProduct ) throws Exception
            
           {     
               deletProductByProfession(monthBegin, monthEnd, Profession);
               
               for ( Integer id_ticket : getIdTicket(monthBegin, monthEnd, Profession))
               {
                   for ( Integer id_product : listOfProduct)
                       SalesToDb.addToOperations(id_product, id_ticket, 1);
                   
               }
                  
               
           }
     
    public static void BasketAnalysis( ArrayList<Integer> listeOfProduct , Integer id_product , float proba  , int numbreTicket) throws Exception

    {
        
        int numbreOfCorrelation =  (int) (numbreTicket * proba );
        LinkedList<Integer> listOfticket = DAO.getIdTicket(numbreTicket); 
        
        for ( int i = 0 ; i < numbreTicket ; i++ , numbreOfCorrelation-- )
        {
            
            for ( Integer prod : listeOfProduct )
                SalesToDb.addToOperations(prod, listOfticket.get(i), 1);
            
            if ( numbreOfCorrelation > 0 )
                SalesToDb.addToOperations(id_product , listOfticket.get(i) , 1);
                 
        }
    }

}
