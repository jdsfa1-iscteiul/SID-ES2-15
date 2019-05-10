package utilities;

import database.mysql.ConnectionMariaDB;

public class Context {
	
	private static Context single_instance = null; 
	
	public static Context getInstance() 
    { 
        if (single_instance == null) 
            single_instance = new Context(); 
  
        return single_instance; 
    }
	
	private ConnectionMariaDB conn;
	
	private String username;
	
	public void setConn(ConnectionMariaDB conn2) {
		this.conn=conn2;
	} 
	
	public ConnectionMariaDB getConn() {
		return conn;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
