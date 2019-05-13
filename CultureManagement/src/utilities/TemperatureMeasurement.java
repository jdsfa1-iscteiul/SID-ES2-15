package utilities;

public class TemperatureMeasurement {
	private int measurementId;
	
	private float value;
	
	private String timestamp;

	public TemperatureMeasurement(int measurementId, float value, String timestamp) {
		this.measurementId = measurementId;
		this.value = value;
		this.timestamp = timestamp;
	}

	public int getMeasurementId() {
		return measurementId;
	}

	public float getValue() {
		return value;
	}

	public String getTimestamp() {
		return timestamp;
	}
	
	@Override
	public String toString() {
		return "Valor: " + value + " | Data: " + timestamp;
	}

}
