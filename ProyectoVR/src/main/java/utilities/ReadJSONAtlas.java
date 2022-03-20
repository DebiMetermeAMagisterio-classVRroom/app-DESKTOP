package utilities;


import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;
import org.bson.Document;

import Configuration.ConfigMongoConnection;

public class ReadJSONAtlas {

    private static List<Document> documents;
    private static Course course;
    private static List<Course> courses;

    public ReadJSONAtlas() throws JsonProcessingException {
        documents = ConfigMongoConnection.getAll();
        getAll(documents);
    }

//    public static void main(String[] args) throws JsonProcessingException {
//        documents = ConfigMongoConnection.getAll();
//        getAll(documents);
//        ;
//    }

    private static List<Course> getAll(List<Document> results) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Element> elements = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        List<VrTask> vrTasks = new ArrayList<>();
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
        return courses;
    }

    private static Subscriber parseSubscriber(JsonNode jsonNode) {
        String[] teacherList = jsonNode.get("teachers").toString().substring(1, jsonNode.get("teachers").toString().length() - 1).split(",");
        List<Teacher> teachers = new ArrayList<Teacher>();
        for (String item : teacherList) {
            Teacher teacher = Teacher.builder().id(Long.valueOf(item)).build();
            teachers.add(teacher);
        }
        String[] studentList = jsonNode.get("students").toString().substring(1, jsonNode.get("students").toString().length() - 1).split(",");
        List<Student> students = new ArrayList<Student>();
        for (String item : studentList) {
            Student student = Student.builder().id(Long.valueOf(item)).build();
            students.add(student);
        }
        Subscriber subscriber = Subscriber.builder().teachers(teachers).students(students).build();
        return subscriber;
    }

    private static Element parseElement(JsonNode elementList, int i) {
        Element element = course.getElements().get(i);
        element.setID(Long.valueOf(elementList.get("ID").toString()));
        return element;
    }

    private static Task parseTask(JsonNode ele, int i) {
        Task task = course.getTasks().get(i);
        task.setID(Long.valueOf(ele.get("ID").toString()));
        return task;
    }

    private static VrTask parseVrTask(JsonNode ele, int i) {
        VrTask vrTask = VrTask.builder().ID(Long.valueOf(ele.get("ID").toString()))
                .title(ele.get("title").toString())
                .description(ele.get("descripcion").toString())
                .VRexID(Integer.valueOf(ele.get("VRexID").toString()))
                .versionID(Integer.valueOf(ele.get("versionID").toString()))
                .pollID(Integer.valueOf(ele.get("pollID").toString())).build();
        List<Completion> completions = new ArrayList<Completion>();

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

    private static Completion parseCompletions(JsonNode item, int j) {
        Completion completion = Completion.builder()
                .studentID(Long.valueOf(item.get("studentID").toString()))
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

    //    private static void createDocuments(MongoDatabase db) {
//        Document doc = new Document("", "");
//        db.getCollection("courses").insertOne(doc);
//    }

}
