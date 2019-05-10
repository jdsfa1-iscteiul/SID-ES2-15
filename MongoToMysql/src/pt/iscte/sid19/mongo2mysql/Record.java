package pt.iscte.sid19.mongo2mysql;

import java.sql.Date;
import java.sql.Timestamp;

public class Record {

	/**
	 * Class to create Record objects from JSON Strings
	 */

	private String tmp;

	private String hum;

	private String dat;

	private String tim;

	private String cell;

	private String sens;

	private Date timestamp;

	public Record(String tmp, String hum, String dat, String tim, String cell, String sens, Date timestamp) {
		this.tmp = tmp;
		this.hum = hum;
		this.dat = dat;
		this.tim = tim;
		this.cell = cell;
		this.sens = sens;
		this.timestamp = timestamp;
	}


	public String getHum() {
		return hum;
	}


	public String getSens() {
		return sens;
	}


	public String getTmp() {
		return tmp;
	}


	public String getDat() {
		return dat;
	}


	public String getTim() {
		return tim;
	}

	public String getCell() {
		return cell;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}

	
	

	@Override
	public String toString() {
		return "Record [tmp = " + tmp + ", hum = " + hum + ", dat = " + dat + ", tim = " + tim + ", cell = " + cell
				+ ", sens = " + sens + "]" + ", timestamp = " + timestamp;
	}

}
