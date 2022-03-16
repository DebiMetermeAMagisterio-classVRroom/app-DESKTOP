package Model;

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
	
	private String id;
	
	private String title;
	
	private String description;
	
	private Subscriber subscriber;
	
	private Set<Element> elements;
	
	private Set<Task> tasks;
	
	private Set<VrTask> vrTasks;
	
}
