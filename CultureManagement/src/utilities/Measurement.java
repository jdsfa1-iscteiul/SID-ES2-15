package utilities;

public class Measurement {
	
	private String variableName, timestamp;
	
	private float valueMeasured;
	
	public Measurement(String variableName, String timestamp, String valueMeasured) {
		this.variableName = variableName;
		this.timestamp = timestamp;
		this.valueMeasured = Float.parseFloat(valueMeasured);
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

	@Override
	public String toString() {
		return variableName + ": { valor medido: " + valueMeasured + " }  -  " + timestamp;
	}
}
