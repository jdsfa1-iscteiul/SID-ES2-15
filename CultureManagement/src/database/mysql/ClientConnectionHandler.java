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

	private PreparedStatement currentStatement;
	
	private Measurement selectedMeasurement;

	public static ClientConnectionHandler getInstance() {
		if(instance == null)
			instance = new ClientConnectionHandler();
		return instance;
	}

	public Connection getConnection() {
		return dbConnection;
	}

	public void setDbConnection(Connection dbConnection, String username, String password) {
		this.dbConnection = dbConnection;
		try {
			executeStatement("Select current_role");
			ResultSet queryResults = currentStatement.getResultSet();
			queryResults.next();
			if(queryResults.getString("current_role").contains("administrator_role")) 
				user = new Administrator(username, password);
			else 
				user = new Researcher(username, password);
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

	public void resetClientConnection() {
		instance=null;
	}

	public void executeStatement(String sqlCommand) throws SQLException {
		currentStatement = dbConnection.prepareStatement(sqlCommand);
		currentStatement.execute();
	}

	public ResultSet getQueryResults() throws SQLException {
		return currentStatement.getResultSet();
	}

	public Measurement getSelectedMeasurement() {
		return selectedMeasurement;
	}

	public void setSelectedMeasurement(Measurement selectedMeasurement) {
		this.selectedMeasurement = selectedMeasurement;
	}

}
