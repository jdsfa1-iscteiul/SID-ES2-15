package pt.iscte.sid19.mongo2mysql.test.mongo;

import java.sql.Date;
import java.sql.Time;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Record {
	
	@BsonProperty("_id")
	private ObjectId id;
	
	@BsonProperty("tmp")
	private double tmp;
	
	@BsonProperty("hum")
	private double hum;
	
	@BsonProperty("dat")
	private Date dat;
	
	@BsonProperty("tim")
	private Time tim;
	
	@BsonProperty("cell")
	private double cell;
	
	@BsonProperty("sens")
	private String sens;
	
	public Record() {}
	
	public double getTmp() { return tmp; }
	
	public void setTmp(double tmp) { this.tmp = tmp; }
	
	public double getHum() { return hum; }
	
	public void setHum(double hum) { this.tmp = hum; }
	
	public Date getDat() { return dat; }
	
	public void setDat(Date dat) { this.dat = dat; }
	
	public Time getTim() { return tim; }
	
	public void setTim(Time tim) { this.tim = tim; }
	
	public double getCell() { return cell; }
	
	public void setCell(double cell) { this.cell = cell; }
	
	public String getSens() { return sens; }
	
	public void setSens(String sens) { this.sens = sens; }

}
