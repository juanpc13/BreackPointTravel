/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import controlador.Conexion;
import entidades.Empleado;
import vistas.LogIn;

/**
 *
 * @author jcpleitez
 */
public class App {
    
    public static void main(String[] args) {
        
//        Conexion conexion = Conexion.getInstance();
//        Empleado empleado = conexion.getEm().find(Empleado.class, 1);
//        System.out.println(empleado);
        
        LogIn logIn = new LogIn();
        logIn.setVisible(true);
        
    }
    
}
