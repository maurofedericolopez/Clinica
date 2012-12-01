package ControladoresJPA;

import ControladoresJPA.exceptions.NonexistentEntityException;
import Modelo.Droga;
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
 * @author Mauro
 */
public class DrogaJpaController implements Serializable {

    public DrogaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Droga droga) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(droga);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Droga droga) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            droga = em.merge(droga);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = droga.getId();
                if (findDroga(id) == null) {
                    throw new NonexistentEntityException("The droga with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Droga droga;
            try {
                droga = em.getReference(Droga.class, id);
                droga.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The droga with id " + id + " no longer exists.", enfe);
            }
            em.remove(droga);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Droga> findDrogaEntities() {
        return findDrogaEntities(true, -1, -1);
    }

    public List<Droga> findDrogaEntities(int maxResults, int firstResult) {
        return findDrogaEntities(false, maxResults, firstResult);
    }

    private List<Droga> findDrogaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Droga.class));
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

    public Droga findDroga(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Droga.class, id);
        } finally {
            em.close();
        }
    }

    public int getDrogaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Droga> rt = cq.from(Droga.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
