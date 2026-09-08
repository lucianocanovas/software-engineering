package org.example.logic;

import java.util.List;
import jakarta.persistence.*;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Subject> subjects;

    public Course() {
    }

    public Course(String name, List<Subject> subjects) {
        this.name = name;
        setSubjects(subjects);
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

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
        if (this.subjects != null) {
            for (Subject subject : this.subjects) {
                subject.setCourse(this);
            }
        }
    }

}
