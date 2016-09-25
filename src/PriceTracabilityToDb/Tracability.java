package PriceTracabilityToDb;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import javax.management.timer.Timer;

import DAOperations.DAO;
import Generate_Random.Generate;
import UserToDb.to_db;

public class Tracability {
	
	public static void TraceToDb(int id_product , float price , String date , int IdStore , Boolean Promotion) throws Exception
	{
		String sql = "INSERT INTO PRICE_TRACABILITY VALUES (default, ? , ?  , ? , ? , ? ) ";
		java.sql.PreparedStatement st = to_db.conn.prepareStatement(sql);
		st.setInt(1, id_product);
		st.setFloat(2, price);
		st.setString(3, date);
		st.setInt(4, IdStore);
		int promo = Promotion ? 1 : 0 ;
		st.setInt(5, promo);
		st.executeUpdate();
		
		
	}
	
	public static void InsertOneTrace(int id_product , float supValue , String dateOfTrace , int IdStore , Boolean Promotion ) throws Exception
	{
		TraceToDb(id_product, DAO.getPriceByIdProduct(id_product) + supValue , dateOfTrace, IdStore, Promotion);
	}
	
	

	public static void toManyTracability(int nombre_trace , int startDate , int endDate) throws Exception
	{
		int maxIdProduct = DAO.getMaxProductSalerID() ;
		int maxIdStore = DAO.getMaxIdStore() ;
		int IdProduct ;

		
		LinkedList<String> liOfDate = Generate.getTooManyDateFormatDateTooSql(nombre_trace, startDate, endDate);
		int sizeOfDate  = liOfDate.size(); 
		Boolean promo = true;
		while (nombre_trace-- > 0)
		{
			IdProduct = Generate.Between(1, maxIdProduct) ;
			promo = nombre_trace % 25 == 0 ? true : false ;
			// Recuper the price of that product 
			InsertOneTrace(IdProduct , DAO.getPriceByIdProduct(IdProduct) + Generate.BetweenFloat(0, 5) , liOfDate.get( Generate.Between(0,sizeOfDate-1) ) , Generate.Between(1, maxIdStore) ,  promo  );
			
		}
		
	}
	

}
