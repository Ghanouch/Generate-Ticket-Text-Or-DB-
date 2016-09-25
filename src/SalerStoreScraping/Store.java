package SalerStoreScraping;

public class Store {
	
	public int id_store ;
	public Store(int id_store, int id_saler, String store_name, String city) {
		super();
		this.id_store = id_store;
		this.id_saler = id_saler;
		this.store_name = store_name;
		this.city = city;
	}

	public int id_saler ;
	public String store_name ;
	public String city ;
	@Override
	public String toString() {
		return "Store [id_saler=" + id_saler + ", store_name=" + store_name
				+ ", city=" + city + "]";
	}
	
	public Store(int id_saler, String store_name, String city) {
		super();
		this.id_saler = id_saler;
		this.store_name = store_name;
		this.city = city;
	}
	
	

}
