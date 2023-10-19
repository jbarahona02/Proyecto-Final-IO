/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package umg.proyectofinal.io;
import umg.servicios.ServicioMetodoHungaro;

/**
 *
 * @author Javier Barahona
 */
public class Principal {

    public static void main(String[] args) {
        ServicioMetodoHungaro metodoAsignacion = new ServicioMetodoHungaro();
        
        try {
            metodoAsignacion.mostrarMenu();
        } catch (Exception e) {
            
        }
    }
    
   
}
