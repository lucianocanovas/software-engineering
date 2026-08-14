package org.example;

import java.util.List;
import org.example.logic.Subject;
import org.example.logic.Student;
import org.example.logic.Course;
import org.example.logic.Controller;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();

        // Delete all students
        for (Student student : controller.getAllStudents()) {
            controller.deleteStudent(student.getId());
        }

        // Create a new student
        Subject subject1 = new Subject("Mathematics");
        Subject subject2 = new Subject("Physics");
        Course course = new Course("Computer Science", List.of(subject1, subject2));
        Student student = new Student("John Doe", new java.util.Date(), course);
        controller.createStudent(student);

        // Retrieve and print the student
        Student retrievedStudent = controller.getStudent(student.getId());
        System.out.println("Retrieved Student: " + retrievedStudent.getName() + ", Course: " + retrievedStudent.getCourse().getName());

        // Edit the student
        retrievedStudent.setName("Jane Doe");
        controller.editStudent(retrievedStudent);

        // Retrieve and print the edited student
        Student editedStudent = controller.getStudent(retrievedStudent.getId());
        System.out.println("Edited Student: " + editedStudent.getName() + ", Course: " + editedStudent.getCourse().getName());
        
        // Retrieve all the subjects of the course
        List<Subject> subjects = editedStudent.getCourse().getSubjects();
        System.out.println("Subjects of the course:");
        for (Subject subject : subjects) {
            System.out.println("- " + subject.getName());
        }
    }
}