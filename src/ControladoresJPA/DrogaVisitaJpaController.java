package ControladoresJPA;

import ControladoresJPA.exceptions.NonexistentEntityException;
import Modelo.DrogaVisita;
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
public class DrogaVisitaJpaController implements Serializable {

    public DrogaVisitaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DrogaVisita drogaVisita) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(drogaVisita);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DrogaVisita drogaVisita) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            drogaVisita = em.merge(drogaVisita);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = drogaVisita.getId();
                if (findDrogaVisita(id) == null) {
                    throw new NonexistentEntityException("The drogaVisita with id " + id + " no longer exists.");
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
            DrogaVisita drogaVisita;
            try {
                drogaVisita = em.getReference(DrogaVisita.class, id);
                drogaVisita.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The drogaVisita with id " + id + " no longer exists.", enfe);
            }
            em.remove(drogaVisita);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DrogaVisita> findDrogaVisitaEntities() {
        return findDrogaVisitaEntities(true, -1, -1);
    }

    public List<DrogaVisita> findDrogaVisitaEntities(int maxResults, int firstResult) {
        return findDrogaVisitaEntities(false, maxResults, firstResult);
    }

    private List<DrogaVisita> findDrogaVisitaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DrogaVisita.class));
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

    public DrogaVisita findDrogaVisita(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DrogaVisita.class, id);
        } finally {
            em.close();
        }
    }

    public int getDrogaVisitaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DrogaVisita> rt = cq.from(DrogaVisita.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
