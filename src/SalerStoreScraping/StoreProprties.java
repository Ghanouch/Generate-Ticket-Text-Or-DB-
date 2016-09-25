package SalerStoreScraping;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import UserToDb.User;
import UserToDb.to_db;

public class StoreProprties {
	
	
	public static LinkedList<Store> getAllcentresBIM() throws Exception
	{
		LinkedList<Store> li = new LinkedList<Store>();
			Document doc = Jsoup.parse(new FileInputStream("center_Bim.html"),"UTF-8","");

		Elements links = doc.select("select"); // select all li from ul

		for ( Element l : links)
		{
		//	System.out.println(" __________________________________Ville :" + i++ + " "+l.id());
			for ( Element Str : l.getAllElements().select("option"))
			{
			//	System.out.println(Str.text());
				li.add(new Store(2, Str.text(),l.id()));
			}
		    
				
			
				
		}
		return li ;
		
	}
		
		public static LinkedList<Store> getAllcentresCarrefour() throws Exception
		{
			LinkedList<Store> li = new LinkedList<Store>();
			Document doc = Jsoup.connect("http://www.carrefourmarket.ma/magasins.html").get();

			Elements links = doc.getElementsByClass("sidebar"); // select all li from ul

			for ( Element l : links)
			{
			   
			  //  System.out.println(new Store(3,l.text().split(":")[1], l.text().split(":")[0]));
			    li.add(new Store(3,l.text().split(":")[1], l.text().split(":")[0]));
			   
					
			}
		
		return li ;
				
		
	}
		
		public static LinkedList<Store> getAllcentresACIMA() throws Exception
		{
			LinkedList<Store> li = new LinkedList<Store>();
			for ( int i =1 ; i<=9 ; i++ )
			{
			Document doc = Jsoup.connect("http://www.acima.ma/nous-connaitre/implantation?page=0%2C0%2C0%2C0%2C0%2C0%2C0%2C0%2C0%2C0%2C"+i).get();

			Elements links = doc.getElementsByClass("TitreActicle2"); // select all li from ul
			for ( Element l : links)
			{
			   
			//   System.out.println(new Store(4,l.text(), l.text().substring(6)));
			    li.add(new Store(4,l.text(), l.text().substring(6)));
			   
					
			}
			}
		return li ;
				
		
	}
	
	public static LinkedList<String> getAllcentresMarjane() throws Exception
	{
		LinkedList<String> listeMarjane = new LinkedList<String>() ;
		Document doc = Jsoup.connect("https://fr.wikipedia.org/wiki/Marjane_Holding").get();
		Elements links = doc.select("li"); // select all li from ul

		for ( Element l : links)
			
			if( l.text().startsWith("Hyper"))
			{
				
				String str =   l.text().contains("(") ? l.text().substring( 0, l.text().indexOf("(") ) : l.text() ;
				
				String filtre = str.contains("-") ? str.substring(0, 5) + str.substring(str.indexOf("-")+1) : str ;
				if( filtre.endsWith("Hyper ")) continue ;
				filtre = filtre.replaceAll("Hyper","HYPERMARCHE");
				listeMarjane.add(filtre);
			}
		
		return listeMarjane ;
	}
	
	public static LinkedList<Store> getMarjane() throws Exception
	{
		LinkedList<Store> li = new LinkedList<Store>();
		for ( String centre : getAllcentresMarjane())
		{
			li.add ( new Store(1, centre , "Casablanca"));
			
		}
		
		return li;
	}
	
	public static void add_Store(Store p) throws Exception {

		String sql = "INSERT INTO saler_store VALUES (default, ? , ?  , ? ) ";
		java.sql.PreparedStatement st = to_db.conn.prepareStatement(sql);
		st.setInt(1, p.id_saler);
		st.setString(2, p.store_name);
		st.setString(3, p.city);
		st.executeUpdate();

	}

	public static void add_all_Store(LinkedList<Store> li) throws Exception {
		int nbr_row = 0;


		for (Store P : li) {
			add_Store(P);
			System.out.println("Ligne insere : " + ++nbr_row);
		}

	}
	
	

}
