package ControladoresJPA;

import ControladoresJPA.exceptions.NonexistentEntityException;
import Modelo.Consultorio;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Piso;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Mauro
 */
public class ConsultorioJpaController implements Serializable {

    public ConsultorioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Consultorio consultorio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Piso piso = consultorio.getPiso();
            if (piso != null) {
                piso = em.getReference(piso.getClass(), piso.getId());
                consultorio.setPiso(piso);
            }
            em.persist(consultorio);
            if (piso != null) {
                piso.getConsultorios().add(consultorio);
                piso = em.merge(piso);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Consultorio consultorio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Consultorio persistentConsultorio = em.find(Consultorio.class, consultorio.getId());
            Piso pisoOld = persistentConsultorio.getPiso();
            Piso pisoNew = consultorio.getPiso();
            if (pisoNew != null) {
                pisoNew = em.getReference(pisoNew.getClass(), pisoNew.getId());
                consultorio.setPiso(pisoNew);
            }
            consultorio = em.merge(consultorio);
            if (pisoOld != null && !pisoOld.equals(pisoNew)) {
                pisoOld.getConsultorios().remove(consultorio);
                pisoOld = em.merge(pisoOld);
            }
            if (pisoNew != null && !pisoNew.equals(pisoOld)) {
                pisoNew.getConsultorios().add(consultorio);
                pisoNew = em.merge(pisoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = consultorio.getId();
                if (findConsultorio(id) == null) {
                    throw new NonexistentEntityException("The consultorio with id " + id + " no longer exists.");
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
            Consultorio consultorio;
            try {
                consultorio = em.getReference(Consultorio.class, id);
                consultorio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The consultorio with id " + id + " no longer exists.", enfe);
            }
            Piso piso = consultorio.getPiso();
            if (piso != null) {
                piso.getConsultorios().remove(consultorio);
                piso = em.merge(piso);
            }
            em.remove(consultorio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Consultorio> findConsultorioEntities() {
        return findConsultorioEntities(true, -1, -1);
    }

    public List<Consultorio> findConsultorioEntities(int maxResults, int firstResult) {
        return findConsultorioEntities(false, maxResults, firstResult);
    }

    private List<Consultorio> findConsultorioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Consultorio.class));
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

    public Consultorio findConsultorio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Consultorio.class, id);
        } finally {
            em.close();
        }
    }

    public int getConsultorioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Consultorio> rt = cq.from(Consultorio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
