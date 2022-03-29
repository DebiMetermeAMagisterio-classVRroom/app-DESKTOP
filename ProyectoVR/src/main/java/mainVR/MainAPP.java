package mainVR;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Course;
import model.User;
import utilities.ReadJSONAtlas;


public class MainAPP extends Application {

	private static ObservableList<Course> courses = FXCollections.observableArrayList();
	private static ObservableList<User> users = FXCollections.observableArrayList();
	private Stage primaryStage;
	private BorderPane rootLayout;
	private static ReadJSONAtlas readJSONAtlas = new ReadJSONAtlas();

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
		} 
	}
	
	public ObservableList<Course> getCourses() throws IOException {
		return courses;
	}

	public ObservableList<User> getUsers() throws IOException {
		return users;
	}
	
	public boolean showCourseEditDialog(Course course) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainAPP.class.getResource("CourseEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

	        // Create the dialog Stage.
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Edit Course");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        dialogStage.getIcons().add(new Image("file:./src/main/resources/Image/gafas-vr.png"));
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        // Set the person into the controller.
	        CourseEditDialogController controller = loader.getController();
	        controller.setDialogStage(dialogStage);
	        controller.setCourse(course);

	        // Show the dialog and wait until the user closes it
	        dialogStage.showAndWait();

	        return controller.isOkClicked();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
//	public MainAPP() throws IOException {
//		courses = readJSONAtlas.getAllCourses();
//		users = readJSONAtlas.getAllUsers();
//	}

	public static void main(String[] args) throws IOException {
		courses = readJSONAtlas.getAllCourses();
		users = readJSONAtlas.getAllUsers();
		launch(args);
	}

}
