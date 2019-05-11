package database.mysql;

import java.sql.Connection;

public class ClientConnectionHandler {
	
	private static ClientConnectionHandler instance = null;
	
	private Connection dbConnection;
	
	private String username;
	
	public static ClientConnectionHandler getInstance() {
		if(instance == null)
			instance = new ClientConnectionHandler();
		return instance;
	}
	
	public Connection getConnection() {
		return dbConnection;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setDbConnection(Connection dbConnection) {
		this.dbConnection = dbConnection;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	

}
