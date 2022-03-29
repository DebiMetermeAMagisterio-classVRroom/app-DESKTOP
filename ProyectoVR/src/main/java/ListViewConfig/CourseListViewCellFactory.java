
package ListViewConfig;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.Course;

public class CourseListViewCellFactory implements Callback<ListView<Course>,ListCell<Course> >{

	@Override
	public ListCell<Course> call(ListView<Course> param) {
		return new ListCell<Course>() {
			@Override
            public void updateItem(Course course, boolean empty) {
                super.updateItem(course, empty);
                if (empty || course == null) {
                    setText(null);
                } else {
                    setText(course.getTitle());
                    setId(course.getId());
                }
            }
		};
	}

}
