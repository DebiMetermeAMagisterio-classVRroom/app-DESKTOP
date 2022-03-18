package Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.client.*;
import org.bson.Document;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigMongoConnection {

	private final static Dotenv DOTENV = Dotenv.configure().directory("./src/main/java/Configuration").load();
	static MongoDatabase db;
	static ArrayList<String> colResult = new ArrayList<>();

	public static ArrayList<String> ConfigMongoConnection () {
		String uri = "mongodb+srv://ladyangel:12345@proyectovr.jlpld.mongodb.net/vrclassroom?retryWrites=true&w=majority";

		try (MongoClient mongoClient = MongoClients.create(uri)) {

			System.out.println("Connexion creada correctamente");
			try {
				db = mongoClient.getDatabase("vrclassroom");
				System.out.println(db.toString());
				MongoCollection<Document> collection = db.getCollection("courses");
				System.out.println(collection.toString());
				FindIterable<Document> result = collection.find();
				System.out.println(result.toString());
				colResult = new ArrayList<String>();
				for (Document doc : result) {
					colResult.add(doc.toJson());
					System.out.println(doc);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return colResult;
	}

	public static  ArrayList<String> getAll(){
		Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
//		String uri = "mongodb+srv://ladyangel:12345@proyectovr.jlpld.mongodb.net/vrclassroom?retryWrites=true&w=majority";

		try (MongoClient mongoClient = MongoClients.create(DOTENV.DB_URI)) {

			System.out.println("Connexion creada correctamente");
			MongoDatabase db = mongoClient.getDatabase("vrclassroom");
			System.out.println(db.toString());
			MongoCollection<Document> collection = db.getCollection("courses");
			System.out.println(collection.toString());
			FindIterable<Document> result = collection.find();
			System.out.println(result.toString());
			for (Document doc : result) {
				colResult.add(doc.toJson());
				System.out.println(doc);
			}
			;
			return colResult;
		}
	}

	private static void printDatabases(MongoClient mongoClient) {
		List<Document> dbDocuments = mongoClient.listDatabases().into(new ArrayList());
		dbDocuments.forEach(document -> System.out.println(document.toJson()));
	}
}
