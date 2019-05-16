package database.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utilities.LightMeasurement;
import utilities.TemperatureMeasurement;

public class DatabaseSystem {

	private List<LightMeasurement> lightMeasurements = new ArrayList<>();

	private List<TemperatureMeasurement> temperatureMeasurements = new ArrayList<>();

	private float lightLowerBound, lightUpperBound;

	private float temperatureLowerBound, temperatureUpperBound;

	public DatabaseSystem() {
		try {
			initializeVariables();
			initializeLists();
		}catch (SQLException exception){

		}
	}

	private void initializeLists() throws SQLException {
		ClientConnectionHandler.getInstance().executeStatement("SELECT * FROM light_measurements");
		ResultSet results = ClientConnectionHandler.getInstance().getQueryResults();
		
		while(results.next()) {
			LightMeasurement lightMeasurement = 
					new LightMeasurement(results.getInt("measurement_id"), results.getFloat("value"), results.getString("timestamp"));
			lightMeasurements.add(lightMeasurement);
		}
		ClientConnectionHandler.getInstance().executeStatement("SELECT * FROM temperature_measurements");
		results = ClientConnectionHandler.getInstance().getQueryResults();
		while(results.next()) {
			TemperatureMeasurement temperatureMeasurement = 
					new TemperatureMeasurement(results.getInt("measurement_id"), results.getFloat("value"), results.getString("timestamp"));
			temperatureMeasurements.add(temperatureMeasurement);
		}

	}

	private void initializeVariables() throws SQLException {
		ClientConnectionHandler.getInstance().executeStatement("SELECT * FROM system");
		ResultSet results = ClientConnectionHandler.getInstance().getQueryResults();
		results.next();
		lightLowerBound = results.getFloat("light_lower_bound");
		lightUpperBound = results.getFloat("light_upper_bound");
		temperatureLowerBound = results.getFloat("temperature_lower_bound");
		temperatureUpperBound = results.getFloat("temperature_lower_bound");	
	}

	public List<LightMeasurement> getLightMeasurements() {
		return lightMeasurements;
	}

	public void setLightMeasurements(List<LightMeasurement> lightMeasurements) {
		this.lightMeasurements = lightMeasurements;
	}

	public List<TemperatureMeasurement> getTemperatureMeasurements() {
		return temperatureMeasurements;
	}

	public void setTemperatureMeasurements(List<TemperatureMeasurement> temperatureMeasurements) {
		this.temperatureMeasurements = temperatureMeasurements;
	}

	public float getLightLowerBound() {
		return lightLowerBound;
	}

	public void setLightLowerBound(float lightLowerBound) {
		this.lightLowerBound = lightLowerBound;
	}
	
	public float getTemperatureLowerBound() {
		return temperatureLowerBound;
	}
	
	public void setTemperatureLowerBound(float temperatureLowerBound) {
		this.temperatureLowerBound = temperatureLowerBound;
	}
	
	public float getLightUpperBound() {
		return lightUpperBound;
	}
	
	public float getTemperatureUpperBound() {
		return temperatureUpperBound;
	}
	
	public void setLightUpperBound(float lightUpperBound) {
		this.lightUpperBound = lightUpperBound;
	}
	
	public void setTemperatureUpperBound(float temperatureUpperBound) {
		this.temperatureUpperBound = temperatureUpperBound;
	}
}
