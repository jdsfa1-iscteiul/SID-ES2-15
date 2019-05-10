package pt.iscte.sid19.mongo2mysql.test.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import pt.iscte.sid19.mongo2mysql.Record;
import pt.iscte.sid19.mongo2mysql.Warning;

public class MysqlWriter {
	
	private static final String DB_URL = "jdbc:mariadb://localhost:3306/grupo15_main";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "123grupo15";

	private Connection connection;
	private Statement stmt;
	
	public MysqlWriter() {
		try {
			connect();
			System.out.println("Success");
			stmt = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("Something went wrong");
			System.exit(1);
		}
	}

	private void connect() throws SQLException {
		connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	}

	private void getResearchers() {
		try {
			// get data
			ResultSet rs = stmt.executeQuery("select * from researcher");
			// display data
			while(rs.next()) {
				int employee_id = rs.getInt("employee_id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String username = rs.getString("username_db");
				String title = rs.getString("title");
				
				System.out.println("[" + employee_id + "] name: " + name + ", email: " + email + ", username: " + username
						+ ", title: " + title);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertResearcher(Researcher researcher) throws SQLException {
		String insert = "insert into researcher values (" + researcher.toString() + ")";
		stmt.executeUpdate(insert);
	}

	private double getLightLowerBound() throws SQLException {
		String sql = "select light_lower_bound from system";
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		double lower_bound = rs.getDouble(1);
		return lower_bound;
	}
	

	private double getLightUpperBound() throws SQLException {
		String sql = "select light_upper_bound from system";
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		double upper_bound = rs.getDouble(1);
		return upper_bound;
	}
	
	private double getTemperatureLowerBound() throws SQLException {
		String sql = "select temperature_lower_bound from system";
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		double lower_bound = rs.getDouble(1);
		return lower_bound;
	}

	private double getTemperatureUpperBound() throws SQLException {
		String sql = "select temperature_upper_bound from system";
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		double upper_bound = rs.getDouble(1);
		return upper_bound;
	}
	
	public static void main(String[] args) {
		MysqlWriter mw = new MysqlWriter();
//		Researcher alice = new Researcher(3313, "Alice Ribeiro", "aribeiro@iscte.pt", "aribeiro", "Integrado");
//		mw.getResearchers();
		
		
		try {
			System.out.println("light_lower_bound: " + mw.getLightLowerBound());
			System.out.println("light_upper_bound: " + mw.getLightUpperBound());
			System.out.println("temperature_lower_bound: " + mw.getTemperatureLowerBound());
			System.out.println("temperature_upper_bound: " + mw.getTemperatureUpperBound());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("-------------------------------------");
		
//		Record r = new Record("", "", "", "", "110", "", new Timestamp(System.currentTimeMillis()).toString());
//		try {
//			String insert_light = "insert into " + "warning" + "(" + Warning.DATETIME + ", " + Warning.VARIABLE_NAME + ", " + 
//					Warning.LOWER_BOUND + ", " + Warning.UPPER_BOUND + ", " + Warning.MEASUREMENT + ") values(" + 
//					r.getTimestamp() + ",'luminosity'," + mw.getLightLowerBound() + "," + mw.getLightUpperBound() + "," + r.getCell() + ")";
//			System.out.println(insert_light);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
//		try {
//			mw.insertResearcher(alice);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	}


	
}
