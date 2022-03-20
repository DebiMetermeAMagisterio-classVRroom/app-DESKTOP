package model;

import java.util.Map;

import org.bson.codecs.pojo.annotations.BsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Completion {

	@BsonProperty("studentID")
	private Long studentID;

	@BsonProperty("position_data")
	private Map<String, String> position_data;

	@BsonProperty("autograde")
	private Map<String, String> autograde;

	@BsonProperty("grade")
	private int grade;

	@BsonProperty("feedback")
	private String feedback;
}
