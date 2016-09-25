package TicketToDb;

public class Ticket {
	
	public String Date ;
	public int id_store ;
	public int id_user ;
	
	
	public Ticket(String date, int id_store, int id_user) {
		super();
		Date = date;
		this.id_store = id_store;
		this.id_user = id_user;
	}


	@Override
	
	public String toString() {
		return "Ticket [Date=" + Date + ", id_store=" + id_store + ", id_user="
				+ id_user + "]";
	}
	
	

}
