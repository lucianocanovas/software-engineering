package org.example.persistence;

import org.example.logic.Student;
import java.io.Serializable;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;

public class StudentJPAController implements Serializable {

    public StudentJPAController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public StudentJPAController() {
        this.emf = jakarta.persistence.Persistence.createEntityManagerFactory("testPU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Student findStudent(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT s FROM Student s " +
                    "LEFT JOIN FETCH s.course c " +
                    "LEFT JOIN FETCH c.subjects " +
                    "WHERE s.id = :id", Student.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void create(Student student) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(student);
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

            Student student = em.find(Student.class, id);
            if (student == null) {
                throw new RuntimeException("The student with id " + id + " no longer exists.");
            }

            em.remove(student);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Student student) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Student existingStudent = em.find(Student.class, student.getId());
            if (existingStudent == null) {
                throw new RuntimeException("The student with id " + student.getId() + " no longer exists.");
            }

            existingStudent.setName(student.getName());
            existingStudent.setDateOfBirth(student.getDateOfBirth());

            em.merge(existingStudent);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Student> findStudentEntities() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
        } finally {
            em.close();
        }
    }

}