package utilities;

public class Variable {
	
	private int variableId;

	private String variableName;
	
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
	

}
