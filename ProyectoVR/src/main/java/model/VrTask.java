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
public class VrTask {
	
	private Long ID;
	
	private String title;

	private String description;
	
	private int VRexID;
	
	private int versionID;
	
	private int pollID;
	
	private Set<Completion> completions;
}
