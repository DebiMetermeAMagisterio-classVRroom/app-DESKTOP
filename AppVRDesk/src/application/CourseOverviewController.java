package application;

import Model.Course1;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CourseOverviewController {
	
	@FXML
	private String idLabel;
	
	@FXML
	private String titleLabel;
	
	@FXML
	private String descriptionLabel;
	
	@FXML
	private TableView<Course1> courseTable;
	
	@FXML
	private TableColumn<Course1, String> titleColumn;
	
	private MainAPP mainAPP;
	
	public CourseOverviewController() {}
	
	private void initialize() {
		titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
	}
	
	public void setMainApp(MainAPP mainApp) {
        this.mainAPP = mainApp;

        // Add observable list data to the table
        System.out.println(mainAPP.getCourseData());
        courseTable.setItems(mainAPP.getCourseData());
    }
		
}
