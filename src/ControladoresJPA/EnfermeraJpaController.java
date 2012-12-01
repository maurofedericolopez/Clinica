package ControladoresJPA;

import ControladoresJPA.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Enfermera;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Mauro
 */
public class EnfermeraJpaController implements Serializable {

    public EnfermeraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Enfermera enfermera) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Enfermera enfermeraRel = enfermera.getEnfermera();
            if (enfermeraRel != null) {
                enfermeraRel = em.getReference(enfermeraRel.getClass(), enfermeraRel.getId());
                enfermera.setEnfermera(enfermeraRel);
            }
            em.persist(enfermera);
            if (enfermeraRel != null) {
                Enfermera oldEnfermeraOfEnfermeraRel = enfermeraRel.getEnfermera();
                if (oldEnfermeraOfEnfermeraRel != null) {
                    oldEnfermeraOfEnfermeraRel.setEnfermera(null);
                    oldEnfermeraOfEnfermeraRel = em.merge(oldEnfermeraOfEnfermeraRel);
                }
                enfermeraRel.setEnfermera(enfermera);
                enfermeraRel = em.merge(enfermeraRel);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Enfermera enfermera) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Enfermera persistentEnfermera = em.find(Enfermera.class, enfermera.getId());
            Enfermera enfermeraRelOld = persistentEnfermera.getEnfermera();
            Enfermera enfermeraRelNew = enfermera.getEnfermera();
            if (enfermeraRelNew != null) {
                enfermeraRelNew = em.getReference(enfermeraRelNew.getClass(), enfermeraRelNew.getId());
                enfermera.setEnfermera(enfermeraRelNew);
            }
            enfermera = em.merge(enfermera);
            if (enfermeraRelOld != null && !enfermeraRelOld.equals(enfermeraRelNew)) {
                enfermeraRelOld.setEnfermera(null);
                enfermeraRelOld = em.merge(enfermeraRelOld);
            }
            if (enfermeraRelNew != null && !enfermeraRelNew.equals(enfermeraRelOld)) {
                Enfermera oldEnfermeraOfEnfermeraRel = enfermeraRelNew.getEnfermera();
                if (oldEnfermeraOfEnfermeraRel != null) {
                    oldEnfermeraOfEnfermeraRel.setEnfermera(null);
                    oldEnfermeraOfEnfermeraRel = em.merge(oldEnfermeraOfEnfermeraRel);
                }
                enfermeraRelNew.setEnfermera(enfermera);
                enfermeraRelNew = em.merge(enfermeraRelNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = enfermera.getId();
                if (findEnfermera(id) == null) {
                    throw new NonexistentEntityException("The enfermera with id " + id + " no longer exists.");
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
            Enfermera enfermera;
            try {
                enfermera = em.getReference(Enfermera.class, id);
                enfermera.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The enfermera with id " + id + " no longer exists.", enfe);
            }
            Enfermera enfermeraRel = enfermera.getEnfermera();
            if (enfermeraRel != null) {
                enfermeraRel.setEnfermera(null);
                enfermeraRel = em.merge(enfermeraRel);
            }
            em.remove(enfermera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Enfermera> findEnfermeraEntities() {
        return findEnfermeraEntities(true, -1, -1);
    }

    public List<Enfermera> findEnfermeraEntities(int maxResults, int firstResult) {
        return findEnfermeraEntities(false, maxResults, firstResult);
    }

    private List<Enfermera> findEnfermeraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Enfermera.class));
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

    public Enfermera findEnfermera(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Enfermera.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnfermeraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Enfermera> rt = cq.from(Enfermera.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
