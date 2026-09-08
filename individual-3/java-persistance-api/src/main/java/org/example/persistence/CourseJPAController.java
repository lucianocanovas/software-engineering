package org.example.persistence;

import org.example.logic.Course;
import java.io.Serializable;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class CourseJPAController implements Serializable {
    
    public CourseJPAController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public CourseJPAController() {
        this.emf = jakarta.persistence.Persistence.createEntityManagerFactory("testPU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Course findCourse(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Course.class, id);
        } finally {
            em.close();
        }
    }

    public void create(Course course) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(course);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Course course = em.find(Course.class, id);
            if (course == null) {
                throw new RuntimeException("The course with id " + id + " no longer exists.");
            }

            em.remove(course);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Course course) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(course);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw new RuntimeException("Error editing course with id " + course.getId(), ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Course> findCourseEntities() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Course c", Course.class).getResultList();
        } finally {
            em.close();
        }
    }
}
