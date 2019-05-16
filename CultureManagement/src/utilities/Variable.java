package utilities;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.mysql.DatabaseObject;

public class Variable extends DatabaseObject {
	
	//private static List<Variable> variables = new ArrayList<>();
	
	private int variableId;

	private String variableName;
	
	public Variable(ResultSet result) {
		super();
		try {
			variableId = result.getInt("variable_id");
			variableName = result.getString("variable_name");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Variable(int variableId, String variableName) {
		super();
		this.variableId = variableId;
		this.variableName = variableName;
	}
	
	public int getVariableId() {
		return variableId;
	}

	public String getVariableName() {
		return variableName;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		else {
			Variable aux = (Variable)obj;
			if(aux.variableId == this.variableId)
				return true;
			return false;
		}
	}
	
	@Override
	public String toString() {
		return variableName;
	}
	

}
