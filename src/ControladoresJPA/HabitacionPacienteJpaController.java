package ControladoresJPA;

import ControladoresJPA.exceptions.NonexistentEntityException;
import Modelo.HabitacionPaciente;
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
public class HabitacionPacienteJpaController implements Serializable {

    public HabitacionPacienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HabitacionPaciente habitacionPaciente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(habitacionPaciente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HabitacionPaciente habitacionPaciente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            habitacionPaciente = em.merge(habitacionPaciente);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = habitacionPaciente.getId();
                if (findHabitacionPaciente(id) == null) {
                    throw new NonexistentEntityException("The habitacionPaciente with id " + id + " no longer exists.");
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
            HabitacionPaciente habitacionPaciente;
            try {
                habitacionPaciente = em.getReference(HabitacionPaciente.class, id);
                habitacionPaciente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The habitacionPaciente with id " + id + " no longer exists.", enfe);
            }
            em.remove(habitacionPaciente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HabitacionPaciente> findHabitacionPacienteEntities() {
        return findHabitacionPacienteEntities(true, -1, -1);
    }

    public List<HabitacionPaciente> findHabitacionPacienteEntities(int maxResults, int firstResult) {
        return findHabitacionPacienteEntities(false, maxResults, firstResult);
    }

    private List<HabitacionPaciente> findHabitacionPacienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HabitacionPaciente.class));
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

    public HabitacionPaciente findHabitacionPaciente(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HabitacionPaciente.class, id);
        } finally {
            em.close();
        }
    }

    public int getHabitacionPacienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HabitacionPaciente> rt = cq.from(HabitacionPaciente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
