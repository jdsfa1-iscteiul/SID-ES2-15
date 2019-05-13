package utilities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Measurement {

	private int measurementId, cultureId, variableId;

	private String cultureName, cultureDescription, variableName, timestamp;

	private float variableUpperBound, variableLowerBound, valueMeasured;

	//	public Measurement(String variableName, String timestamp, String valueMeasured) {
	//		this.variableName = variableName;
	//		this.timestamp = timestamp;
	//		this.valueMeasured = Float.parseFloat(valueMeasured);
	//	}

	public Measurement(ResultSet object) throws SQLException {
		measurementId = object.getInt("measurement_id");
		cultureId = object.getInt("culture_id");
		variableId = object.getInt("variable_id");
		cultureName = object.getString("culture_name");
		cultureDescription = object.getString("culture_description");
		variableName = object.getString("variable_name");
		timestamp = object.getString("timestamp");
		variableUpperBound = object.getFloat("upper_bound");
		variableLowerBound = object.getFloat("lower_bound");
		valueMeasured = object.getFloat("value");


		//	this.timestamp = timestamp;
		//	this.valueMeasured = Float.parseFloat(valueMeasured);
	}


	public String getTimestamp() {
		return timestamp;
	}

	public float getValueMeasured() {
		return valueMeasured;
	}

	public String getVariableName() {
		return variableName;
	}	

	public String getCultureDescription() {
		return cultureDescription;
	}

	public int getCultureId() {
		return cultureId;
	}

	public String getCultureName() {
		return cultureName;
	}

	public int getMeasurementId() {
		return measurementId;
	}

	public int getVariableId() {
		return variableId;
	}

	public float getVariableLowerBound() {
		return variableLowerBound;
	}

	public float getVariableUpperBound() {
		return variableUpperBound;
	}

	@Override
	public String toString() {
		return variableName + ": { valor medido: " + valueMeasured + " }  -  " + timestamp;
	}
}
