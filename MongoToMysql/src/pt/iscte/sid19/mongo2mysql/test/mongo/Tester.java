package pt.iscte.sid19.mongo2mysql.test.mongo;

import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Filters.not;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import com.mongodb.Block;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;



public class Tester {
	
	private static String DATABASE = "SensorDataDB";
	private static String COLLECTION = "dataCollection";

	private MongoClient client;
	private MongoDatabase db;
	private MongoCollection<Document> collection;
	
	public Tester() {
		client = MongoClients.create();
		db = client.getDatabase(DATABASE);
		collection = db.getCollection(COLLECTION);
	}
	
	public void printUnexpected() {
		/*   1
		Document doc = collection.find(not(exists("unexpected"))).first();
		System.out.println(doc.toJson());
		*/
		
		Block<Document> printBlock = new Block<Document>() {
			public void apply(final Document document) {
				System.out.println(document.toJson());
			}
		};
		System.out.println("---------------------------------");
		collection.find(not(exists("unexpected"))).forEach(printBlock);
		
	}
	
	public void printRecords() {
//		CodecProvider pojoCodecProvider = PojoCodecProvider.builder().register("org.example.pojos").build();
//		CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
		
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
					fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		MongoCollection<Record> records = db.getCollection("collection", Record.class).withCodecRegistry(pojoCodecRegistry);
		
		Bson queryFilter = new Document
				
		Record one = records.find(queryFilter).iterator().tryNext();
		
	}
	
	
	public static void main(String[] args) {
		Tester a = new Tester();
//		a.printUnexpected();
	}

}
