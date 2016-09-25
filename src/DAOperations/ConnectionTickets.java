package DAOperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTickets {

	
	public static Connection conn;

	public static Connection connect() throws SQLException {
		conn = null;
		try {
			String url = "jdbc:mysql://localhost:3306/ticket";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, "root", "");
			return conn;
		} catch (ClassNotFoundException e) {
			e.getCause();
			System.err.println("Le driver JDBC pour MYSQL est introuvable");
		} catch (SQLException e) {

			e.getCause();
			System.err
					.println("Un probleme de connexion est sourvenu, verifier la chaine de connexion : "
							+ e.getMessage());
		} finally {
			return conn;
		}
	}
}
