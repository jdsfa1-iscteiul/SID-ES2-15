package utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.mysql.ClientConnectionHandler;
import database.mysql.DatabaseUser;

public class Administrator extends DatabaseUser {

	List<Researcher> researcherList = new ArrayList<Researcher>();


	public Administrator(String username, String password) {
		super(username, password);
		try {
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

	private void initializeResearcherList() throws SQLException {
		ClientConnectionHandler.getInstance().executeStatement("SELECT * FROM researcher join mysql.user ON mysql.user.User=researcher.username_db");
		ResultSet results = ClientConnectionHandler.getInstance().getQueryResults();
		while(results.next())
			researcherList.add(new Researcher(results));
	}

	public void updateResearcherList() throws SQLException {
		initializeResearcherList();
	}

	@Override
	public String getUserType() {
		return "Administrator";
	}

	@Override
	public List<Culture> getCultureList() {
		try {
			ClientConnectionHandler.getInstance().executeStatement("CALL show_all_cultures");
			ResultSet results = ClientConnectionHandler.getInstance().getQueryResults();
			while(results.next())
				this.cultureList.add(new Culture(results));
		} 	
		catch (SQLException e) {
			e.printStackTrace();
		}
		return this.cultureList;
	}	

}
