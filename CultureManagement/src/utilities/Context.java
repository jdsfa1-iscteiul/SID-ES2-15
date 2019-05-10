package utilities;

import java.sql.Connection;

public class Context {
	
	private static Context single_instance = null; 
	
	public static Context getInstance() 
    { 
        if (single_instance == null) 
            single_instance = new Context(); 
  
        return single_instance; 
    }
	
	private Connection conn;
	
	private String username;
	
	public void setConn(Connection conn2) {
		this.conn=conn2;
	} 
	
	public Connection getConn() {
		return conn;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
