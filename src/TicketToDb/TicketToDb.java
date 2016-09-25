package TicketToDb;

import java.util.LinkedList;

import DAOperations.DAO;
import Generate_Random.Generate;
import SalerStoreScraping.Store;
import UserToDb.to_db;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketToDb {
	
	public static Ticket OneTicketToDb(int startDate , int endDate , String salerName ) throws Exception
	{
		
		// we have too many store in the same saler , so we pop one of them arbitry
		LinkedList<Store> li = DAO.getStoresBySaler(DAO.getId_salerByName(salerName)) ;
		
		int IdOfStore = li.get(Generate.Between(0,li.size()-1)).id_store ;
		
		Ticket tk = new Ticket(Generate.getTooManyDateFormatDateTooSql(1,startDate,endDate).get(0), IdOfStore , Generate.Between(DAO.numberMIN(),DAO.numberOfUser())) ;
		
       return tk ;
  
			  
			  
	 }
	
	 public static void add_ticket(Ticket T ) 
	   {
		   
		   try{
		   String sql = "INSERT INTO ticket VALUES (default, ? , ? , ? ) " ; 
		   java.sql.PreparedStatement st = to_db.conn.prepareStatement(sql) ;
		   st.setString(1, T.Date);
		   st.setInt(2,  T.id_store);
		   st.setInt(3, T.id_user);
		   st.executeUpdate() ;
                   }catch(Exception e)
                   {
                       
                   }
		  
	   }
	 
	 

		public static void addToManyTicket(int nbre_ticket , int startDate , int endDate , String nomSaler) throws Exception
		{
			
			
		Ticket tk ;
		
		for ( int i = 0 ; i < nbre_ticket ; i++ )
		{
			tk = TicketToDb.OneTicketToDb(startDate, endDate, nomSaler);
			add_ticket(tk);
			System.out.println(" Ticket N� :"+ (i+1));
		}
		
		
		}
	 
                 public static void UpdateDateOfTicket() 
        {
            try {
           String sql = "Select id_ticket from ticket ";
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
                java.sql.Statement st1 = to_db.conn.createStatement();
                LinkedList<String> toManyDate = Generate.getTooManyDateFormatDateTooSql(2001, 2010, 2015);
                int i = 0;
 		while (res.next()) {
			
			st1.executeUpdate("Update ticket set date ='"+toManyDate.get(i++)+ "' where id_ticket ='"+res.getInt(1)+ "' ; ");
                        System.out.println(" Update N : "+ i);
		}
            }catch(SQLException e )
            {
                System.out.println(e.getMessage());
            }
		
        }
	
	
	
	
	
		
	}
	
	
	


