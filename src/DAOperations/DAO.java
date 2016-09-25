package DAOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import Generate_Random.Generate;
import SalerStoreScraping.Store;
import ProductSaler.Product;
import BuildTicketText.StructProductTicket;
import UserToDb.ExcelToJava;
import UserToDb.to_db;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DAO {
	
	public static int numberOfUser() throws Exception
	{
		
		String sql = " select MAX(id_user)from user ";
		java.sql.PreparedStatement st = to_db.conn.prepareStatement(sql);	
		ResultSet res = st.executeQuery() ;
		res.next() ;
		return res.getInt(1);
		
		
	}
        
        public static int numberMIN() throws Exception
	{
		
		String sql = " select MIN(id_user)from user ";
		java.sql.PreparedStatement st = to_db.conn.prepareStatement(sql);	
		ResultSet res = st.executeQuery() ;
		res.next() ;
		return res.getInt(1);
		
		
	}
        
        public static float getTauxTvaByClass(int classe) throws Exception
        {
            String sql = "Select RATE from TVA where ID_TVA = '" + classe +"' ; ";
		// System.out.println(sql);
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		res.next() ;
		return res.getInt(1);
        }

	public static LinkedList<Store> getStoresBySaler(int idSaler) throws Exception
	{
		LinkedList<Store> li = new LinkedList<Store>();
		String sql = "Select * from saler_store where id_saler = '" + idSaler +"' ; ";
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		while (res.next()) {
			
			li.add(new Store(res.getInt(1),res.getInt(2), res.getString(3), res.getString(4)));

		}
		res.close();
		st.close();
	

		return li;
	
	}
        
        public static LinkedList<String> getStoresNameBySaler(int idSaler) throws Exception
	{
		LinkedList<String> li = new LinkedList<String>();
		String sql = "Select store_name  from saler_store where id_saler = '" + idSaler +"' ; ";
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		while (res.next()) {
			
			li.add(new String (res.getString(1)) );

		}
		res.close();
		st.close();
	

		return li;
	
	}
	
	public static int getId_salerByName(String salerName )
	{
		int a = 0 ;
		try {
		String sql = "Select  id_saler from saler where saler_name = '" + salerName +"' ; ";
		// System.out.println(sql);
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		res.next() ;
		return res.getInt(1);
		
		}catch(SQLException e) {  System.out.println(e.getMessage());} 
		
	  return a ;
	}
        
        public static int getMaxTicket()
                {
		int a = 0 ;
		try {
		String sql = "Select  MAX(id_ticket) from ticket ";
		// System.out.println(sql);
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		res.next() ;
		return res.getInt(1);
		
		}catch(SQLException e) {  System.out.println(e.getMessage());} 
		
	  return a ;
	}
        
        public static int getId_storeByName(String storeName )
	{
		int a = 1 ;
		try {
		String sql = "Select  id_store from saler_store where store_name = '" + storeName +"' ; ";
		// System.out.println(sql);
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		res.next() ;
		return res.getInt(1);
		
		}catch(SQLException e) {  System.out.println(e.getMessage());} 
		
	  return a ;
	}
	
	public static int getMaxProductSalerID() throws Exception
	{
		String sql = "Select MAX(ID_PRODUCT_SALER) from PRODUCT_SALER where ID_SALER = 1  ";
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		res.next();
		return res.getInt(1);
	
	
	
	}
	
	public static LinkedList<Integer> getIdOfCategories() throws Exception
	{
		LinkedList<Integer> li = new LinkedList<Integer>();
		
		String sql = "Select DISTINCT ID_CATEGORY_SALER from PRODUCT_SALER ORDER BY ID_CATEGORY_SALER ASC  ";
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		while( res.next())
		{
			li.add(res.getInt(1));
		}
		return li;
		
		
	}
	
	public static int getMaxTicketID() throws Exception
	{
		String sql = "Select MAX(ID_ticket) from ticket ";
		
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		res.next();
		return res.getInt(1);
	
	
	
	}
	
	
	public static int returnUniqueCatID(LinkedList<Integer> li) {
		int Ind_Rand = Generate.Between(0, li.size() - 1);
		Integer Cat = new Integer(li.get(Ind_Rand));
		li.remove(Ind_Rand);
		return Cat;
	}
	
	
	public static LinkedList<Integer> ProdByCat(int IdCategorie, int nbreprod)
			throws Exception {

		LinkedList<Integer> li = new LinkedList<Integer>();
		String sql = "Select ID_PRODUCT_SALER from PRODUCT_SALER where ID_CATEGORY_SALER = '" + IdCategorie
				+ "' AND id_saler=1  LIMIT " + nbreprod;
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		while (res.next()) {
			
			li.add(res.getInt(1));

		}
		res.close();
		st.close();
	

		return li;
	}

        public static LinkedList<String> ProdNameByCat(int IdCategorie)
			throws Exception {

		LinkedList<String> li = new LinkedList<String>();
		String sql = "Select DISTINCT label from PRODUCT_SALER where ID_CATEGORY_SALER = '" + IdCategorie + "';";
				
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		while (res.next()) {
			
			li.add(res.getString(1));

		}
		res.close();
		st.close();
	

		return li;
	}
	
	public static float  getPriceByIdProduct(int IdProduct) 
	{
            try{
		String sql = "Select SELLING_PRICE from PRODUCT_SALER where ID_PRODUCT_SALER = '"+IdProduct+"';";
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		res.next();
		return res.getFloat(1);
            }catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
		return 80.55f;
	}
	
	public static int getMaxIdStore() throws Exception
	{
		String sql = "Select MAX(ID_STORE) from SALER_STORE ";
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		res.next();
		return res.getInt(1);
	}
        
        public static int getClassTvaByNameProduct(String nameProduct) 
                
        {
            ResultSet res = null;
            try{
            String sql = " select ID_TVA from generic_product  where generic_product_name = '"+nameProduct+"';";
		java.sql.PreparedStatement st = to_db.conn.prepareStatement(sql);	
		 res = st.executeQuery() ;
		res.next() ;
		return res.getInt(1);
            } catch( SQLException e) {  return 1; }
            
        }
	
        
        public static LinkedList<String> AllCategorie() throws Exception {
		LinkedList<String> li = new LinkedList<String>();
		String sql = "Select DISTINCT category_name from category_saler ";
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		while (res.next()) {
		
			li.add(res.getString(1));

		}
		res.close();
		st.close();
	

		return li;
	}
        
        public static String returnUniqueCat(LinkedList<String> li) {
		int Ind_Rand =Generate.Between(0, li.size() - 1);
		String Cat = new String(li.get(Ind_Rand));
		li.remove(Ind_Rand);
		return Cat;
	}
        
        
        	public static LinkedList<Product> ProdByCat(String Categorie, int nbreprod)
			throws Exception {

		LinkedList<Product> li = new LinkedList<Product>();
		String sql = "Select * from product_saler where id_category_saler = '" + getIdCategoryByName(Categorie) + "' AND id_saler=1 LIMIT " + nbreprod;
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		while (res.next()) {
			
			li.add(new Product(res.getInt(1) , res.getInt(2) ,res.getInt(3) ,res.getString(4), res.getInt(5) , res.getFloat(6) ,res.getString(7) ) ) ;
					

		}
		res.close();
		st.close();
	

		return li;
	}
                
                public static LinkedList<Product> ProdByCat(String Categorie)
			throws Exception {

		LinkedList<Product> li = new LinkedList<Product>();
		String sql = "Select * from product_saler where id_category_saler = '" + getIdCategoryByName(Categorie) + "';" ;
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		while (res.next()) {
			
			li.add(new Product(res.getInt(1) , res.getInt(2) ,res.getInt(3) ,res.getString(4), res.getInt(5) , res.getFloat(6) ,res.getString(7) ) ) ;
					

		}
		res.close();
		st.close();
	

		return li;
	}
                
                  public static LinkedList<String> getProdByCatName(String Categorie,int nbre)
			 {
try{
		LinkedList<String> li = new LinkedList<String>();
		String sql = "Select DISTINCT label from product_saler where id_category_saler = '" + getIdCategoryByName(Categorie) + "' AND id_saler=1 LIMIT " +nbre ;
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		while (res.next()) {
			
			li.add(res.getString(1) )  ;
					

		}
		res.close();
		st.close();
	

		return li;
}
catch(Exception e )
{
    e.printStackTrace();
}
return null;
	}
            
                
         public static int getIdCategoryByName( String nameCategory) throws Exception
         {
             String sql = "Select id_category_saler from category_saler where category_name = '"+nameCategory+"';";
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		res.next();
		return res.getInt(1);
         }
         
         public static LinkedList<String> getAllSaler()
         {
             LinkedList<String> li = new LinkedList<>();
             li.add("MARJANE");
             li.add("BIM");           
             li.add("CARREFOUR");          
             li.add("ACIMA");

             return li ;

         }
         
         public static StructProductTicket getProductStructByName ( String nameProduct)
         {
             try{
             
              StructProductTicket P = new StructProductTicket();

		String sql = "Select * from product_saler where label = '" + nameProduct + "' ; " ;
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
                
		res.next() ;
                String code = res.getString(7);
                
                float price =res.getFloat(6);
                
		int classTVA = getClassTvaByNameProduct(nameProduct);
                
                res.close();
		st.close();
		
                return new StructProductTicket(code, nameProduct, classTVA, price);
               
             } catch(Exception e ) {
                 
                  new StructProductTicket("0000000039259", "Mixed Proper", 1, false, DAO.getPriceByIdProduct(3));
               
                 }

           return  new StructProductTicket("0000000039259", "Mixed Proper", 1, false, DAO.getPriceByIdProduct(3));
         }
         
          
         public static void InserIntoOCR(String str ) throws Exception
     	{
     		
     		 String sql = "INSERT INTO ticket_format VALUES (default, ? , ? ) " ; 
     		 PreparedStatement st = to_db.conn.prepareStatement(sql);
     		   st.setString(1, str);
     		   st.setString(2,"");
     		   st.executeUpdate() ;
     	}
         
         
          public static String getCategoryByNameProduct(String nameProduct) 
                
        {
            ResultSet res = null;
            try{
            String sql = " select C.category_name from category_saler C , product_saler P   where P.id_category_saler = C.id_category_saler and id_saler=1 and  label = '"+nameProduct+"';";
		java.sql.PreparedStatement st = to_db.conn.prepareStatement(sql);	
		 res = st.executeQuery() ;
		res.next() ;
		return res.getString(1);
            } catch( SQLException e) {  return ""; }
            
        }
          
           public static String getSalerByNameStore(String nameStore) 
                
        {
            ResultSet res = null;
            try{
            String sql = " select S.saler_name from saler S , saler_store St   where St.id_saler = S.id_saler and  store_name = '"+nameStore+"';";
		java.sql.PreparedStatement st = to_db.conn.prepareStatement(sql);	
		 res = st.executeQuery() ;
		res.next() ;
		return res.getString(1);
            } catch( SQLException e) {  System.out.println(e.getMessage()); return ""; }
            
        }
           
           public static LinkedList<Integer> getAllIDticket(String dateDepart , String dateFin)  {

                 LinkedList<Integer> li = null ;
               try {
               li = new LinkedList<Integer>();
		String sql = "Select id_ticket from ticket where date > '"+dateDepart+ "' and date < '"+dateFin+"';";
                java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		while (res.next()) {
		
			li.add(res.getInt(1));
                   
		}
		res.close();
		st.close();
	return li;
               }catch( Exception e)
               {
                   e.printStackTrace();
               }
		
               
              return li; 
           }
           
            public static LinkedList<Integer> getIdTicket(int numbre_Ticket)  {

                 LinkedList<Integer> li = null ;
               try {
               li = new LinkedList<Integer>();
		String sql = "Select id_ticket from ticket  LIMIT " + numbre_Ticket;
                java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		while (res.next()) {
		
			li.add(res.getInt(1));
                   
		}
		res.close();
		st.close();
	return li;
               }catch( Exception e)
               {
                   e.printStackTrace();
               }
		
               
              return li; 
           }
           
            public static LinkedList<Integer> getAllIDticket(int month)  {

                 LinkedList<Integer> li = null ;
               try {
               li = new LinkedList<Integer>();
		String sql = "Select id_ticket from ticket where MONTH(date) = '"+month+"';";
                java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		while (res.next()) {
		
			li.add(res.getInt(1));
                   
		}
		res.close();
		st.close();
	return li;
               }catch( Exception e)
               {
                   e.printStackTrace();
               }
		
               
              return li; 
           }
           
           public static int getIdProductByName(String label ) 
           {
               try {
               String sql = "Select id_product_saler from product_saler where label = '" + label + "';" ;
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
                
		res.next() ;
                int id = res.getInt(1);
                return id ;
               }
               catch ( Exception e)
               {
                   System.out.println(e.getMessage());
                   return 1 ;
               }
           }
           
           public static void executeSql(String str )
           {
               try{
               java.sql.Statement st = to_db.conn.createStatement();
	 	int i = st.executeUpdate(str);
               }
               catch(Exception e) 
               {
                   System.out.println(e.getMessage());
               }
              //  System.out.println(" Ligne supprimé :"+i);
                
           }
           
           
           // We can remove all products between 2 date in table sales ( operation ) in order to control the correlation between these products 
           public static void deleteAllProductbetween(String dateDepart , String dateFin , String nameProduct_1 ,  String nameProduct_2) throws Exception
           {
               
               LinkedList<Integer> listOfIdTicket = getAllIDticket(dateDepart, dateFin);
               int idProduct_1 = getIdProductByName(nameProduct_1);
               int idProduct_2 = getIdProductByName(nameProduct_2);
               for ( int idTicket : listOfIdTicket)
                     executeSql("delete from sales where id_ticket = '"+idTicket+ "' and ( id_product_saler = '"+idProduct_1+ "' OR id_product_saler= '"+idProduct_2 +"' ); " );
              
               System.out.println(" Clean all relationShip between "+ nameProduct_1 + " AND " + nameProduct_2 );
           }
           
           public static String villeOf(String storeName) 
           {
               try{
                String sql = "Select city from saler_store where store_name = '" + storeName + "' ; " ;
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
                
		res.next() ;
                String ville = res.getString(1);
                return ville ;
               }
               catch(SQLException e)
               {
                   e.printStackTrace();
               }
               catch(Exception e)
               {
                   System.out.println(e.getCause());
               }
               
               System.out.println(" --------------- 7asasla "+storeName);
               return "Casablanca ";
           }
           
         // this function allows to show for each product sold, how many sales could rélaiser
           
           public static Map<Integer , Integer > SalesForEachProductSwithMonth(int month) throws Exception
           {
                Map<Integer , Integer > mapOccurProduct = new HashMap<Integer, Integer>();
                int occurence = 1 ;
                String sql = "select S.id_product_saler , S.quantity from sales S , ticket T  where T.id_ticket = S.id_ticket  AND MONTH( T.date )  = '"+month+"';";
                java.sql.Statement st = to_db.conn.createStatement();
                ResultSet res = st.executeQuery(sql);
                int id_product ;
                int qté ;
                while(res.next())
                {
                    id_product = res.getInt(1) ;
                    qté = res.getInt(2);
                    if ( mapOccurProduct.containsKey(id_product))
                        mapOccurProduct.put(id_product, mapOccurProduct.get(id_product)+ qté );
                    else 
                        mapOccurProduct.put(id_product , qté) ;
                }
                
                return mapOccurProduct;
           }
           
          // this function allow us to show the max of sales for the month , and the product could rélaiser this 
           public static int getProdMaximiseSales(int month) throws Exception
           {
                Map<Integer , Integer > mapOccurProduct = SalesForEachProductSwithMonth(month);
                int id_productMax = 0 ;
                int maxSales = 0 ;
                for (Map.Entry<Integer, Integer> entry : mapOccurProduct.entrySet()) 
                {
                    if ( entry.getValue() > maxSales)
                    {
                        maxSales = entry.getValue() ;
                        id_productMax = entry.getKey() ;
                    }
                   
                }
                
                
                System.out.println(" The most purchased product to month: : "+month+ " pcarries Identifier: :"+id_productMax+ "\n He bought "+maxSales+ " times");
                return maxSales ;
              
               
               
           }
           
                public static LinkedList<Integer> getIdTicket(int monthBegin , int monthEnd , String Profession)
			 {
try{
		LinkedList<Integer> li = new LinkedList<Integer>();
		String sql = "select T.id_ticket from Ticket T , user U where T.id_user = U.id_user AND month(T.date) >= '"+monthBegin+"' AND month(T.date) <= '"+monthEnd+"' AND U.profession = '"+Profession+"' ;"  ;
		java.sql.Statement st = to_db.conn.createStatement();
		ResultSet res = st.executeQuery(sql);
		while (res.next()) {
			
			li.add(res.getInt(1) )  ;
					

		}
		res.close();
		st.close();
	

		return li;
}
catch(Exception e )
{
    e.printStackTrace();
}
return null;
	}
           
           public static void deletProductByProfession( int monthBegin , int monthEnd , String Profession ) throws Exception
           {
               
                executeSql(" delete from sales where id_ticket IN ( select T.id_ticket from Ticket T , user U where T.id_user = U.id_user AND month(T.date) >= '"+monthBegin+"' AND month(T.date) <= '"+monthEnd+"' AND U.profession = '"+Profession+"' ) ; " );  
           }
           
           
          public static void RenitilaiseBD() throws Exception
          {
              
              
              executeSql("delete from sales");
              executeSql("delete from ticket");
              executeSql("delete from user");
              UserToDb.to_db.RemplirAllUser();
             
          }
                   
         
        
	
}
