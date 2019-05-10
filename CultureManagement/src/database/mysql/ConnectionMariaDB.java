package database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMariaDB {
	
	Connection conn = null;
	String dbName = "grupo15_main";

	public boolean connectToDB(String user, String password) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/" + dbName +
																"?user=" + user + "&password=" + password);
			
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
}
