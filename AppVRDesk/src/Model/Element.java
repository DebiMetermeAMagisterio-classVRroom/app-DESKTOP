package Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Element {
	
	private Long ID;
	
	private String type;
	
	private String title;

	private String description;
	
	private int order;
	
	private String contents;
	
	private String file;

}
