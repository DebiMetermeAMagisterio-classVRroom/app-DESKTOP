package Model;

import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Course1 {
	
	private StringProperty id;
	
	private StringProperty title;
	
	private StringProperty description;
	
	private Subscriber subscriber;
	
	private Set<Element> elements;
	
	private Set<Task> tasks;
	
	private Set<VrTask> vrTasks;
	
	public Course1() {
		this(null, null, null);
	}
	
	public Course1(String id, String title, String description) {
		this.id = new SimpleStringProperty(id);
		this.title = new SimpleStringProperty(title);
		this.description = new SimpleStringProperty(description);
	}
	
	public String getId() {
		return id.get();
	}
	
	public void setId(String id) {
		this.id.set(id);
	}
	
	public StringProperty idProperty() {
		return id;
	}
	
	public String getTitle() {
		return title.get();
	}
	
	public void setTitle(String title) {
		this.title.set(title);
	}
	
	public StringProperty titleProperty() {
		return title;
	}
	
	public String getDescription() {
		 return description.get();
	}
	
	public void setDescription(String description) {
		this.description.set(description);
	}
	
	public StringProperty descriptionProperty() {
		return description;
	}
	
}
