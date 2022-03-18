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
	
	private Long studentID;
	
	private Map<String, String> position_data;
	
	private Map<String, String> autograde;
	
	private int grade;
	
	private String feedback;
}
