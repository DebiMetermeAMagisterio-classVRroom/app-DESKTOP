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
	
	private Long studentID;
	
	private String text;
	
	private String file;
	
	private int grade;
	
	private String feedback;
	
}
