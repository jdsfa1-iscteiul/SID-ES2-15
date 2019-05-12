package database.mysql;

public abstract class DatabaseUser {
	
	private String username;
	
	public DatabaseUser(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public abstract String getUserType();

}
