package pt.iscte.sid19.mongo2mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.LinkedBlockingQueue;

public class MySQLImporter extends Thread {
	
	private static final String DB_URL = "jdbc:mariadb://localhost:3306/grupo15_main";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "123grupo15";
	
	private static final String WARNING = "warning";
	
	private Connection connection;
	private Statement stmt;

	private LinkedBlockingQueue<Record> records;
	
	public MySQLImporter(LinkedBlockingQueue<Record> records) {
		try {
			this.records = records;
			connect();
			System.out.println("Success");
			stmt = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("Something went wrong while establishing a connection to MySQL");
			System.exit(1);
		}
	}
	
	private void connect() throws SQLException {
		connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	}

	@Override
	public void run() {
		while(!isInterrupted()) {
			try {
				Record r = records.take();
				
				String insert_light = "insert into " + WARNING + "(" + Warning.DATETIME + ", " + Warning.VARIABLE_NAME + ", " + 
								Warning.LOWER_BOUND + ", " + Warning.UPPER_BOUND + ", " + Warning.MEASUREMENT + ") values(" + 
								r.getTimestamp() + ",'luminosity'," + getLightLowerBound() + "," + getLightUpperBound() + "," + r.getCell() + ")";
				
				stmt.executeUpdate(insert_light);
				
			} catch (InterruptedException e) {
				interrupt();
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	
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

	
	public Date getLastTimestamp() {
		Date timestamp = null;
		
		return timestamp;
	}

}
