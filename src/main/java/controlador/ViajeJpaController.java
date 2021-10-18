/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import App.exceptions.IllegalOrphanException;
import App.exceptions.NonexistentEntityException;
import App.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Cliente;
import entidades.Viaje;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jcpleitez
 */
public class ViajeJpaController implements Serializable {

    public ViajeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Viaje viaje) throws PreexistingEntityException, Exception {
        if (viaje.getClienteList() == null) {
            viaje.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : viaje.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getIdCliente());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            viaje.setClienteList(attachedClienteList);
            em.persist(viaje);
            for (Cliente clienteListCliente : viaje.getClienteList()) {
                Viaje oldIdViajeOfClienteListCliente = clienteListCliente.getIdViaje();
                clienteListCliente.setIdViaje(viaje);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldIdViajeOfClienteListCliente != null) {
                    oldIdViajeOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldIdViajeOfClienteListCliente = em.merge(oldIdViajeOfClienteListCliente);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findViaje(viaje.getIdViaje()) != null) {
                throw new PreexistingEntityException("Viaje " + viaje + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Viaje viaje) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Viaje persistentViaje = em.find(Viaje.class, viaje.getIdViaje());
            List<Cliente> clienteListOld = persistentViaje.getClienteList();
            List<Cliente> clienteListNew = viaje.getClienteList();
            List<String> illegalOrphanMessages = null;
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its idViaje field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getIdCliente());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            viaje.setClienteList(clienteListNew);
            viaje = em.merge(viaje);
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Viaje oldIdViajeOfClienteListNewCliente = clienteListNewCliente.getIdViaje();
                    clienteListNewCliente.setIdViaje(viaje);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldIdViajeOfClienteListNewCliente != null && !oldIdViajeOfClienteListNewCliente.equals(viaje)) {
                        oldIdViajeOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldIdViajeOfClienteListNewCliente = em.merge(oldIdViajeOfClienteListNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = viaje.getIdViaje();
                if (findViaje(id) == null) {
                    throw new NonexistentEntityException("The viaje with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Viaje viaje;
            try {
                viaje = em.getReference(Viaje.class, id);
                viaje.getIdViaje();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The viaje with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cliente> clienteListOrphanCheck = viaje.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Viaje (" + viaje + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable idViaje field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(viaje);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Viaje> findViajeEntities() {
        return findViajeEntities(true, -1, -1);
    }

    public List<Viaje> findViajeEntities(int maxResults, int firstResult) {
        return findViajeEntities(false, maxResults, firstResult);
    }

    private List<Viaje> findViajeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Viaje.class));
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

    public Viaje findViaje(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Viaje.class, id);
        } finally {
            em.close();
        }
    }

    public int getViajeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Viaje> rt = cq.from(Viaje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
