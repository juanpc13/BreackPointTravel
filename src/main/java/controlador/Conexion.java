/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

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
    
    
    
}
