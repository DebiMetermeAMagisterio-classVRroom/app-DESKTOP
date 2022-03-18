package model;

import java.util.Set;

import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.codecs.pojo.annotations.BsonRepresentation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
	
//	@BsonId()
//	@BsonRepresentation(BsonType.OBJECT_ID)
	private String id;
	
//	@BsonProperty(value = "title")
	private String title;
	
//	@BsonProperty(value = "description")
	private String description;
	
//	@BsonProperty(value = "subscribers")
//	@BsonRepresentation(BsonType.OBJECT_ID)
//	@BsonIgnore
	private Subscriber subscriber;
	
//	@BsonProperty(value = "elements")
//	@BsonIgnore
	private Set<Element> elements;
	
//	@BsonProperty(value = "tasks")
//	@BsonIgnore	
	private Set<Task> tasks;
	
//	@BsonProperty(value = "vr_tasks")
//	@BsonIgnore
	private Set<VrTask> vrTasks;
	
}
