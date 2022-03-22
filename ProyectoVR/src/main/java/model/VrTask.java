package model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

@Data
@NoArgsConstructor
@Builder
public class VrTask {
	@BsonId
	@BsonProperty("ID")
	private int ID;

	@BsonProperty("title")
	private String title;

	@BsonProperty("description")
	private String description;

	@BsonProperty("VRexID")
	private int VRexID;

	@BsonProperty("versionID")
	private int versionID;

	@BsonProperty("pollID")
	private int pollID;

	private List<Completion> completions;

	@BsonCreator
	public VrTask(@BsonProperty("ID") int ID,@BsonProperty("title") String title,@BsonProperty("description") String description,@BsonProperty("VRexID") int VRexID,@BsonProperty("versionID") int versionID,@BsonProperty("pollID") int pollID,@BsonProperty("completions") List<Completion> completions) {
		this.ID = ID;
		this.title = title;
		this.description = description;
		this.VRexID = VRexID;
		this.versionID = versionID;
		this.pollID = pollID;
		this.completions = completions;
	}
}
