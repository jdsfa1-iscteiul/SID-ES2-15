package database.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utilities.Culture;
import utilities.Variable;

public abstract class DatabaseUser extends DatabaseObject {
	
	private String username, password;
	
	protected List<Culture> cultureList = new ArrayList<Culture>();
	
	protected List<Variable> variableList = new ArrayList<Variable>();
	
	public DatabaseUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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
	
	public String getPassword() {
		return password;
	}
	private void initializeCultureList() throws SQLException {
		cultureList.clear();
		ClientConnectionHandler.getInstance().executeStatement("CALL show_all_cultures");
		ResultSet results = ClientConnectionHandler.getInstance().getQueryResults();
		while(results.next()) 
			cultureList.add(new Culture(results));
	}

	private void initializeVariableList() throws SQLException {
		variableList.clear();
		ClientConnectionHandler.getInstance().executeStatement("CALL show_all_variables");
		ResultSet results = ClientConnectionHandler.getInstance().getQueryResults();
		while(results.next())
			variableList.add(new Variable(results));
	}
	
	public void updateAllLists() throws SQLException {
		initializeVariableList();
		initializeCultureList();
	}
	
	public abstract List<Culture> getCultureList();

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
