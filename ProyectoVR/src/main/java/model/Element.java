package model;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
public class Element {
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

	@BsonProperty("contents")
	private String contents;

	@BsonProperty("file")
	private String file;

	@BsonCreator
	public Element(@BsonProperty("ID") Long ID, @BsonProperty("type") String type, @BsonProperty("title") String title,@BsonProperty("description") String description,@BsonProperty("order") int order,@BsonProperty("contents") String contents,@BsonProperty("file") String file) {
		this.ID = ID;
		this.type = type;
		this.title = title;
		this.description = description;
		this.order = order;
		this.contents = contents;
		this.file = file;
	}
}
