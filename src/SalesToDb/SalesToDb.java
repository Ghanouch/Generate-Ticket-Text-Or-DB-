package SalesToDb;

import java.util.LinkedList;

import org.apache.xmlbeans.impl.jam.annotation.DefaultAnnotationProxy;

import DAOperations.DAO;
import Generate_Random.Generate;
import UserToDb.to_db;

public class SalesToDb {
	
	
	
	public static void ADD_ONE_TICKET(int numero_ticket , int numbreOfCategorie , LinkedList<Integer> listeOfCategorieID ) throws Exception
	
	{
		if ( numbreOfCategorie == 0 ) 
			System.exit(0);
		int IdCategorie = 0  ;
		int qte = 1 , i=1 ;
		
		while ( numbreOfCategorie-- > 0)
		{
		// Switch the numbre of categorie , we will choice this categories to put them into the ticket 
		  IdCategorie = DAO.returnUniqueCatID( listeOfCategorieID );
		 
		 // we will add for every category to many product 
		
		for ( int IdProduct : DAO.ProdByCat(IdCategorie, Generate.Between(1, 10))) 
		{
			qte = Math.random() > 0.90 ? Generate.Between(2, 7) : 1 ;
			addToOperations(IdProduct, numero_ticket <= 0 ? 1 : numero_ticket, qte ) ;
			System.out.println(" Sales Insere : "+ i++);
		}
		
		}
		 
		
		
	}
	
public static void ADD_ONE_TICKET(int numero_ticket , int numbreOfCategorie) throws Exception
	
	{
		if ( numbreOfCategorie == 0 ) 
			System.exit(0);
		int IdCategorie = 0  ;
		int qte = 1 , i=1 ;
		
		while ( numbreOfCategorie-- > 0)
		{
		// Switch the numbre of categorie , we will choice this categories to put them into the ticket 
		  IdCategorie = DAO.returnUniqueCatID( DAO.getIdOfCategories() );
		 
		 // we will add for every category to many product 
		
		for ( int IdProduct : DAO.ProdByCat(IdCategorie, Generate.Between(1, 10))) 
		{
			qte = Math.random() > 0.90 ? Generate.Between(2, 7) : 1 ;
			addToOperations(IdProduct, numero_ticket, qte ) ;
			System.out.println(" Sales Insere : "+ i++);
		}
		
		}
		 
		
		
	}
	
	
  public static void addToManySales(int number_Saler) throws Exception
  {
	  int maxTickets = DAO.getMaxTicketID() ;
	  
	  while ( number_Saler --  > 0 )
	  {
	  ADD_ONE_TICKET(Generate.Between(1,maxTickets), Generate.Between(1, 12) );
	  
	  }
	  
  }
	
	public static void addToOperations(int id_product_saler , int id_ticket , int qte)throws Exception
	
	{
            try{
		  String sql = "INSERT INTO SALES VALUES (default, ? , ? , ? ) " ; 
		   java.sql.PreparedStatement st = to_db.conn.prepareStatement(sql) ;
		   st.setInt(1, id_product_saler);
		   st.setInt(2,  id_ticket);
		   st.setInt(3, qte);
		   st.executeUpdate() ;
            }catch(Exception e)
            {
                
            }
		
	}

}
