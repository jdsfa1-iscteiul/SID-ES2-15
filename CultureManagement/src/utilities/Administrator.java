package utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.mysql.ClientConnectionHandler;
import database.mysql.DatabaseUser;

public class Administrator extends DatabaseUser {
	
	List<Researcher> researcherList = new ArrayList<Researcher>();
	
	List<Culture> cultureList = new ArrayList<Culture>();
	
	List<Variable> variableList = new ArrayList<Variable>();

	public Administrator(String username) {
		super(username);
		try {
			initializeCultureList();
			initializeVariableList();
			initializeResearcherList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Researcher> getResearcherList() {
		return researcherList;
	}

	public void setResearcherList(List<Researcher> researcherList) {
		this.researcherList = researcherList;
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

	private void initializeResearcherList() throws SQLException {
		ClientConnectionHandler.getInstance().prepareStatement("SELECT * FROM researcher");
		ClientConnectionHandler.getInstance().executeStatement();
		ResultSet results = ClientConnectionHandler.getInstance().getQueryResults();
		while(results.next())
			researcherList.add(new Researcher(results));
		
	}

	private void initializeCultureList() throws SQLException {
		ClientConnectionHandler.getInstance().prepareStatement("SELECT * FROM culture");
		ClientConnectionHandler.getInstance().executeStatement();
		ResultSet results = ClientConnectionHandler.getInstance().getQueryResults();
		while(results.next()) 
			cultureList.add(new Culture(results));
	}

	private void initializeVariableList() throws SQLException {
		ClientConnectionHandler.getInstance().prepareStatement("SELECT * FROM variable");
		ClientConnectionHandler.getInstance().executeStatement();
		ResultSet results = ClientConnectionHandler.getInstance().getQueryResults();
		while(results.next())
			variableList.add(new Variable(results));
	}
	
	@Override
	public String getUserType() {
		return "Administrator";
	}	

}
