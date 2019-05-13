package utilities;

public class LightMeasurement {
	private int measurementId;
	
	private float value;
	
	private String timestamp;

	public LightMeasurement(int measurementId, float value, String timestamp) {
		super();
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
