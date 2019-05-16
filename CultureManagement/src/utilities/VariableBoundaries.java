package utilities;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.mysql.DatabaseObject;

public class VariableBoundaries extends DatabaseObject{
	
	private Culture culture;
	private Variable variable;
	private float lowerBound;
	private float upperBound;
	
	public VariableBoundaries(ResultSet result) {
		super();
		try {
			culture = new Culture(result);
			variable = new Variable(result);
			lowerBound = result.getFloat("lower_bound");
			upperBound = result.getFloat("upper_bound");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public VariableBoundaries(Culture culture, Variable variable, float lowerBound, float upperBound) {
		super();
		this.culture = culture;
		this.variable = variable;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	public float getLowerBound() {
		return lowerBound;
	}

	public float getUpperBound() {
		return upperBound;
	}

	public Culture getCulture() {
		return culture;
	}

	public Variable getVariable() {
		return variable;
	}
	
	public void setUpperBound(float upperBound) {
		this.upperBound = upperBound;
	}
	
	public void setLowerBound(float lowerBound) {
		this.lowerBound = lowerBound;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		else {
			VariableBoundaries aux = (VariableBoundaries)obj;
			if(aux.culture.equals(this.culture) && aux.variable.equals(this.variable))
				return true;
			return false;
		}
	}
	
}
