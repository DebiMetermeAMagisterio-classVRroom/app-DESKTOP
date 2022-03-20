package model;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
//	@JsonAlias({"_id", "$oid"})
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