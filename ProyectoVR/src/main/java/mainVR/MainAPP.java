package mainVR;

import java.io.IOException;
import java.util.List;

import Configuration.ConfigMongoConnection;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Course;
import model.CourseOverview;
import model.User;
import utilities.ReadJSONAtlas;

public class MainAPP extends Application {

	private static ObservableList<CourseOverview> courseObservableList = FXCollections.observableArrayList();
	private static ObservableList<CourseOverview> userObservableList = FXCollections.observableArrayList();
	private Stage primaryStage;
	private BorderPane rootLayout;
	private static List<Course> courseList;
	private static List<User> userList;

	//	private static final String PATH = "./src/main/java/view/";

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("VR DESKTOP APP");
		this.primaryStage.getIcons().add(new Image("file:./src/main/resources/Image/gafas-vr.png"));
		initRootLayout();
		showCourseOverview();
	}

	private void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainAPP.class.getResource("RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showCourseOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainAPP.class.getResource("CourseOverview.fxml"));
			AnchorPane courseOverview = (AnchorPane) loader.load();

			rootLayout.setCenter(courseOverview);

			CourseOverviewController controller = loader.getController();
			controller.setMainAPP(this);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception f) {
			System.out.println("Aqui una excepcion");
			f.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) throws IOException {
		courseList = ReadJSONAtlas.getAllCourses(ConfigMongoConnection.getAllCourses());
		ObservableList<IntegerProperty> teachers = FXCollections.observableArrayList();
		ObservableList<IntegerProperty> students = FXCollections.observableArrayList();
		courseList.forEach(course -> {
			course.getSubscriber().getTeachers().forEach(x -> {
				teachers.add(new SimpleIntegerProperty(x.getId()));
			});
			course.getSubscriber().getStudents().forEach(x -> students.add(new SimpleIntegerProperty(x.getId())));
			courseObservableList.add(new CourseOverview(course.getTitle(), course.getDescription(), teachers, students));
		});
		userList = ReadJSONAtlas.getAllUsers(ConfigMongoConnection.getAllUsers());
		userList.forEach(user -> {
			userObservableList.add(new CourseOverview(user.getFirstName().substring(1, user.getFirstName().length()-1) + " " +user.getLastName().substring(1, user.getLastName().length()-1)));
		});
		launch(args);
	}

	public ObservableList<CourseOverview> getCourseObservableList() {
		return courseObservableList;
	}

	public ObservableList<CourseOverview> getUserObservableList() {
		return userObservableList;
	}

}
