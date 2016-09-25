package GeneriqueProductToDb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedList;

import UserToDb.to_db;
import DAOperations.ConnectionTickets;
import Generate_Random.Generate;


public class ProductToDb {

	public static LinkedList<GenericProduct> getGeneriqueProduct(int nbre_product, int start_TVA , int end_TVA)
			throws Exception {
		ConnectionTickets.connect() ;
		LinkedList<GenericProduct> li = new LinkedList<GenericProduct>();
		String sql = "Select DISTINCT name from produit ";
		
		java.sql.Statement st = ConnectionTickets.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		while (res.next() && nbre_product-- >0 ) {
			
			if (res.getString(1).length() > 50)
				continue;
			li.add(new GenericProduct(res.getString(1),Generate.Between(start_TVA, end_TVA)));
		}
		
		res.close();
		st.close();
	

		return li;
	}
	
	public static void addGeneriqueProduct(LinkedList<GenericProduct> li ) throws Exception
	{
		int i =1 ;
	
		 for ( GenericProduct l : li)
		 {
		  String sql = "INSERT INTO generic_product VALUES (default,? , ? ,  , 1 ) " ; 
		   java.sql.PreparedStatement st = to_db.conn.prepareStatement(sql) ;
		   st.setString(1,l.name);
		   st.setFloat(2,l.tva);
		   st.executeUpdate() ;
		  System.out.println( i++  +" : ");
		 }
	
	
	 
	}
}
