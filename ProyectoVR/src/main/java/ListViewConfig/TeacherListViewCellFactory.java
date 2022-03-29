package ListViewConfig;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.User;

public class TeacherListViewCellFactory implements Callback<ListView<User>,ListCell<User> > {

	@Override
	public ListCell<User> call(ListView<User> param) {
		return new ListCell<User>() {
			@Override
            public void updateItem(User user, boolean empty) {
                super.updateItem(user, empty);
                if (empty || user == null) {
                    setText(null);
                } else {
                	setId(String.valueOf(user.getId()));
                	setText(user.getFirstName().substring(1, user.getFirstName().length()-1) + " " +user.getLastName().substring(1, user.getLastName().length()-1));
                }
            }
		};
	}

}
