package model;

import org.bson.codecs.pojo.annotations.BsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Upload {

	@BsonProperty("studentID")
	private Long studentID;

	@BsonProperty("text")
	private String text;

	@BsonProperty("file")
	private String file;

	@BsonProperty("grade")
	private int grade;

	@BsonProperty("feedback")
	private String feedback;
	
}
