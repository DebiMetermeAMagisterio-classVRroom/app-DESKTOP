package model;

import java.util.List;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonRepresentation;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

	@BsonId()
	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;
	
	@BsonProperty(value = "title")
	private String title;
	
	@BsonProperty(value = "description")
	private String description;
	
	@BsonProperty(value = "subscribers")
	private Subscriber subscriber;
	
	@BsonProperty(value = "elements")
	private List<Element> elements;
	
	@BsonProperty(value = "tasks")
	private List<Task> tasks;
	
	@BsonProperty(value = "vr_tasks")
	private List<VrTask> vrTasks;

	@BsonCreator
	public Course(@BsonProperty("title") String title) {
		this.title = title;
	}
}