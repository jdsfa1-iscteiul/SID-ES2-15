package pt.iscte.sid19.mongo2mysql;

import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Filters.not;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.bson.Document;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoExporter extends Thread{
	
	
	private static final String DATABASE = "SensorDataDB";
	private static final String COLLECTION = "dataCollection";
	private static final long DELAY = 3000;
	
	private MongoClient client;
	private MongoDatabase db;
	private MongoCollection<Document> collection;
	private LinkedBlockingQueue<Record> records;
	
	// MongoExporter needs the MySQLImporter instance in order to ask for the last timestamp
	private MySQLImporter mysql;
	
	public MongoExporter(LinkedBlockingQueue<Record> records, MySQLImporter mysql) {
		this.records = records;
		connect();
		this.mysql = mysql;
	}
	
	/**
	 * Este método estabele a ligação ao servidor Mongo e instancia os atributos db e collection
	 * com a base de dados e a colecção que iremos utilizar
	 */
	private void connect() {
		client = MongoClients.create();
		db = client.getDatabase(DATABASE);
		collection = db.getCollection(COLLECTION);
	}

	@Override
	public void run() {
		while(!isInterrupted()) {
			try {
				List<String> documents = fetchDocuments();
				
				// parse JSON strings into Record objects
				Gson gson = new Gson();
				
				for(String json: documents) {
					System.out.println(json);
					
					Record record = gson.fromJson(json, Record.class);
					
					System.out.println(record.toString());
					
					records.offer(record);
				}
				Thread.sleep(DELAY);
			} catch (InterruptedException ie) {
				interrupt();
			} catch (JsonParseException je) {}
		}
	}

	// method that fetches documents from mongoDB.
	// How to make this incremental?
	private List<String> fetchDocuments() {
		List<String> documents = new LinkedList<>();
		
		// fetches all documents from the collection that do not have the field "unexpected"
		MongoCursor<Document> cursor = collection.find(not(exists("unexpected"))).iterator();
		 try {
			 while(cursor.hasNext()) {
				 String m = cursor.next().toJson();
				 documents.add(m);
			 }
		 } finally {
			 cursor.close();
		 }
		
		return documents;
	}
	
	private List<String> fetchDocuments(Date lastTimestamp) {
		List<String> documents = new LinkedList<>();
		
		
		
		return documents;
	}

}
