package utilities;



import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.json.simple.parser.JSONParser;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import Configuration.ConfigMongoConnection;

public class ReadJSONAtlas {

	private static ConfigMongoConnection configMongo;
	private static JSONParser jsonParser;
	private static ArrayList<String> courses;

	public static void main(String[] args) {
//		courses = ConfigMongoConnection.ConfigMongoConnection();
//		findAllJSONAtlas(courses);
		courses = ConfigMongoConnection.getAll();
		getAll(courses);
	}

	private static void findAllJSONAtlas(List<String> coursesList) {
		coursesList.forEach(x -> System.out.println(x));
	}
	
	private static void getAll(ArrayList<String> courses) {
		for(String element: courses) {
			System.out.println(element);
		}
	}
	
	private static void createDocuments(MongoDatabase db) {
		Document doc = new Document("", "");
		db.getCollection("courses").insertOne(doc);
	}

}
