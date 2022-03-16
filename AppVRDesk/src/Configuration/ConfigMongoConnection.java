package Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigMongoConnection {

	private final static Dotenv DOTENV = Dotenv.configure().directory("./src/Configuration").load();
	private static FindIterable<Document> iterable;
	private static List<Document> courses;

	public static List<Document> ConfigMongoConnection () {

//		Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
//
//		MongoClientURI uri = new MongoClientURI("mongodb+srv://"+DOTENV.get("DB_USERNAME")+":"+DOTENV.get("DB_PASSWORD")+"@proyectovr.jlpld.mongodb.net/"+DOTENV.get("DB_NAME")+"?retryWrites=true&w=majority");
//
//		try(MongoClient mongoClient = new MongoClient(uri)){
//			System.out.println("Connexion creada correctamente");
//
//			MongoDatabase db = mongoClient.getDatabase("vrclassroom");
//			MongoCollection<Document> collection = db.getCollection("courses");
//			System.out.println(collection.toString());
//			
//			collection.find().into(courses);
//			System.out.println(courses);
			return courses;
//		}
	}

	public static void getAll(){
		Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
		ConnectionString connectionString = new ConnectionString("mongodb+srv://ladyangelVR:qnIxw5VJzYUQWPoe@proyectovr.jlpld.mongodb.net/vrclassroom?retryWrites=true&w=majority");
//		MongoClientSettings settings = MongoClientSettings.builder()
//		        .applyConnectionString(connectionString)
//		        .serverApi(ServerApi.builder()
//		            .version(ServerApiVersion.V1)
//		            .build())
//		        .build();
		try(MongoClient mongoClient = MongoClients.create(connectionString)){
		
			System.out.println("Connexion creada correctamente");
			
			List<Document> dbDocuments = mongoClient.listDatabases().into(new ArrayList());
			printDatabases(mongoClient);
		}
		
//		MongoDatabase database = mongoClient.getDatabase("vrclassroom");
//		
//		MongoCollection<Document> collection = database.getCollection("courses");
		
//		System.out.println(collection.countDocuments());
//		collection.find().into(courses);
//		System.out.println(courses.toString());
		
		
//		MongoClientURI uri = new MongoClientURI();
//		try(MongoClient mongoClient = new MongoClient(uri)){
//			
//
//			MongoDatabase db = mongoClient.getDatabase("classVRroom");
//			
//			iterable = db.getCollection("courses").find();

//			return iterable;
		

	}

	private static void printDatabases(MongoClient mongoClient) {
		List<Document> dbDocuments = mongoClient.listDatabases().into(new ArrayList());
		dbDocuments.forEach(document -> System.out.println(document.toJson()));
		
	}

}