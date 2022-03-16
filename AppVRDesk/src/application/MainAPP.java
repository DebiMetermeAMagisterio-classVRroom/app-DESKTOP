package application;
	
import java.io.IOException;
import java.util.List;

import Model.Course;
import Model.Course1;
import Utilities.ReadJSON;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MainAPP extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
//	private static final String PATH = "./src/main/java/view/";
	private static List<Course> courseList;
	private static ObservableList<Course1> courseData; 
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("VR DESKTOP APP");
		initRootLayout();
		showCourseOverview();
	}

	private void showCourseOverview() {
		try {
            // Load course overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAPP.class.getResource("CourseOverview.fxml"));
            AnchorPane courseOverview = (AnchorPane) loader.load();
            
            // Set course overview into the center of root layout.
            rootLayout.setCenter(courseOverview);
            
            CourseOverviewController controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }catch(Exception f) {
        	System.out.println("hola");
        	f.printStackTrace();
        }
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
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public ObservableList<Course1> getCourseData() {
		courseData = FXCollections.observableArrayList();
		for(Course item: courseList) {
			Course1 course = new Course1(item.getId(), item.getTitle(), item.getDescription());
			courseData.add(course);
		}
		System.out.println(courseData);
		return courseData;
	}

	public static void main(String[] args) {
		courseList = ReadJSON.ReadJSON();
		launch(args);
	}
}
