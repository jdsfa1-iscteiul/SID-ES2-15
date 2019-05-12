package utilities;

import database.mysql.DatabaseUser;

public class Administrator extends DatabaseUser {

	public Administrator(String username) {
		super(username);
	}

	@Override
	public String getUserType() {
		return "Administrator";
	}
	
	
	

}
