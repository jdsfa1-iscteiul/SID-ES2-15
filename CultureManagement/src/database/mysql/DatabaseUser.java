package database.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utilities.Culture;
import utilities.Variable;

public abstract class DatabaseUser {
	
	private String username, password;
	
	List<Culture> cultureList = new ArrayList<Culture>();
	
	List<Variable> variableList = new ArrayList<Variable>();
	
	public DatabaseUser(String username) {
		this.username = username;
		try {
			initializeCultureList();
			initializeVariableList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String getUsername() {
		return username;
	}
	
	private void initializeCultureList() throws SQLException {
		ClientConnectionHandler.getInstance().prepareStatement("CALL show_all_cultures");
		ClientConnectionHandler.getInstance().executeStatement();
		ResultSet results = ClientConnectionHandler.getInstance().getQueryResults();
		while(results.next()) 
			cultureList.add(new Culture(results));
	}

	private void initializeVariableList() throws SQLException {
		ClientConnectionHandler.getInstance().prepareStatement("CALL show_all_variables");
		ClientConnectionHandler.getInstance().executeStatement();
		ResultSet results = ClientConnectionHandler.getInstance().getQueryResults();
		while(results.next())
			variableList.add(new Variable(results));
	}
	
	public List<Culture> getCultureList() {
		return cultureList;
	}

	public void setCultureList(List<Culture> cultureList) {
		this.cultureList = cultureList;
	}

	public List<Variable> getVariableList() {
		return variableList;
	}

	public void setVariableList(List<Variable> variableList) {
		this.variableList = variableList;
	}

	public abstract String getUserType();

}
