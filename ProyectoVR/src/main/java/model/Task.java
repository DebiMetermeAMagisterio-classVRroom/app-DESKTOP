package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
public class Task {

	@BsonId
	@BsonProperty("ID")
	private Long ID;

	@BsonProperty("type")
	private String type;

	@BsonProperty("title")
	private String title;

	@BsonProperty("description")
	private String description;

	@BsonProperty("order")
	private int order;

	private List<Upload> uploads;

	@BsonCreator
	public Task(@BsonProperty("ID") Long ID,@BsonProperty("type") String type,@BsonProperty("title") String title,@BsonProperty("description") String description,@BsonProperty("order") int order,@BsonProperty("uploads") List<Upload> uploads) {
		this.ID = ID;
		this.type = type;
		this.title = title;
		this.description = description;
		this.order = order;
		this.uploads = uploads;
	}
}
