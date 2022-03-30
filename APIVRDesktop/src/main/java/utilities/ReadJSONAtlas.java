package utilities;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import config.ConfigMongoConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Completion;
import model.Course;
import model.Element;
import model.Student;
import model.Subscriber;
import model.Task;
import model.Teacher;
import model.User;
import model.VrTask;

public class ReadJSONAtlas {

	private Course course;
	private ObservableList<Course> courses;
	private User user;
	private ObservableList<User> users;
	private ConfigMongoConnection configMongoConnection = new ConfigMongoConnection();
	private List<Document> results = new ArrayList<Document>();
	private Document document = new Document();

	    public static void main(String[] args) throws IOException {
	    	ReadJSONAtlas readJSONAtlas = new ReadJSONAtlas();
//	    	readJSONAtlas.getAllCourses();
//	    	readJSONAtlas.getAllUsers();
	    	readJSONAtlas.getCourse("Course4");
		}

	public Course getCourse(String title) throws IOException {
		document = configMongoConnection.findOne(title);
		this.courses = FXCollections.observableArrayList();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		ObservableList<Element> elements = FXCollections.observableArrayList();
		ObservableList<Task> tasks = FXCollections.observableArrayList();
		ObservableList<VrTask> vrTasks = FXCollections.observableArrayList();
		course = Course.builder().build();
		JsonNode jsonNode = objectMapper.readTree(document.toJson());
		course = objectMapper.readValue(document.toJson(), Course.class);
		course.setId(jsonNode.get("_id").get("$oid").toString());
		course.setSubscriber(parseSubscriber(jsonNode.get("subscribers")));
		Iterator<JsonNode> elementsNode = jsonNode.get("elements").iterator();
		int i = 0;
		for (Iterator<JsonNode> it = elementsNode; it.hasNext(); ) {
			JsonNode ele = it.next();
			elements.add(parseElement(ele, i));
			i++;
		}
		course.setElements(elements);
		Iterator<JsonNode> taskNode = jsonNode.get("tasks").iterator();
		i = 0;
		for (Iterator<JsonNode> it = taskNode; it.hasNext(); ) {
			JsonNode ele = it.next();
			tasks.add(parseTask(ele, i));
			i++;
		}
		course.setTasks(tasks);
		Iterator<JsonNode> vrTaskNode = jsonNode.get("vr_tasks").iterator();
		i = 0;
		for (Iterator<JsonNode> it = vrTaskNode; it.hasNext(); ) {
			JsonNode ele = it.next();
			vrTasks.add(parseVrTask(ele, i));
			i++;
		}
		course.setVrTasks(vrTasks);
		return course;
}


public ObservableList<Course> getAllCourses() throws IOException {
	results = configMongoConnection.getAllCourses();
	this.courses = FXCollections.observableArrayList();
	ObjectMapper objectMapper = new ObjectMapper();
	objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	ObservableList<Element> elements = FXCollections.observableArrayList();
	ObservableList<Task> tasks = FXCollections.observableArrayList();
	ObservableList<VrTask> vrTasks = FXCollections.observableArrayList();
	for (Document doc : results) {
		course = Course.builder().build();
		JsonNode jsonNode = objectMapper.readTree(doc.toJson());
		course = objectMapper.readValue(doc.toJson(), Course.class);
		course.setId(jsonNode.get("_id").get("$oid").toString());
		course.setSubscriber(parseSubscriber(jsonNode.get("subscribers")));
		Iterator<JsonNode> elementsNode = jsonNode.get("elements").iterator();
		int i = 0;
		for (Iterator<JsonNode> it = elementsNode; it.hasNext(); ) {
			JsonNode ele = it.next();
			elements.add(parseElement(ele, i));
			i++;
		}
		course.setElements(elements);

		Iterator<JsonNode> taskNode = jsonNode.get("tasks").iterator();
		i = 0;
		for (Iterator<JsonNode> it = taskNode; it.hasNext(); ) {
			JsonNode ele = it.next();
			tasks.add(parseTask(ele, i));
			i++;
		}
		course.setTasks(tasks);
		Iterator<JsonNode> vrTaskNode = jsonNode.get("vr_tasks").iterator();
		i = 0;
		for (Iterator<JsonNode> it = vrTaskNode; it.hasNext(); ) {
			JsonNode ele = it.next();
			vrTasks.add(parseVrTask(ele, i));
			i++;
		}
		course.setVrTasks(vrTasks);
		courses.add(course);
	}
	//        courses.forEach(x ->System.out.println(x));
	return courses;
}

private Subscriber parseSubscriber(JsonNode jsonNode) {
	String[] teacherList = jsonNode.get("teachers").toString().substring(1, jsonNode.get("teachers").toString().length() - 1).split(",");
	ObservableList<Teacher> teachers = FXCollections.observableArrayList();
	for (String item : teacherList) {
		Teacher teacher = Teacher.builder().id(Integer.valueOf(item)).build();
		teachers.add(teacher);
	}
	String[] studentList = jsonNode.get("students").toString().substring(1, jsonNode.get("students").toString().length() - 1).split(",");
	ObservableList<Student> students = FXCollections.observableArrayList();
	for (String item : studentList) {
		Student student = Student.builder().id(Integer.valueOf(item)).build();
		students.add(student);
	}
	Subscriber subscriber = Subscriber.builder().teachers(teachers).students(students).build();
	return subscriber;
}

private Element parseElement(JsonNode elementList, int i) {
	Element element = course.getElements().get(i);
	element.setID(Integer.valueOf(elementList.get("ID").toString()));
	return element;
}

private Task parseTask(JsonNode ele, int i) {
	Task task = course.getTasks().get(i);
	task.setID(Integer.valueOf(ele.get("ID").toString()));
	return task;
}

private VrTask parseVrTask(JsonNode ele, int i) {
	VrTask vrTask = VrTask.builder().ID(Integer.valueOf(ele.get("ID").toString()))
			.title(ele.get("title").toString())
			.description(ele.get("descripcion").toString())
			.VRexID(Integer.valueOf(ele.get("VRexID").toString()))
			.versionID(Integer.valueOf(ele.get("versionID").toString()))
			.pollID(Integer.valueOf(ele.get("pollID").toString())).build();
	ObservableList<Completion> completions = FXCollections.observableArrayList();

	Iterator<JsonNode> vrTaskNode = ele.get("completions").iterator();
	int j = 0;
	for (Iterator<JsonNode> it = vrTaskNode; it.hasNext(); ) {
		JsonNode item = it.next();
		completions.add(parseCompletions(item, j));
		j++;
	}
	vrTask.setCompletions(completions);
	return vrTask;
}

private Completion parseCompletions(JsonNode item, int j) {
	Completion completion = Completion.builder()
			.studentID(Integer.valueOf(item.get("studentID").toString()))
			.grade(Integer.valueOf(item.get("grade").toString()))
			.feedback(item.get("feedback").toString()).build();

	JsonNode positionD = item.get("position_data");
	Map<String, String> positionData = new HashMap<String, String>();
	positionData.put("data", positionD.get("data").toString());
	completion.setPosition_data(positionData);

	JsonNode autogradeO = item.get("autograde");
	Map<String, String> autograde = new HashMap<String, String>();
	autograde.put("passed_item", autogradeO.get("passed_items").toString());
	autograde.put("failed_item", autogradeO.get("failed_items").toString());
	autograde.put("comments", autogradeO.get("comments").toString());
	completion.setAutograde(autograde);
	return completion;
}

public ObservableList<User> getAllUsers() throws IOException {
	results = configMongoConnection.getAllUsers();
	users = FXCollections.observableArrayList();
	ObjectMapper objectMapper = new ObjectMapper();
	objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	for (Document doc : results) {
		JsonNode jsonNode = objectMapper.readTree(doc.toJson());
		user = User.builder().build();
		user = objectMapper.readValue(doc.toJson(), User.class);
		user.setFirstName(jsonNode.get("first_name").toString());
		user.setLastName(jsonNode.get("last_name").toString());
		users.add(user);
	}
	//        users.forEach(x ->System.out.println(x));
	return users;
}

}
