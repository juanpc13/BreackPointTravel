/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import controlador.Conexion;
import controlador.EmpleadoJpaController;
import entidades.Empleado;
import java.util.List;

/**
 *
 * @author jcpleitez
 */
public class App {
    
    public static void main(String[] args) {
        
        Conexion conexion = Conexion.getInstance();
        
        EmpleadoJpaController ejc = new EmpleadoJpaController(conexion.getEmf());
        List<Empleado> empleados = ejc.findEmpleadoEntities();
        System.out.println(empleados);
        
    }
    
}
