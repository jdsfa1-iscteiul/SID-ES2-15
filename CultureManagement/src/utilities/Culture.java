package utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.mysql.DatabaseObject;

public class Culture extends DatabaseObject {

	private int cultureId;

	private String cultureName, cultureDescription, researcher;

	private List<Variable> variablesList = new ArrayList<>();

	public Culture(ResultSet result) {
		super();
		try {
			cultureId = result.getInt("culture_id");
			cultureName = result.getString("culture_name");
			cultureDescription = result.getString("culture_description");
			researcher = result.getString("researcher");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Culture(String cultureName) {
		this.cultureName = cultureName;
	}

	public String getCultureName() {
		return cultureName;
	}

	public int getCultureId() {
		return cultureId;
	}

	public String getCultureDescription() {
		return cultureDescription;
	}

	public String getResearcher() {
		return researcher;
	}

	public void addVariableToList(Variable variable) {
		if(!variablesList.contains(variable))
			variablesList.add(variable);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		else {
			Culture aux = (Culture)obj;
			if(aux.cultureId == this.cultureId)
				return true;
			return false;
		}
	}

	@Override
	public String toString() {
		return cultureName;
	}
}
