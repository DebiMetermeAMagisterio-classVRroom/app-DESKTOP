package mainVR;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;



public class MainAPP extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private static final String PATH = "./src/main/java/view/";
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("VR DESKTOP APP");
		initRootLayout();
		showCourseOverview();
	}

	private void showCourseOverview() {
		try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainAPP.class.getResource("CourseOverview.fxml"));
            AnchorPane courseOverview = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(courseOverview);
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

	public static void main(String[] args) {
		launch(args);
//		
		File file = new File(PATH+"CourseOverview.fxml");
		System.out.println(file.exists());
//		String[] list = file.list();
//		for(String x: list) {
//			System.out.println(x);
//	
//		}
	}

}
