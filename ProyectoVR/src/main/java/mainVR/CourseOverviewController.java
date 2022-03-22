package mainVR;

import java.io.File;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.CourseOverview;

public class CourseOverviewController {

	@FXML
	private Button addButton;

	@FXML
	private ListView<String> listViewCourses;
	
	@FXML
	private ListView<ImageView> listViewImage;
	
	@FXML
	private Label labelTitleDetails;

	@FXML
	private Label labelDescriptionDetails;

	@FXML
	private ListView<String> listViewUsers;

	@FXML
	private ListView<IntegerProperty> listViewTeachers;

	@FXML
	private ListView<IntegerProperty> listViewStudents;

	@FXML
	private Button buttonAddTeacher;

	@FXML
	private Button buttonRemoveTeacher;

	@FXML
	private Button buttonAddStudent;

	@FXML
	private Button buttonRemoveStudent;

	private MainAPP mainAPP = new MainAPP();

	private static ObservableList<CourseOverview> coursesOverview = FXCollections.observableArrayList();

	private static ObservableList<CourseOverview> userOverview = FXCollections.observableArrayList();

	public CourseOverviewController(){}

	@FXML
	private void initialize(){
		System.out.println("initialize");
		coursesOverview = mainAPP.getCourseObservableList();
		userOverview = mainAPP.getUserObservableList();
		
		getListCourse(listViewCourses);
		getListButton(listViewImage);
		//		getAllStudents(listViewStudents);
		//		getAllTeachers(listViewTeachers);
		getAllUsers(listViewUsers);
	}

	public void setMainAPP(MainAPP mainAPP){
		this.mainAPP = mainAPP;
	}
	
	public void getListCourse(ListView<String> listViewCourses){
		ObservableList<String> titleList = FXCollections.observableArrayList();
		for(CourseOverview string: coursesOverview) {
			System.out.println(string.getDescription());
			titleList.add(string.getTitle());
		}
		listViewCourses.setItems(titleList);
	}
	public void getListButton(ListView<ImageView> listViewImage){
		ObservableList<ImageView> imageList = FXCollections.observableArrayList();
		for(int i=0; i<coursesOverview.size(); i++) {
			ImageView imageViewDelete = new ImageView();
			File file = new File("./src/main/resources/Image/trash.png");
			Image image = new Image(file.toURI().toString());
			imageViewDelete.setImage(image);
			imageViewDelete.setPreserveRatio(true);
			imageViewDelete.setFitHeight(17);
			imageViewDelete.setFitWidth(19);
			imageList.add(imageViewDelete);
		}
		listViewImage.setItems(imageList);
	}
	
	
	
//	public static void getlistCourse(GridPane gridPaneList){
//		int i = 0;
//		for(CourseOverview course: coursesOverview){
//			Label prueba = new Label();
//			prueba.setFont(new Font("Calibri Light", 15));
//			prueba.setMinHeight(10);
//			prueba.setText(course.getTitle());
//			ImageView imageViewDelete = new ImageView();
//			File file = new File("./src/main/resources/Image/trash.png\"");
//			Image image = new Image(file.toURI().toString());
//			imageViewDelete.setImage(image);
//			imageViewDelete.maxHeight(17);
//			imageViewDelete.maxWidth(19);
//			gridPaneList.add(prueba,0, i);
////			gridPaneList.add(imageViewDelete,1, i);
////			gridPaneList.getColumnConstraints().set(0, new ColumnConstraints(299));
////			gridPaneList.getColumnConstraints().set(1, new ColumnConstraints(27));
//			
//			i++;
//		}
//	}

	public void getAllUsers(ListView<String> listViewUsers){
		ObservableList<String> userNameList = FXCollections.observableArrayList();
		for(CourseOverview string: userOverview) {
			userNameList.add(string.getUserName());
		}
		listViewUsers.setItems(userNameList);
	}

	public void getAllTeachers(ListView<IntegerProperty> listViewTeachers){
		for(CourseOverview course: coursesOverview) {
			listViewTeachers.setItems(course.getTeachers());
		}
	}

	public void getAllStudents(ListView<IntegerProperty> listViewStudents){
		for(CourseOverview course: coursesOverview) {
			listViewStudents.setItems(course.getStudents());
		}
	}




}
