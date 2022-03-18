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
public class Subscriber {
	
	private Set<Teacher> teachers;
	
	private Set<Student> students;

}
