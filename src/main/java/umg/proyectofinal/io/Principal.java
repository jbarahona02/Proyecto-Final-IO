/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package umg.proyectofinal.io;

import umg.servicios.Hungarian;

/**
 * @author Javier Barahona
 */
public class Principal {

    public static void main(String[] args) {

        // the problem is written in the form of a matrix
        int[][] dataMatrix = {
                //col0  col1  col2  col3
                {1, 4, 6, 3},  //row0
                {9, 7, 10, 9},  //row1
                {4, 5, 11, 7},  //row2
                {8, 7, 8, 5}   //row3
        };

        //find optimal assignment
        Hungarian hungarian = new Hungarian(dataMatrix);

        int[] result = hungarian.getResult();
        for (int i = 0; i < result.length; i++)
            System.out.println(String.format("Row%d => Col%d (%d)", i + 1, result[i] + 1, dataMatrix[i][result[i]])); // Rowi => Colj (value)

        System.out.printf("\nTotal: %d%n", hungarian.getTotal()); // Total
    }


}
