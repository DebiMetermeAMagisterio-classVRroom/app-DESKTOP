package model;

import java.util.Set;

import org.bson.codecs.pojo.annotations.BsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
	
	private Long ID;
	
	private String type;
	
	private String title;

	private String description;
	
	private int order;
	
	private Set<Upload> uploads;
	
}
