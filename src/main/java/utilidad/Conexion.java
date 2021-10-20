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
    private EntityManager em;

    public Conexion() {
        emf = Persistence.createEntityManagerFactory("TravelPU");
        em = emf.createEntityManager();
    }

    public static Conexion getInstance() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    public EntityManager getEm() {
        return em;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void create(Object object) {
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public Object findEmpleado(Class c, Integer id) {
        Object object = null;
        try {
            object = em.find(c, id);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return object;
    }

    public void edit(Object object) {
        try {
            em.getTransaction().begin();
            object = em.merge(object);
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void destroy(Class c, Integer id) {
        try {
            em.getTransaction().begin();
            Object object = findEmpleado(c, id);
            em.remove(object);
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public void destroy(Object object) {
        try {
            em.getTransaction().begin();
            em.remove(object);
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
