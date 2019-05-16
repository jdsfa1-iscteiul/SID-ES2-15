package utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.mysql.ClientConnectionHandler;
import database.mysql.DatabaseUser;

public class Researcher extends DatabaseUser{
	
	private int employeeId;
	
	private String name, email, title;
	
	private Map<VariableBoundaries, List<Measurement>> measurementsData = new HashMap<>();
	
	private List<Culture> researcherCultureList = new ArrayList<>();
	
	private List<VariableBoundaries> variableBoundariesList = new ArrayList<>();
	
	private boolean receiveAlerts = true;
	
	public Researcher(String username, String password) {
		super(username, password);
		try {
			initializeVariables();
			initializeMeasurementsData();
			initializeResearcherCultureList();
			initializeVariableBoundariesList();
		} catch (SQLException e) {
		}		
	}
	
	private void initializeResearcherCultureList() throws SQLException {
		researcherCultureList.clear();
		ClientConnectionHandler.getInstance().executeStatement("CALL get_cultures()");
		ResultSet results = ClientConnectionHandler.getInstance().getQueryResults();
		while(results.next()) 
			researcherCultureList.add(new Culture(results));
	}

	private void initializeVariableBoundariesList() throws SQLException {
		variableBoundariesList.clear();
		ClientConnectionHandler.getInstance().executeStatement("CALL get_researcher_variable_boundaries()");
		ResultSet results = ClientConnectionHandler.getInstance().getQueryResults();
		while(results.next()) 
			variableBoundariesList.add(new VariableBoundaries(results));
	}
	
	private void initializeMeasurementsData() throws SQLException {
		measurementsData = new HashMap<VariableBoundaries, List<Measurement>>();
		ClientConnectionHandler.getInstance().executeStatement("CALL load_researcher_data()");
		ResultSet results = ClientConnectionHandler.getInstance().getQueryResults();
		while(results.next()) {
			VariableBoundaries vb = new VariableBoundaries(results);
			Measurement measurement = new Measurement(results);
			if(!measurementsData.containsKey(vb) || measurementsData.get(vb) == null) {
				List<Measurement> list = new ArrayList<>();
				list.add(measurement);
				measurementsData.put(vb, list);
			}
			else {
				measurementsData.get(vb).add(measurement);
			}
		}	
	}
	
	public void updateResearcherLists() throws SQLException {
		initializeResearcherCultureList();
		initializeMeasurementsData();	
		initializeVariableBoundariesList();
	}
	
	private void initializeVariables() throws SQLException {
		ClientConnectionHandler.getInstance().executeStatement("CALL get_researcher_bio()");
		ResultSet results = ClientConnectionHandler.getInstance().getQueryResults();
		results.next();
		employeeId = results.getInt("employee_id");
		name = results.getString("name");
		email = results.getString("email");
		title = results.getString("title");	
	}


	public Researcher(int employeeId, String name, String email, String username, String password, String title) {
		super(username, password);
		this.employeeId = employeeId;
		this.name = name;
		this.email = email;
		this.title = title;
	}
	
	public Researcher(ResultSet object) throws SQLException {
		super(object.getString("username_db"), object.getString("password"));
		employeeId = object.getInt("employee_id");
		name = object.getString("name");
		email = object.getString("email");
		title = object.getString("title");
	}
	
	public Map<VariableBoundaries, List<Measurement>> getMeasurementsData() {
		return measurementsData;
	}
	
	public List<Culture> getResearcherCultureList() {
		return researcherCultureList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void addCultureToList(Culture culture) {
		if(!researcherCultureList.contains(culture))
			researcherCultureList.add(culture);
	}
	
	public List<VariableBoundaries> getVariableBoundariesList() {
		return variableBoundariesList;
	}

	@Override
	public String getUserType() {
		return "Researcher";
	}
	
	@Override
	public String toString() {
		return this.getUsername();
	}
	
	public List<Measurement> getAllMeasurements() {
		List<Measurement> measurements = new ArrayList<>();
		for(List<Measurement> measurementList: measurementsData.values()) 
			measurements.addAll(measurementList);
		return measurements;
	}

	@Override
	public List<Culture> getCultureList() {
		return null;
	}
	
	public boolean getReceiveAlerts() {
		return receiveAlerts;
	}
	
	public void setReceiveAlerts(boolean value) {
		this.receiveAlerts = value;
	}
}
