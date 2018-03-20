/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import entities.Intervention;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Azergante
 */
public class InterventionJpaController implements Serializable {

    public InterventionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Intervention intervention) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(intervention);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Intervention intervention) throws NonexistentEntityException, Exception {
        EntityManager em = null;
       
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Intervention intervention;
            try {
                intervention = em.getReference(Intervention.class, id);
                intervention.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The intervention with id " + id + " no longer exists.", enfe);
            }
            em.remove(intervention);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Intervention> findInterventionEntities() {
        return findInterventionEntities(true, -1, -1);
    }

    public List<Intervention> findInterventionEntities(int maxResults, int firstResult) {
        return findInterventionEntities(false, maxResults, firstResult);
    }

    private List<Intervention> findInterventionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Intervention.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Intervention findIntervention(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Intervention.class, id);
        } finally {
            em.close();
        }
    }

    public int getInterventionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Intervention> rt = cq.from(Intervention.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
