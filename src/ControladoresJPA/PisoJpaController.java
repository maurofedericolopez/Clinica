package ControladoresJPA;

import ControladoresJPA.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Edificio;
import Modelo.Habitacion;
import java.util.ArrayList;
import java.util.List;
import Modelo.Consultorio;
import Modelo.Piso;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Mauro
 */
public class PisoJpaController implements Serializable {

    public PisoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Piso piso) {
        if (piso.getHabitaciones() == null) {
            piso.setHabitaciones(new ArrayList<Habitacion>());
        }
        if (piso.getConsultorios() == null) {
            piso.setConsultorios(new ArrayList<Consultorio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Edificio edificio = piso.getEdificio();
            if (edificio != null) {
                edificio = em.getReference(edificio.getClass(), edificio.getId());
                piso.setEdificio(edificio);
            }
            List<Habitacion> attachedHabitaciones = new ArrayList<Habitacion>();
            for (Habitacion habitacionesHabitacionToAttach : piso.getHabitaciones()) {
                habitacionesHabitacionToAttach = em.getReference(habitacionesHabitacionToAttach.getClass(), habitacionesHabitacionToAttach.getId());
                attachedHabitaciones.add(habitacionesHabitacionToAttach);
            }
            piso.setHabitaciones(attachedHabitaciones);
            List<Consultorio> attachedConsultorios = new ArrayList<Consultorio>();
            for (Consultorio consultoriosConsultorioToAttach : piso.getConsultorios()) {
                consultoriosConsultorioToAttach = em.getReference(consultoriosConsultorioToAttach.getClass(), consultoriosConsultorioToAttach.getId());
                attachedConsultorios.add(consultoriosConsultorioToAttach);
            }
            piso.setConsultorios(attachedConsultorios);
            em.persist(piso);
            if (edificio != null) {
                edificio.getPisos().add(piso);
                edificio = em.merge(edificio);
            }
            for (Habitacion habitacionesHabitacion : piso.getHabitaciones()) {
                Piso oldPisoOfHabitacionesHabitacion = habitacionesHabitacion.getPiso();
                habitacionesHabitacion.setPiso(piso);
                habitacionesHabitacion = em.merge(habitacionesHabitacion);
                if (oldPisoOfHabitacionesHabitacion != null) {
                    oldPisoOfHabitacionesHabitacion.getHabitaciones().remove(habitacionesHabitacion);
                    oldPisoOfHabitacionesHabitacion = em.merge(oldPisoOfHabitacionesHabitacion);
                }
            }
            for (Consultorio consultoriosConsultorio : piso.getConsultorios()) {
                Piso oldPisoOfConsultoriosConsultorio = consultoriosConsultorio.getPiso();
                consultoriosConsultorio.setPiso(piso);
                consultoriosConsultorio = em.merge(consultoriosConsultorio);
                if (oldPisoOfConsultoriosConsultorio != null) {
                    oldPisoOfConsultoriosConsultorio.getConsultorios().remove(consultoriosConsultorio);
                    oldPisoOfConsultoriosConsultorio = em.merge(oldPisoOfConsultoriosConsultorio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Piso piso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Piso persistentPiso = em.find(Piso.class, piso.getId());
            Edificio edificioOld = persistentPiso.getEdificio();
            Edificio edificioNew = piso.getEdificio();
            List<Habitacion> habitacionesOld = persistentPiso.getHabitaciones();
            List<Habitacion> habitacionesNew = piso.getHabitaciones();
            List<Consultorio> consultoriosOld = persistentPiso.getConsultorios();
            List<Consultorio> consultoriosNew = piso.getConsultorios();
            if (edificioNew != null) {
                edificioNew = em.getReference(edificioNew.getClass(), edificioNew.getId());
                piso.setEdificio(edificioNew);
            }
            List<Habitacion> attachedHabitacionesNew = new ArrayList<Habitacion>();
            for (Habitacion habitacionesNewHabitacionToAttach : habitacionesNew) {
                habitacionesNewHabitacionToAttach = em.getReference(habitacionesNewHabitacionToAttach.getClass(), habitacionesNewHabitacionToAttach.getId());
                attachedHabitacionesNew.add(habitacionesNewHabitacionToAttach);
            }
            habitacionesNew = attachedHabitacionesNew;
            piso.setHabitaciones(habitacionesNew);
            List<Consultorio> attachedConsultoriosNew = new ArrayList<Consultorio>();
            for (Consultorio consultoriosNewConsultorioToAttach : consultoriosNew) {
                consultoriosNewConsultorioToAttach = em.getReference(consultoriosNewConsultorioToAttach.getClass(), consultoriosNewConsultorioToAttach.getId());
                attachedConsultoriosNew.add(consultoriosNewConsultorioToAttach);
            }
            consultoriosNew = attachedConsultoriosNew;
            piso.setConsultorios(consultoriosNew);
            piso = em.merge(piso);
            if (edificioOld != null && !edificioOld.equals(edificioNew)) {
                edificioOld.getPisos().remove(piso);
                edificioOld = em.merge(edificioOld);
            }
            if (edificioNew != null && !edificioNew.equals(edificioOld)) {
                edificioNew.getPisos().add(piso);
                edificioNew = em.merge(edificioNew);
            }
            for (Habitacion habitacionesOldHabitacion : habitacionesOld) {
                if (!habitacionesNew.contains(habitacionesOldHabitacion)) {
                    habitacionesOldHabitacion.setPiso(null);
                    habitacionesOldHabitacion = em.merge(habitacionesOldHabitacion);
                }
            }
            for (Habitacion habitacionesNewHabitacion : habitacionesNew) {
                if (!habitacionesOld.contains(habitacionesNewHabitacion)) {
                    Piso oldPisoOfHabitacionesNewHabitacion = habitacionesNewHabitacion.getPiso();
                    habitacionesNewHabitacion.setPiso(piso);
                    habitacionesNewHabitacion = em.merge(habitacionesNewHabitacion);
                    if (oldPisoOfHabitacionesNewHabitacion != null && !oldPisoOfHabitacionesNewHabitacion.equals(piso)) {
                        oldPisoOfHabitacionesNewHabitacion.getHabitaciones().remove(habitacionesNewHabitacion);
                        oldPisoOfHabitacionesNewHabitacion = em.merge(oldPisoOfHabitacionesNewHabitacion);
                    }
                }
            }
            for (Consultorio consultoriosOldConsultorio : consultoriosOld) {
                if (!consultoriosNew.contains(consultoriosOldConsultorio)) {
                    consultoriosOldConsultorio.setPiso(null);
                    consultoriosOldConsultorio = em.merge(consultoriosOldConsultorio);
                }
            }
            for (Consultorio consultoriosNewConsultorio : consultoriosNew) {
                if (!consultoriosOld.contains(consultoriosNewConsultorio)) {
                    Piso oldPisoOfConsultoriosNewConsultorio = consultoriosNewConsultorio.getPiso();
                    consultoriosNewConsultorio.setPiso(piso);
                    consultoriosNewConsultorio = em.merge(consultoriosNewConsultorio);
                    if (oldPisoOfConsultoriosNewConsultorio != null && !oldPisoOfConsultoriosNewConsultorio.equals(piso)) {
                        oldPisoOfConsultoriosNewConsultorio.getConsultorios().remove(consultoriosNewConsultorio);
                        oldPisoOfConsultoriosNewConsultorio = em.merge(oldPisoOfConsultoriosNewConsultorio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = piso.getId();
                if (findPiso(id) == null) {
                    throw new NonexistentEntityException("The piso with id " + id + " no longer exists.");
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
            Piso piso;
            try {
                piso = em.getReference(Piso.class, id);
                piso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The piso with id " + id + " no longer exists.", enfe);
            }
            Edificio edificio = piso.getEdificio();
            if (edificio != null) {
                edificio.getPisos().remove(piso);
                edificio = em.merge(edificio);
            }
            List<Habitacion> habitaciones = piso.getHabitaciones();
            for (Habitacion habitacionesHabitacion : habitaciones) {
                habitacionesHabitacion.setPiso(null);
                habitacionesHabitacion = em.merge(habitacionesHabitacion);
            }
            List<Consultorio> consultorios = piso.getConsultorios();
            for (Consultorio consultoriosConsultorio : consultorios) {
                consultoriosConsultorio.setPiso(null);
                consultoriosConsultorio = em.merge(consultoriosConsultorio);
            }
            em.remove(piso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Piso> findPisoEntities() {
        return findPisoEntities(true, -1, -1);
    }

    public List<Piso> findPisoEntities(int maxResults, int firstResult) {
        return findPisoEntities(false, maxResults, firstResult);
    }

    private List<Piso> findPisoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Piso.class));
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

    public Piso findPiso(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Piso.class, id);
        } finally {
            em.close();
        }
    }

    public int getPisoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Piso> rt = cq.from(Piso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
