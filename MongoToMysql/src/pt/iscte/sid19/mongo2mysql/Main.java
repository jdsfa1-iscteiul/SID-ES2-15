package pt.iscte.sid19.mongo2mysql;

import java.util.concurrent.LinkedBlockingQueue;

public class Main {
	
	public static void main(String[] args) {
		LinkedBlockingQueue<Record> records = new LinkedBlockingQueue<>();
		
		MySQLImporter mysql = new MySQLImporter(records);
		mysql.start();
		
		new MongoExporter(records, mysql).start();
	}

}
