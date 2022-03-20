package Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.client.*;
import model.Course;
import model.Element;
import model.Subscriber;
import model.Teacher;
import org.bson.Document;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.simple.JSONObject;

public class ConfigMongoConnection {

	private final static Dotenv DOTENV = Dotenv.configure().directory("./src/main/java/Configuration").load();
	private static MongoDatabase db;
	private static List<Course> courses = new ArrayList<Course>();
	private static FindIterable<Document> result;
	private static List<Document> documents;
	private ConfigMongoConnection () throws JsonProcessingException {
		getAll();

	}

	public static  List<Document> getAll() throws JsonProcessingException {
		Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);

		try (MongoClient mongoClient = MongoClients.create(DOTENV.get("DB_URI"))) {
			System.out.println("Connexion creada correctamente");
			db = mongoClient.getDatabase("vrclassroom");
			MongoCollection<Document> collection = db.getCollection("courses");
			result = collection.find();
			documents = new ArrayList<Document>();
			result.forEach(documents::add);
		}
		return documents;
	}

//	private static void printDatabases(MongoClient mongoClient) {
//		List<Document> dbDocuments = mongoClient.listDatabases().into(new ArrayList());
//		dbDocuments.forEach(document -> System.out.println(document.toJson()));
//	}
}
