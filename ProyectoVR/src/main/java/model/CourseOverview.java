package model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseOverview {

    private StringProperty title;
    private StringProperty description;
    private StringProperty userName;
    private ListProperty<IntegerProperty> teachers;
    private ListProperty<IntegerProperty> students;

    public CourseOverview(String title, String description, List<IntegerProperty> teachers, List<IntegerProperty> students) {
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        ObservableList<IntegerProperty> observableListTeacher = FXCollections.observableArrayList(teachers);
        this.teachers = new SimpleListProperty<>(observableListTeacher);
        ObservableList<IntegerProperty> observableListStudent = FXCollections.observableArrayList(students);
        this.students = new SimpleListProperty<>(observableListStudent);
    }

    public CourseOverview(String userName) {
        this.userName = new SimpleStringProperty(userName);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public ObservableList<IntegerProperty> getTeachers() {
        return teachers.get();
    }

    public ListProperty<IntegerProperty> teachersProperty() {
        return teachers;
    }

    public void setTeachers(ObservableList<IntegerProperty> teachers) {
        this.teachers.set(teachers);
    }

    public ObservableList<IntegerProperty> getStudents() {
        return students.get();
    }

    public ListProperty<IntegerProperty> studentsProperty() {
        return students;
    }

    public void setStudents(ObservableList<IntegerProperty> students) {
        this.students.set(students);
    }
}
