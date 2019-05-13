package pt.iscte.sid19.sensor2mongo;

import java.sql.Timestamp;
import java.util.concurrent.LinkedBlockingQueue;

import org.bson.Document;
import org.bson.json.JsonParseException;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Esta classe recebe da classe Callback strings JSON com as mensagens recebidas
 * do sensor.
 * <p>
 * É responsável por inserir as strings as-is na base de dados MongoDB.
 * 
 * @author grupo15
 *
 */
public class MongoWriter extends Thread {

	private static String DATABASE = "SensorDataDB";
	private static String COLLECTION = "dataCollection";

	private MongoClient client;
	private MongoDatabase db;
	private MongoCollection<Document> collection;
	private LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

	public MongoWriter(LinkedBlockingQueue<String> queue) {
		this.queue = queue;
		client = MongoClients.create();
		db = client.getDatabase(DATABASE);
		collection = db.getCollection(COLLECTION);
	}

	@Override
	public void run() {
		while (true) {
			try {
				// fetch message from queue
				String message = queue.take();
				
				// create document
				Document doc = null;
				try {
					// try to parse the message as a json string
					doc = Document.parse(message).append("timestamp",
							new Timestamp(System.currentTimeMillis()));
				} catch(JsonParseException e) {
					// if it is not possible to parse the message, just copy it the way it is
					doc = new Document("unexpected", message)
							.append("timestamp", new Timestamp(System.currentTimeMillis()));
				}
				// insert document
				if(doc != null)
					collection.insertOne(doc);				
			} catch (InterruptedException e) {}
		}
	}
}
