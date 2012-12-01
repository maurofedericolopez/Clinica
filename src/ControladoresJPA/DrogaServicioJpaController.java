package ControladoresJPA;

import ControladoresJPA.exceptions.NonexistentEntityException;
import Modelo.DrogaServicio;
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
public class DrogaServicioJpaController implements Serializable {

    public DrogaServicioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DrogaServicio drogaServicio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(drogaServicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DrogaServicio drogaServicio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            drogaServicio = em.merge(drogaServicio);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = drogaServicio.getId();
                if (findDrogaServicio(id) == null) {
                    throw new NonexistentEntityException("The drogaServicio with id " + id + " no longer exists.");
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
            DrogaServicio drogaServicio;
            try {
                drogaServicio = em.getReference(DrogaServicio.class, id);
                drogaServicio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The drogaServicio with id " + id + " no longer exists.", enfe);
            }
            em.remove(drogaServicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DrogaServicio> findDrogaServicioEntities() {
        return findDrogaServicioEntities(true, -1, -1);
    }

    public List<DrogaServicio> findDrogaServicioEntities(int maxResults, int firstResult) {
        return findDrogaServicioEntities(false, maxResults, firstResult);
    }

    private List<DrogaServicio> findDrogaServicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DrogaServicio.class));
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

    public DrogaServicio findDrogaServicio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DrogaServicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getDrogaServicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DrogaServicio> rt = cq.from(DrogaServicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
