package Configuration;

import com.mongodb.client.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigMongoConnection {

	private final static Dotenv DOTENV = Dotenv.configure().directory("./src/main/java/Configuration").load();
	private static MongoDatabase db;
	private static FindIterable<Document> result;
	private static List<Document> documents;

	public ConfigMongoConnection () {

	}

	public static List<Document> getAllUsers() {
		Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);

		try (MongoClient mongoClient = MongoClients.create(DOTENV.get("DB_URI"))) {
			System.out.println("Connexion creada correctamente");
			db = mongoClient.getDatabase("vrclassroom");
			MongoCollection<Document> collection = db.getCollection("users");
			result = collection.find();
			documents = new ArrayList<>();
			result.forEach(documents::add);
		}
		return documents;
	}

	public static  List<Document> getAllCourses() {
		Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);

		try (MongoClient mongoClient = MongoClients.create(DOTENV.get("DB_URI"))) {
			System.out.println("Connexion creada correctamente");
			db = mongoClient.getDatabase("vrclassroom");
			MongoCollection<Document> collection = db.getCollection("courses");
			result = collection.find();
			documents = new ArrayList<>();
			result.forEach(documents::add);
		}
		return documents;
	}

//	private static void printDatabases(MongoClient mongoClient) {
//		List<Document> dbDocuments = mongoClient.listDatabases().into(new ArrayList());
//		dbDocuments.forEach(document -> System.out.println(document.toJson()));
//	}
}
