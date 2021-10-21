/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidad;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author jcpleitez
 */
public class Conexion {

    // INSTANCIA DE TIPO SINGLETON
    private static Conexion instance;
    private EntityManagerFactory emf;

    public Conexion() {
        emf = Persistence.createEntityManagerFactory("TravelPU");
    }

    public static Conexion getInstance() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Object object) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Object object) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            object = em.merge(object);
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Object object) {
        EntityManager em = null;
        try {
            em = getEntityManager();            
            em.getTransaction().begin();
            if (!em.contains(object)) {
                object = em.merge(object);
            }
            em.remove(object);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void clearCache(){
        emf.getCache().evictAll();
    }

}
