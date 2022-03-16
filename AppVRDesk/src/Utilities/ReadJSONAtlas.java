package Utilities;



import java.util.List;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import Configuration.ConfigMongoConnection;

public class ReadJSONAtlas {

	private static List<Document> courses;
	private static List<String> courses2;
	private static FindIterable<Document> iterableL;

	public static void main(String[] args) {
//		courses = ConfigMongoConnection.ConfigMongoConnection();
//		findAllJSONAtlas(courses);
		ConfigMongoConnection.getAll();
		getAll(iterableL);
	}

	private static void findAllJSONAtlas(List<Document> courses2) {
		courses2.forEach(x -> System.out.println(x));
	}
	
	private static void getAll(FindIterable<Document> iterable) {
//		for(Document obj: iterable) {
//			courses2.add(obj.toJson());
//		}
//		System.out.println(courses.toString());
		System.out.println(iterable.toString());
	}
	
	private static void createDocuments(MongoDatabase db) {
		Document doc = new Document("", "");
		db.getCollection("courses").insertOne(doc);
	}

}
