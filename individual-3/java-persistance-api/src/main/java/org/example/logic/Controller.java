package org.example.logic;

import java.util.List;
import org.example.persistence.PersistenceController;

public class Controller {
    
    PersistenceController persistenceController = new PersistenceController();

    public Student getStudent(int id) {
        return persistenceController.getStudent(id);
    }

    public List<Student> getAllStudents() {
        return persistenceController.getAllStudents();
    }

    public void createStudent(Student student) {
        persistenceController.createStudent(student);
    }
    
    public void deleteStudent(int id) {
        persistenceController.deleteStudent(id);
    }

    public void editStudent(Student student) {
        persistenceController.editStudent(student);
    }

    public Course getCourse(int id) {
        return persistenceController.getCourse(id);
    }

    public List<Course> getAllCourses() {
        return persistenceController.getAllCourses();
    }

    public void createCourse(Course course) {
        persistenceController.createCourse(course);
    }

    public void deleteCourse(int id) {
        persistenceController.deleteCourse(id);
    }

    public void editCourse(Course course) {
        persistenceController.editCourse(course);
    }

}
