package Configuration;

import static com.mongodb.client.model.Filters.eq;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigMongoConnection {

	private Dotenv DOTENV = Dotenv.configure().directory("./src/main/java/Configuration").load();
	private MongoDatabase db;
	private FindIterable<Document> result;
	private List<Document> documents;
	private Document document;
	private static String dirPath = "./src/main/resources/Docs/";
	private static final File FILE_COURSE= new File(dirPath.concat("CourseTemp.json"));
	private static JSONParser jsonParser;
	private static MongoCollection<Document> collection;


	public ConfigMongoConnection() {
		super();
		Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);
		MongoClient mongoClient = MongoClients.create(DOTENV.get("DB_URI"));
		System.out.println("Connexion creada correctamente");
		db = mongoClient.getDatabase("vrclassroom");
		collection = db.getCollection("courses");
	}

	public List<Document> getAllUsers() {
		MongoCollection<Document> collectionUser = db.getCollection("users");
		result = collectionUser.find();
		documents = new ArrayList<>();
		result.forEach(documents::add);
		return documents;
	}



	public List<Document> getAllCourses() {
		result = collection.find();
		documents = new ArrayList<>();
		result.forEach(documents::add);
		return documents; 
	}

	public Document getFirst() {
		collection = db.getCollection("courses");
		document = new Document();
		document = collection.find().first();
		return document;
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	public void insertCourse(String title, String description) throws IOException, ParseException {
		jsonParser = new JSONParser();

		FileReader reader = new FileReader(FILE_COURSE);
		Object obj = jsonParser.parse(reader);

		JSONObject newCourse = (JSONObject) obj;
		newCourse.put("title", title);
		newCourse.put("description", description);
		document = getFirst();
		try {
			InsertOneResult result = collection.insertOne(new Document().parse(newCourse.toString()));
			System.out.println("Success! Inserted document id: " + result.getInsertedId());
		} catch (MongoException me) {
			System.err.println("Unable to insert due to an error: " + me);
		}
	}	

	public void deleteDocument (String title) {
		Bson query = eq("title", title);
		try {
			DeleteResult result = collection.deleteOne(query);
			System.out.println("Deleted document count: " + result.getDeletedCount());
		} catch (MongoException me) {
			System.err.println("Unable to delete due to an error: " + me);
		}
	}

	public void updateTeacher(String course, int teacher) {
		Document query = new Document().append("title", course);
		Bson updates = Updates.push("subscribers.teachers", teacher);

		UpdateOptions options = new UpdateOptions().upsert(true);
		try {
			UpdateResult result = collection.updateOne(query, updates, options);
			System.out.println("Modified document count: " + result.getModifiedCount());
			System.out.println("Upserted id: " + result.getUpsertedId()); // only contains a value when an upsert is performed
		} catch (MongoException me) {
			System.err.println("Unable to update due to an error: " + me);
		}
	}

	public void updateStudent(String course, int student) {
		Document query = new Document().append("title", course);
		Bson updates = Updates.push("subscribers.students", student);

		UpdateOptions options = new UpdateOptions().upsert(true);
		try {
			UpdateResult result = collection.updateOne(query, updates, options);
			System.out.println("Modified document count: " + result.getModifiedCount());
			System.out.println("Upserted id: " + result.getUpsertedId()); // only contains a value when an upsert is performed
		} catch (MongoException me) {
			System.err.println("Unable to update due to an error: " + me);
		}
	}

	public void deleteTeacher(String course, int teacher) {
		Document query = new Document().append("title", course);
		Bson updates = Updates.pull("subscribers.teachers", teacher);
		UpdateOptions options = new UpdateOptions().upsert(true);
		try {
			UpdateResult result = collection.updateOne(query, updates, options);
			System.out.println("Modified document count: " + result.getModifiedCount());
			System.out.println("Upserted id: " + result.getUpsertedId()); // only contains a value when an upsert is performed
		} catch (MongoException me) {
			System.err.println("Unable to update due to an error: " + me);
		}
	}

	public void deleteStudent(String course, int student) {
		Document query = new Document().append("title", course);
		Bson updates = Updates.pull("subscribers.students", student);
		UpdateOptions options = new UpdateOptions().upsert(true);
		try {
			UpdateResult result = collection.updateOne(query, updates, options);
			System.out.println("Modified document count: " + result.getModifiedCount());
			System.out.println("Upserted id: " + result.getUpsertedId()); // only contains a value when an upsert is performed
		} catch (MongoException me) {
			System.err.println("Unable to update due to an error: " + me);
		}
	}

	public Document findOne(String title) {
		document = collection.find(eq("title", title)).first();
		if (document == null) {
			System.out.println("No results found.");
		} else {
			System.out.println(document.toJson());
		}
		return document;
	}
}
