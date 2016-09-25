package UserToDb;

public class User {
	
	public int idUser ;
	public String name ;
	public String email ;
	public String birthday ;
	public String profession ;
	public String gender ;
	public User(int idUser, String name, String email, String birthday,
			String profession , String ge ) {
		this.idUser = idUser;
		this.name = name;
		this.email = email;
		this.birthday = birthday;
		this.profession = profession;
		this.gender = ge ;
	}
	public User(String name, String email, String birthday, String profession ,  String ge) {
		super();
		this.name = name;
		this.email = email;
		this.birthday = birthday;
		this.profession = profession;
		this.gender = ge ;
	}
	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", name=" + name + ", email=" + email
				+ ", birthday=" + birthday + ", profession=" + profession
				+ ", gender=" + gender + "]";
	}
	
	
	
	
	

}
