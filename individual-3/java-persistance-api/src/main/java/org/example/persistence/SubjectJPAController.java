package org.example.persistence;

import org.example.logic.Subject;
import java.io.Serializable;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class SubjectJPAController implements Serializable {
    
    public SubjectJPAController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public SubjectJPAController() {
        this.emf = jakarta.persistence.Persistence.createEntityManagerFactory("testPU");
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Subject findSubject(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Subject.class, id);
        } finally {
            em.close();
        }
    }

    public void create(Subject subject) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(subject);
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

            Subject subject = em.find(Subject.class, id);
            if (subject == null) {
                throw new RuntimeException("The subject with id " + id + " no longer exists.");
            }

            em.remove(subject);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Subject subject) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(subject);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw new RuntimeException("Error editing subject with id " + subject.getId(), ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Subject> findSubjectEntities() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Subject s", Subject.class).getResultList();
        } finally {
            em.close();
        }
    }
    
}
