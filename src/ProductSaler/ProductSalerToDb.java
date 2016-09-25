package ProductSaler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.LinkedList;

import UserToDb.to_db;
import DAOperations.ConnectionTickets;
import Generate_Random.Generate;

public class ProductSalerToDb {
	
	public static LinkedList<Product> getProductAssociate() throws Exception {
		
		LinkedList<Product> li = new LinkedList<Product>() ;
		
        // this table trace_product is a table builded by associate table product and categories 
		String sql = "Select * from trace_product" ;
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		while (res.next()) {
			li.add(new Product(res.getInt(2) , res.getString(3) , res.getInt(5) , res.getFloat(4) , res.getString(1) ) ) ;
				//	.getFloat(4), res.getString(5)));

		}
		res.close();
		st.close();
	

		return li;

	
	}
	
	 public static void add_product(Product p ) throws Exception
	   {
		   
		   
		   String sql = "INSERT INTO product_saler VALUES (default, ? , ?  , ? , ? , ? , ?) " ; 
		   java.sql.PreparedStatement st = to_db.conn.prepareStatement(sql) ;
		   st.setInt(1, p.id_saler);
		   st.setInt(2, p.id_generic_product);
		   st.setString(3, p.label);
		   st.setInt(4, p.id_category_saler);
		   st.setFloat(5, p.price);
		   while ( p.code.length() < 13 )
			   p.code += Generate.Between(0,9);
		   st.setString(6, p.code);
		   
		   st.executeUpdate() ;
		  	  
	   }
	 
	 public static void addAllProduct(LinkedList<Product> li ) throws Exception
	 {
		 
		 
		 int i = 1 , j=1 ;
		 i = 0 ;
		 while ( ++i <= 4) 
		 {
		 for ( Product P : li )
		 {
			 P.id_saler = i ;
			 add_product(P);
			 
			 System.out.println(" Produit : "+ j++);
			 
		 }

		 
		 }
		 
		 
	 }
	
	


}
