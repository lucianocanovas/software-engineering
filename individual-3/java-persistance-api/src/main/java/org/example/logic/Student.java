package org.example.logic;

import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    private String name;
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Course course;

    public Student() {
    }

    public Student(String name, Date dateOfBirth, Course course) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}