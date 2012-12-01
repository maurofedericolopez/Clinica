package ControladoresJPA;

import ControladoresJPA.exceptions.NonexistentEntityException;
import Modelo.Edificio;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Piso;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Mauro
 */
public class EdificioJpaController implements Serializable {

    public EdificioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Edificio edificio) {
        if (edificio.getPisos() == null) {
            edificio.setPisos(new ArrayList<Piso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Piso> attachedPisos = new ArrayList<Piso>();
            for (Piso pisosPisoToAttach : edificio.getPisos()) {
                pisosPisoToAttach = em.getReference(pisosPisoToAttach.getClass(), pisosPisoToAttach.getId());
                attachedPisos.add(pisosPisoToAttach);
            }
            edificio.setPisos(attachedPisos);
            em.persist(edificio);
            for (Piso pisosPiso : edificio.getPisos()) {
                Edificio oldEdificioOfPisosPiso = pisosPiso.getEdificio();
                pisosPiso.setEdificio(edificio);
                pisosPiso = em.merge(pisosPiso);
                if (oldEdificioOfPisosPiso != null) {
                    oldEdificioOfPisosPiso.getPisos().remove(pisosPiso);
                    oldEdificioOfPisosPiso = em.merge(oldEdificioOfPisosPiso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Edificio edificio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Edificio persistentEdificio = em.find(Edificio.class, edificio.getId());
            List<Piso> pisosOld = persistentEdificio.getPisos();
            List<Piso> pisosNew = edificio.getPisos();
            List<Piso> attachedPisosNew = new ArrayList<Piso>();
            for (Piso pisosNewPisoToAttach : pisosNew) {
                pisosNewPisoToAttach = em.getReference(pisosNewPisoToAttach.getClass(), pisosNewPisoToAttach.getId());
                attachedPisosNew.add(pisosNewPisoToAttach);
            }
            pisosNew = attachedPisosNew;
            edificio.setPisos(pisosNew);
            edificio = em.merge(edificio);
            for (Piso pisosOldPiso : pisosOld) {
                if (!pisosNew.contains(pisosOldPiso)) {
                    pisosOldPiso.setEdificio(null);
                    pisosOldPiso = em.merge(pisosOldPiso);
                }
            }
            for (Piso pisosNewPiso : pisosNew) {
                if (!pisosOld.contains(pisosNewPiso)) {
                    Edificio oldEdificioOfPisosNewPiso = pisosNewPiso.getEdificio();
                    pisosNewPiso.setEdificio(edificio);
                    pisosNewPiso = em.merge(pisosNewPiso);
                    if (oldEdificioOfPisosNewPiso != null && !oldEdificioOfPisosNewPiso.equals(edificio)) {
                        oldEdificioOfPisosNewPiso.getPisos().remove(pisosNewPiso);
                        oldEdificioOfPisosNewPiso = em.merge(oldEdificioOfPisosNewPiso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = edificio.getId();
                if (findEdificio(id) == null) {
                    throw new NonexistentEntityException("The edificio with id " + id + " no longer exists.");
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
            Edificio edificio;
            try {
                edificio = em.getReference(Edificio.class, id);
                edificio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The edificio with id " + id + " no longer exists.", enfe);
            }
            List<Piso> pisos = edificio.getPisos();
            for (Piso pisosPiso : pisos) {
                pisosPiso.setEdificio(null);
                pisosPiso = em.merge(pisosPiso);
            }
            em.remove(edificio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Edificio> findEdificioEntities() {
        return findEdificioEntities(true, -1, -1);
    }

    public List<Edificio> findEdificioEntities(int maxResults, int firstResult) {
        return findEdificioEntities(false, maxResults, firstResult);
    }

    private List<Edificio> findEdificioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Edificio.class));
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

    public Edificio findEdificio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Edificio.class, id);
        } finally {
            em.close();
        }
    }

    public int getEdificioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Edificio> rt = cq.from(Edificio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
