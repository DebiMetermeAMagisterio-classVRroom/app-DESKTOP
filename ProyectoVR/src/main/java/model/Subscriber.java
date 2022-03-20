package model;

import java.util.List;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class Subscriber {
	@BsonProperty("teachers")
	private List<Teacher> teachers;

	@BsonProperty("students")
	private List<Student> students;

	@BsonCreator
	public Subscriber(@BsonProperty("teachers") List<Teacher> teachers,@BsonProperty("students") List<Student> students){
		this.teachers = teachers;
		this.students = students;
	}

}
