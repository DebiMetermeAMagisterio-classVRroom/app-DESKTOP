package mainVR;

import constants.ExceptionConstants;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Course;

public class CourseEditDialogController {
	
	@FXML
	private TextField titleField;
	
	@FXML
	private TextField descriptionField;
	
	private Stage dialogStage;
	private Course course = new Course();
	private boolean okClicked = false;
	private ExceptionConstants exceptionConstants;
	
	@FXML
	private void initialize() {	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setCourse(Course course) {
		this.course = course;
		titleField.setText(course.getTitle());
		descriptionField.setText(course.getDescription());
	}
	
	public boolean isOkClicked() {
		return okClicked;
	}
	
	@FXML
	private void handleOk() {
		if(isInputValid()) {
			course.setTitle(titleField.getText());
			course.setDescription(descriptionField.getText());
			okClicked = true;
			dialogStage.close();
		}
	}
	
	@FXML 
	private void handleCancel() {
		dialogStage.close();
	}
	
	private boolean isInputValid() {
		String[] errorMessage = null;
		if (titleField.getText() == null || titleField.getText().length()==0) {
			errorMessage = exceptionConstants.NO_VALID_TITLE.split("-");
		}
		if (descriptionField.getText() == null || descriptionField.getText().length()==0) {
			errorMessage = exceptionConstants.NO_VALID_DESCRIPTION.split("-");
		}
		if (errorMessage == null) {
			return true;
		}else {
			Alert alert = new Alert(AlertType.ERROR);
	    	alert.setTitle(errorMessage[0]);
	    	alert.setHeaderText(errorMessage[1]);
	    	alert.setContentText(errorMessage[2]);
	    	alert.showAndWait();
        return false;
		}
	}
}
