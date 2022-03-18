package Configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConfigConstants {	
	
	private final static String DB_USER = "ladyangelVR";
	private final static String DB_PASSWORD = "qnIxw5VJzYUQWPoe";
	private final static String DB_NAME = "vrclassroom";
	private final static String DB_URI = "mongodb+srv://"+DB_USER+":"+DB_PASSWORD+"@proyectovr.jlpld.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";

	public static String getDbUser() {
		return DB_USER;
	}
	public static String getDbPassword() {
		return DB_PASSWORD;
	}
	public static String getDbName() {
		return DB_NAME;
	}
	public static String getDbUri() {
		return DB_URI;
	}
}
