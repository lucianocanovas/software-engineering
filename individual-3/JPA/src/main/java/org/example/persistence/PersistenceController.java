package org.example.persistence;

import java.util.List;
import org.example.logic.Student;
import org.example.logic.Course;
import org.example.logic.Subject;

public class PersistenceController {
    StudentJPAController studentController = new StudentJPAController();
    CourseJPAController courseController = new CourseJPAController();
    SubjectJPAController subjectController = new SubjectJPAController();

    public Student getStudent(int id) {
        return studentController.findStudent(id);
    }

    public List<Student> getAllStudents() {
        return studentController.findStudentEntities();
    }

    public void createStudent(Student student) {
        studentController.create(student);
    }

    public void deleteStudent(int id) {
        try {
            studentController.destroy(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting student with id " + id, e);
        }
    }

    public void editStudent(Student student) {
        try {
            studentController.edit(student);
        } catch (Exception e) {
            throw new RuntimeException("Error editing student with id " + student.getId(), e);
        }
    }

    public Course getCourse(int id) {
        return courseController.findCourse(id);
    }

    public List<Course> getAllCourses() {
        return courseController.findCourseEntities();
    }

    public void createCourse(Course course) {
        courseController.create(course);
    }

    public void deleteCourse(int id) {
        try {
            courseController.destroy(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting course with id " + id, e);
        }
    }

    public void editCourse(Course course) {
        try {
            courseController.edit(course);
        } catch (Exception e) {
            throw new RuntimeException("Error editing course with id " + course.getId(), e);
        }
    }

    public Subject getSubject(int id) {
        return subjectController.findSubject(id);
    }

    public List<Subject> getAllSubjects() {
        return subjectController.findSubjectEntities();
    }

    public void createSubject(Subject subject) {
        subjectController.create(subject);
    }

    public void deleteSubject(int id) {
        try {
            subjectController.destroy(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting subject with id " + id, e);
        }
    }

    public void editSubject(Subject subject) {
        try {
            subjectController.edit(subject);
        } catch (Exception e) {
            throw new RuntimeException("Error editing subject with id " + subject.getId(), e);
        }
    }

}
