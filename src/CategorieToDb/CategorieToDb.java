package CategorieToDb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import DAOperations.ConnectionTickets;
import UserToDb.to_db;

public class CategorieToDb {
	
	
	public static LinkedList<String> getAllCategories() throws Exception
	{
		
		ConnectionTickets.connect() ;
		LinkedList<String> li = new LinkedList<String>();
		String sql = "Select DISTINCT categorie from produit ";
		java.sql.Statement st = ConnectionTickets.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		while (res.next()) {
			if (res.getString("categorie").length() > 20)
				continue;
			li.add(res.getString("categorie"));

		}
		res.close();
		st.close();
	

		return li;
	}
	
	
	public static void addCategories(LinkedList<String> li ) throws Exception
	{
		int i =1 ;
		
		 for ( String l : li)
		 {
		  String sql = "INSERT INTO category_saler VALUES (default,? ) " ; 
		   java.sql.PreparedStatement st = to_db.conn.prepareStatement(sql) ;
		   st.setString(1,l);
		   st.executeUpdate() ;
		   System.out.println( i++  +" : "+l);
		 }
	
	
	
	}

}
