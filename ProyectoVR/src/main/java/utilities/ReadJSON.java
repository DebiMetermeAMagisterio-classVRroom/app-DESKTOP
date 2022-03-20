package utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.Completion;
import model.Course;
import model.Element;
import model.Student;
import model.Subscriber;
import model.Task;
import model.Teacher;
import model.Upload;
import model.VrTask;

public class ReadJSON {

	private static String dirPath = "./src/main/resources/Docs/";
	private static final File FILE_COURSE= new File(dirPath.concat("classVRroom_courses.json"));
	private static JSONParser jsonParser;


	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		jsonParser = new JSONParser();
		try (Reader reader = new FileReader(FILE_COURSE))
		{
			//Read JSON file
			Object obj = jsonParser.parse(reader);
			JSONArray courseList = (JSONArray) obj;
			List<Course> courses = new ArrayList<Course>();
			courseList.forEach( course -> courses.add(parseCourseObject((JSONObject) course )));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
 
	@SuppressWarnings("unchecked")
	private static Course parseCourseObject(JSONObject courseJSON) 
	{
		JSONObject courseObject = (JSONObject) courseJSON.get("_id");
		System.out.println();
		Course course = Course.builder()
				.id(courseObject.get("$oid").toString())
				.title(courseJSON.get("title").toString())
				.description(courseJSON.get("description").toString())
				.build();

		courseObject = (JSONObject) courseJSON.get("subscribers");
		String[] arrayTeacher = courseObject.get("teachers").toString().substring(1, courseObject.get("teachers").toString().length() - 1).split(",");
		String[] arrayStudent = courseObject.get("students").toString().substring(1, courseObject.get("students").toString().length() - 1).split(",");
		List<Teacher> teachers = new ArrayList<Teacher>();
		List<Student> students = new ArrayList<Student>();
		for(String x: arrayTeacher) {
			teachers.add(Teacher.builder().id(Long.valueOf(x)).build());
		}
		for(String x: arrayStudent) {
			students.add(Student.builder().id(Long.valueOf(x)).build());
		}
		Subscriber subscriber = Subscriber.builder().teachers(teachers).students(students).build();
		course.setSubscriber(subscriber);

		List<Element> elements = new ArrayList<Element>();
		JSONArray elementList = (JSONArray) courseJSON.get("elements");
		elementList.forEach(elementItem -> elements.add(parseElement((JSONObject) elementItem )));
		course.setElements(elements);

		List<Task> tasks = new ArrayList<Task>();
		JSONArray taskList = (JSONArray) courseJSON.get("tasks");

		taskList.forEach(task -> tasks.add(parseTask((JSONObject) task)));

		course.setTasks(tasks);

		List<VrTask> vrTasks = new ArrayList<VrTask>();
		JSONArray vrTaskList = (JSONArray) courseJSON.get("vr_tasks");

		vrTaskList.forEach(vrTaskObj -> vrTasks.add(parseVRTask((JSONObject) vrTaskObj)));

		course.setVrTasks(vrTasks);
		System.out.println(course);
		return course;

	}

	@SuppressWarnings("unchecked")
	private static VrTask parseVRTask(JSONObject vrTaskObj) {
		VrTask vrTask = VrTask.builder()
				.ID(Long.valueOf(vrTaskObj.get("ID").toString()))
				.title(vrTaskObj.get("title").toString())
				.description(vrTaskObj.get("descripcion").toString())
				.VRexID(Integer.valueOf(vrTaskObj.get("VRexID").toString()))
				.versionID(Integer.valueOf(vrTaskObj.get("versionID").toString()))
				.pollID(Integer.valueOf(vrTaskObj.get("pollID").toString())).build();
		List<Completion> completions = new ArrayList<Completion>();
		JSONArray completionList = (JSONArray) vrTaskObj.get("completions");

		completionList.forEach(completionObj -> {
			JSONObject completionJSON = (JSONObject) completionObj;

			Completion completion = Completion.builder()
					.studentID(Long.valueOf(completionJSON.get("studentID").toString()))
					.grade(Integer.valueOf(completionJSON.get("grade").toString()))
					.feedback(completionJSON.get("feedback").toString()).build();

			JSONObject positionD = (JSONObject) completionJSON.get("position_data");
			Map<String, String> positionData = new HashMap<String, String>();
			positionData.put("data",positionD.get("data").toString());
			completion.setPosition_data(positionData);

			JSONObject autogradeO = (JSONObject) completionJSON.get("autograde");
			Map<String, String> autograde = new HashMap<String, String>();
			autograde.put("passed_item",autogradeO.get("passed_items").toString());
			autograde.put("failed_item",autogradeO.get("failed_items").toString());
			autograde.put("comments",autogradeO.get("comments").toString());
			completion.setAutograde(autograde);

			completions.add(completion);
		});
		vrTask.setCompletions(completions);
		return vrTask;
	}

	@SuppressWarnings("unchecked")
	private static Task parseTask(JSONObject taskObject) {
		Task task = Task.builder()
				.ID(Long.valueOf(taskObject.get("ID").toString()))
				.type(taskObject.get("type").toString())
				.title(taskObject.get("title").toString())
				.description(taskObject.get("description").toString())
				.order(Integer.valueOf(taskObject.get("order").toString())).build();
		List<Upload> uploads = new ArrayList<Upload>();
		JSONArray uploadList = (JSONArray) taskObject.get("uploads");

		uploadList.forEach(uploadObj -> {
			JSONObject uploadJSON = (JSONObject) uploadObj;

			Upload upload = Upload.builder()
					.studentID(Long.valueOf(uploadJSON.get("studentID").toString()))
					.text(uploadJSON.get("text").toString())
					.grade(Integer.valueOf(uploadJSON.get("grade").toString()))
					.feedback(uploadJSON.get("feedback").toString()).build();

			if(uploadJSON.containsKey("file")) {
				upload.setFile(uploadJSON.get("file").toString());
			}else {
				upload.setFile("");
			}
			uploads.add(upload);
		});
		task.setUploads(uploads);
		return task;
	}

	private static Element parseElement(JSONObject elementList) {
		Element element = Element.builder()
				.ID(Long.valueOf(elementList.get("ID").toString()))
				.type(elementList.get("type").toString())
				.title(elementList.get("title").toString())
				.description(elementList.get("description").toString())
				.order(Integer.valueOf(elementList.get("order").toString())).build();

		if(elementList.containsKey("contents")) {
			element.setContents(elementList.get("contents").toString());
		}else {
			element.setContents("");
		}
		if(elementList.containsValue("file")) {
			element.setFile(elementList.get("file").toString());
		} else {
			element.setFile("");
		}

		return element;
	}

}