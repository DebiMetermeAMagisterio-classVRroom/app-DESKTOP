package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {

    @BsonId
    @BsonProperty(value = "id")
    private int id;

    @BsonProperty(value = "first_name")
    private String firstName;

    @BsonProperty(value = "last_name")
    private String lastName;

    @BsonProperty(value = "password")
    private String password;

    @BsonProperty(value = "email")
    private String email;

    @BsonProperty("role")
    private String role;

    @BsonProperty("session_token")
    private String sessionToken;

    @BsonProperty("session_token_expiration")
    private Date sessionTokenExpiration;

    @BsonCreator
    public User(@BsonProperty("id") int id, @BsonProperty("first_name") String firstName, @BsonProperty("last_name") String lastName, @BsonProperty("password") String password, @BsonProperty("email") String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }

}
