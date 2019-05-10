package pt.iscte.sid19.mongo2mysql.test.mysql;

public class Researcher {

	private int employee_id;
	private String name;
	private String email;
	private String username_db;
	private String title;
	
	public Researcher(int employee_id, String name, String email, String username_db, String title) {
		this.employee_id = employee_id;
		this.name = name;
		this.email = email;
		this.username_db = username_db;
		this.title = title;
	}
	
	public int getEmployee_id() {
		return employee_id;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getUsername() {
		return username_db;
	}
	public String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return employee_id + ",'" +  name + "','" + email + "','"
				+ username_db + "','" + title + "'";
	}
	
	
	
	
	
}
