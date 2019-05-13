package database.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utilities.Administrator;
import utilities.Measurement;
import utilities.Researcher;

public class ClientConnectionHandler {

	private static ClientConnectionHandler instance = null;

	private Connection dbConnection;
	
	private DatabaseUser user;
	
	private DatabaseSystem system;

	//private String username;

	private PreparedStatement currentStatement;
	
	private Measurement selectedMeasurement;
	private ResultSet queryResults;

	//private String accountType;

	public static ClientConnectionHandler getInstance() {
		if(instance == null)
			instance = new ClientConnectionHandler();
		return instance;
	}

	public Connection getConnection() {
		return dbConnection;
	}

//	public String getUsername() {
//		return username;
//	}

	public void setDbConnection(Connection dbConnection, String username) {
		this.dbConnection = dbConnection;
		try {
			prepareStatement("Select current_role");
			executeStatement();
			queryResults.next();
			if(queryResults.getString("current_role").contains("administrator_role")) 
				user = new Administrator(username);
			else 
				user = new Researcher(username);
			system = new DatabaseSystem();
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}

	}
	
	public DatabaseUser getUser() {
		return user;
	}
	
	public DatabaseSystem getSystem() {
		return system;
	}

//	public void setUsername(String username) {
//		this.username = username;
//	}

//	public String getAccountType() {
//		return accountType;
//	}

	public void resetClientConnection() {
		instance=null;
	}


	public void prepareStatement(String sqlCommand) throws SQLException {
		currentStatement = dbConnection.prepareStatement(sqlCommand);
	}

	public void executeStatement() throws SQLException {
		currentStatement.execute();
		queryResults = currentStatement.getResultSet();
	}

	public ResultSet getQueryResults() {
		return queryResults;
	}

	public void resetQueryResults() {
		queryResults = null;
	}

	public Measurement getSelectedMeasurement() {
		return selectedMeasurement;
	}

	public void setSelectedMeasurement(Measurement selectedMeasurement) {
		this.selectedMeasurement = selectedMeasurement;
	}

}
