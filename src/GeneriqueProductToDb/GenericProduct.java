package GeneriqueProductToDb;

public class GenericProduct {

	public String name ;
	public int tva ;
	
	
	public GenericProduct(String name, int tva) {
		super();
		this.name = name;
		this.tva = tva;
	}
	@Override
	public String toString() {
		return "GenericProduct [name=" + name + ", tva=" + tva + "]";
	}
	
	
}
