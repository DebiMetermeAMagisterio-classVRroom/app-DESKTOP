package mainVR;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.json.simple.parser.ParseException;

import Configuration.ConfigMongoConnection;
import ListViewConfig.CourseListViewCellFactory;
import ListViewConfig.StudentListViewCellFactory;
import ListViewConfig.TeacherListViewCellFactory;
import ListViewConfig.UserListViewCellFactory;
import constants.ExceptionConstants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.Course;
import model.Student;
import model.Teacher;
import model.User;
import utilities.ReadJSONAtlas;

public class CourseOverviewController {

	@FXML
	private Button addButton;

	@FXML
	private Label labelTitleDetails;

	@FXML
	private Label labelDescriptionDetails;

	@FXML
	private ListView<Course> listViewCourses;

	@FXML
	private VBox listViewImage;

	@FXML
	private ListView<User> listViewUsers;

	@FXML
	private ListView<User> listViewTeachers;

	@FXML
	private ListView<User> listViewStudents;

	@FXML
	private Button buttonAddTeacher;

	@FXML
	private Button buttonRemoveTeacher;

	@FXML
	private Button buttonAddStudent;

	@FXML
	private Button buttonRemoveStudent;

	private MainAPP mainAPP = new MainAPP();

	private ObservableList<Course> courses = FXCollections.observableArrayList();

	private ObservableList<User> users = FXCollections.observableArrayList();

	private ObservableList<Button> buttons =  FXCollections.observableArrayList();
	
	private ExceptionConstants exceptionConstants = new ExceptionConstants();

	private ConfigMongoConnection configMongoConnection = new ConfigMongoConnection();

	private ReadJSONAtlas readJSONAtlas = new ReadJSONAtlas();

	public CourseOverviewController(){}


	@FXML
	private void initialize() throws IOException{
		courses = mainAPP.getCourses();
		users = mainAPP.getUsers();
		setListCourse();
		setListButton();
		setAllUsers();

		showCourseDetails(null);

		listViewCourses.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showCourseDetails(newValue));

	}

	public void setMainAPP(MainAPP mainAPP){
		this.mainAPP = mainAPP;
	}

	public void setListCourse() throws IOException{
		listViewCourses.setItems(courses);
		listViewCourses.setCellFactory(new CourseListViewCellFactory());
	}

	public void setListButton(){
		for(int i=0; i<listViewCourses.getItems().size(); i++) {
			Button button = new Button();
			ImageView imageViewDelete = new ImageView();
			File file = new File("./src/main/resources/Image/trash.png");
			Image image = new Image(file.toURI().toString());
			button.setGraphic(new ImageView(image));
			imageViewDelete.setImage(image);
			imageViewDelete.setPreserveRatio(true);
			imageViewDelete.setFitHeight(17);
			imageViewDelete.setFitWidth(19);
			button.setGraphic(imageViewDelete);
			button.setPadding(new Insets(1, 1, 1, 1));
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						handleDeleteCourseOverview(button.getId());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			buttons.add(button);
		}
		listViewImage.getChildren().addAll(buttons);
	}

	public void setAllUsers() throws IOException{
		listViewUsers.setItems(users);
		listViewUsers.setCellFactory(new UserListViewCellFactory());
	}

	public void setAllTeachers(ObservableList<User> teachers){
		listViewTeachers.setItems(teachers);
		listViewTeachers.setCellFactory(new TeacherListViewCellFactory());
	}

	public void setAllStudents(ObservableList<User> students){
		listViewStudents.setItems(students);
		listViewStudents.setCellFactory(new StudentListViewCellFactory());
	}

	private void showCourseDetails(Course course) {
		ObservableList<User> teachers = FXCollections.observableArrayList();
		ObservableList<User> students = FXCollections.observableArrayList();
//
		if(course != null) {
			for(User user: users) {
				for(Teacher teacher: course.getSubscriber().getTeachers()) {
					if (teacher.getId() == user.getId()) {
						teachers.add(user);
					}
				}

				for(Student student: course.getSubscriber().getStudents()) {
					if(student.getId() == user.getId()) {
						students.add(user);
					}
				}
			}
			labelTitleDetails.setText(course.getTitle());
			labelDescriptionDetails.setText(course.getDescription());
			setAllTeachers(teachers);
			setAllStudents(students);
		}else {
			labelTitleDetails.setText("");
			labelDescriptionDetails.setText("");
			setAllTeachers(null);
			setAllStudents(null);
		}
	}

	private void handleDeleteCourseOverview(String id) throws IOException {
		String[] errorMessage = exceptionConstants.CONFIRMATION_DELETE_DATA.split("-");

		int selectedIndex = listViewCourses.getSelectionModel().getSelectedIndex();

		if(selectedIndex >= 0) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle(errorMessage[0]);
			alert.setHeaderText(errorMessage[1]);
			alert.setContentText(errorMessage[2]);
			Optional<ButtonType> action = alert.showAndWait();
			if(action.get() == ButtonType.OK) {
				for(Course course: courses) {
					if(course.getId().equals(listViewCourses.getSelectionModel().getSelectedItem().getId())) {
						configMongoConnection.deleteDocument(course.getTitle());
						//						mainAPP.getCourses().remove(course.getId());
						listViewImage.getChildren().clear();
					}
				}
				listViewCourses.getItems().remove(selectedIndex);
				setListButton();
			}
		}else {
			errorMessage = exceptionConstants.NO_VALID_DATA.split("-");
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle(errorMessage[0]);
			alert.setHeaderText(errorMessage[1]);
			alert.setContentText(errorMessage[2]);
			alert.showAndWait();
		}
	}

	@FXML
	private void handleEditTeacher() {
		String[] errorMessage = exceptionConstants.NO_USER_SELECTED.split("-");
		Course selectedCourse = listViewCourses.getSelectionModel().getSelectedItem();
		User selectedUser = listViewUsers.getSelectionModel().getSelectedItem();
		if(selectedCourse != null) {
			if(selectedUser != null) {
				for(User user: listViewUsers.getSelectionModel().getSelectedItems()) {
					if(listViewTeachers.getItems().contains(user) || listViewStudents.getItems().contains(user)) {
						errorMessage = exceptionConstants.USER_DUPLICATED.split("-");
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle(errorMessage[0]);
						alert.setHeaderText(errorMessage[1]);
						alert.setContentText(errorMessage[2]);
						alert.showAndWait();
					}
					else {	
						listViewTeachers.getItems().add(selectedUser);
						configMongoConnection.updateTeacher(selectedCourse.getTitle(), user.getId());
					}
				}
			} else {
				errorMessage = exceptionConstants.NO_USER_SELECTED.split("-");
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle(errorMessage[0]);
				alert.setHeaderText(errorMessage[1]);
				alert.setContentText(errorMessage[2]);
				alert.showAndWait();
			}
		}else {
			errorMessage = exceptionConstants.NO_COURSE_SELECTED.split("-");
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(errorMessage[0]);
			alert.setHeaderText(errorMessage[1]);
			alert.setContentText(errorMessage[2]);
			alert.showAndWait();
		}
	}
	
	@FXML
	private void handleEditStudent() {
		String[] errorMessage = exceptionConstants.NO_USER_SELECTED.split("-");
		Course selectedCourse = listViewCourses.getSelectionModel().getSelectedItem();
		User selectedUser = listViewUsers.getSelectionModel().getSelectedItem();
		if(selectedCourse != null) {
			if(selectedUser != null) {
				for(User user: listViewUsers.getSelectionModel().getSelectedItems()) {
					if(listViewTeachers.getItems().contains(user) || listViewStudents.getItems().contains(user)) {
						errorMessage = exceptionConstants.USER_DUPLICATED.split("-");
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle(errorMessage[0]);
						alert.setHeaderText(errorMessage[1]);
						alert.setContentText(errorMessage[2]);
						alert.showAndWait();
					}
					else {	
						configMongoConnection.updateStudent(selectedCourse.getTitle(), user.getId());
						listViewStudents.getItems().add(selectedUser);
					}
				}
			} else {
				errorMessage = exceptionConstants.NO_USER_SELECTED.split("-");
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle(errorMessage[0]);
				alert.setHeaderText(errorMessage[1]);
				alert.setContentText(errorMessage[2]);
				alert.showAndWait();
			}
		}else {
			errorMessage = exceptionConstants.NO_COURSE_SELECTED.split("-");
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(errorMessage[0]);
			alert.setHeaderText(errorMessage[1]);
			alert.setContentText(errorMessage[2]);
			alert.showAndWait();
		}
	}
	
	@FXML
	private void handleRemoveTeacher() {
		String[] errorMessage = exceptionConstants.NO_USER_SELECTED.split("-");
		Course selectedCourse = listViewCourses.getSelectionModel().getSelectedItem();
		User selectedTeacher = listViewTeachers.getSelectionModel().getSelectedItem();
		if(selectedCourse != null) {
			if(selectedTeacher != null) {
				listViewTeachers.getItems().remove(selectedTeacher);
				configMongoConnection.deleteTeacher(selectedCourse.getTitle(), selectedTeacher.getId());
			} else {
//				errorMessage = exceptionConstants.NO_USER_SELECTED.split("-");
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle(errorMessage[0]);
				alert.setHeaderText(errorMessage[1]);
				alert.setContentText(errorMessage[2]);
				alert.showAndWait();
			}
		}else {
			errorMessage = exceptionConstants.NO_COURSE_SELECTED.split("-");
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(errorMessage[0]);
			alert.setHeaderText(errorMessage[1]);
			alert.setContentText(errorMessage[2]);

			alert.showAndWait();
		}
	}
	
	@FXML
	private void handleRemoveStudent() {
		String[] errorMessage = exceptionConstants.NO_USER_SELECTED.split("-");
		Course selectedCourse = listViewCourses.getSelectionModel().getSelectedItem();
		User selectedStudent = listViewStudents.getSelectionModel().getSelectedItem();
		if(selectedCourse != null) {
			if(selectedStudent != null) {
				listViewStudents.getSelectionModel().getSelectedItems().remove(selectedStudent.getId());
				configMongoConnection.deleteStudent(selectedCourse.getTitle(), selectedStudent.getId());
			}else {
//				errorMessage = exceptionConstants.NO_USER_SELECTED.split("-");
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle(errorMessage[0]);
				alert.setHeaderText(errorMessage[1]);
				alert.setContentText(errorMessage[2]);
				alert.showAndWait();
			}
		}else {
			errorMessage = exceptionConstants.NO_COURSE_SELECTED.split("-");
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle(errorMessage[0]);
			alert.setHeaderText(errorMessage[1]);
			alert.setContentText(errorMessage[2]);
			alert.showAndWait();
		}
	}
	
	

	@FXML
	private void handleNewCourse() throws IOException, ParseException {
		Course tempCourse = new Course();
		boolean okClicked = mainAPP.showCourseEditDialog(tempCourse);

		if (okClicked) {
			configMongoConnection.insertCourse(tempCourse.getTitle(), tempCourse.getDescription());
			tempCourse = readJSONAtlas.getCourse(tempCourse.getTitle());
			mainAPP.getCourses().add(tempCourse);
			listViewImage.getChildren().clear();
			setListButton();
		} 

	}

}
