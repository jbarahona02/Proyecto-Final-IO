/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package umg.proyectofinal.io.utilidades;

import java.util.regex.Pattern;

/**
 *
 * @author Javier Barahona
 */
public class Utilidades {
    
    public static boolean validarNumero(String cadenaNumero) {
        return Pattern.matches("[0-9]+", cadenaNumero);
    }
}
