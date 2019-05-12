package utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.mysql.DatabaseUser;

public class Researcher extends DatabaseUser{
	
	private int employeeId;
	
	private String name, email, title;
	
	private Map<VariableBoundaries, List<Measurement>> measurementsData = new HashMap<>();
	
	private List<Culture> cultureList = new ArrayList<>();
	
	public Researcher(String username) {
		super(username);
	}

	public Researcher(int employeeId, String name, String email, String username, String title) {
		super(username);
		this.employeeId = employeeId;
		this.name = name;
		this.email = email;
	//	this.username = username;
		this.title = title;
	}
	
	public Researcher(ResultSet object) throws SQLException {
		super(object.getString("username_db"));
		employeeId = object.getInt("measurement_id");
		name = object.getString("name");
		email = object.getString("email");
		//username = object.getString("username_db");
		title = object.getString("culture_description");
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

//	public String getUsername() {
//		return username;
//	}
	
	public void addCultureToList(Culture culture) {
		if(!cultureList.contains(culture))
			cultureList.add(culture);
	}

	@Override
	public String getUserType() {
		return "Researcher";
	}
}
