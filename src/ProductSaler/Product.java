package ProductSaler;

public class Product {
	
	public int id_product ;
	public int id_saler ;
	public int id_generic_product ;
	public String label ;
	public int id_category_saler ;
	public float price ;
	public String code ;
	@Override
	public String toString() {
		return "Product [id_product=" + id_product + ", id_saler=" + id_saler
				+ ", id_generic_product=" + id_generic_product + ", label="
				+ label + ", id_category_saler=" + id_category_saler
				+ ", price=" + price + ", code=" + code + "]";
	}
	
	
	
	public Product(int id_generic_product, String label,
			int id_category_saler, float price, String code) {
		super();
		this.id_saler = id_saler;
		this.id_generic_product = id_generic_product;
		this.label = label;
		this.id_category_saler = id_category_saler;
		this.price = price;
		this.code = code;
	}

    public Product(int id_product, int id_saler, int id_generic_product, String label, int id_category_saler, float price, String code) {
        this.id_product = id_product;
        this.id_saler = id_saler;
        this.id_generic_product = id_generic_product;
        this.label = label;
        this.id_category_saler = id_category_saler;
        this.price = price;
        this.code = code;
    }
        
        
        
        
	
	

}
