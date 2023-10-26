package umg.proyectofinal.io.servicios;

public class MetodoHungaro {
    int[] filas; // Index of the column selected by every row (The final result)
    int[] columnasOcupadas; // Verify that all column are occupied, used in the optimization step
    private int[][] valoresOriginales; // Given values
    private int[][] valores; // Cloned given values to be processed
    private int[][] lineas; // Line drawn
    private int numeroLineas; // Number of line drawn

    public MetodoHungaro(int[][] matrix) {
        valoresOriginales = matrix;
        valores = copiarMatriz(matrix);
        filas = new int[valores.length];
        columnasOcupadas = new int[valores.length];

        //Algorithm
        obtenerMinimosFila();                // Step 1
        subtractColMinimal();                // Step 2
        convertirCeros();                        // Step 3
        while (numeroLineas < valores.length) {
            agregarCerosAdicionales();        // Step 4 (Condition)
            convertirCeros();                    // Step 3 Again (Condition)
        }
        optimizacion();                        // Optimization
    }

    /**
     * Step 1
     * Subtract from every element the minimum value from its row
     */
    public void obtenerMinimosFila() {
        int[] filaValorMinimo = new int[valores.length];
        //get the minimum for each row and store in filaValorMinimo[]
        for (int fila = 0; fila < valores.length; fila++) {
            filaValorMinimo[fila] = valores[fila][0];
            for (int col = 1; col < valores.length; col++) {
                if (valores[fila][col] < filaValorMinimo[fila])
                    filaValorMinimo[fila] = valores[fila][col];
            }
        }

        //subtract minimum from each row using filaValorMinimo[]
        for (int fila = 0; fila < valores.length; fila++) {
            for (int col = 0; col < valores.length; col++) {
                valores[fila][col] -= filaValorMinimo[fila];
            }
        }
    } //End Step 1

    /**
     * Step 2
     * Subtract from every element the minimum value from its column
     */
    public void subtractColMinimal() {
        int colValorMinimo[] = new int[valores.length];
        //get the minimum for each column and store them in colValorMinimo[]
        for (int col = 0; col < valores.length; col++) {
            colValorMinimo[col] = valores[0][col];
            for (int row = 1; row < valores.length; row++) {
                if (valores[row][col] < colValorMinimo[col])
                    colValorMinimo[col] = valores[row][col];
            }
        }

        //subtract minimum from each column using colValorMinimo[]
        for (int col = 0; col < valores.length; col++) {
            for (int fila = 0; fila < valores.length; fila++) {
                valores[fila][col] -= colValorMinimo[col];
            }
        }
    } //End Step 2

    /**
     * Step 3.1
     * Loop through all elements, and run colorNeighbors when the element visited is equal to zero
     */
    public void convertirCeros() {
        numeroLineas = 0;
        lineas = new int[valores.length][valores.length];

        for (int fila = 0; fila < valores.length; fila++) {
            for (int col = 0; col < valores.length; col++) {
                if (valores[fila][col] == 0)
                    marcar(fila, col, maxVH(fila, col));
            }
        }
    }

    /**
     * Step 3.2
     * Checks which direction (vertical,horizontal) contains more zeros, every time a zero is found vertically, we increment the result
     * and every time a zero is found horizontally, we decrement the result. At the end, result will be negative, zero or positive
     *
     * @param row Row index for the target cell
     * @param col Column index for the target cell
     * @return Positive integer means that the line passing by indexes [row][col] should be vertical, Zero or Negative means that the line passing by indexes [row][col] should be horizontal
     */
    private int maxVH(int row, int col) {
        int result = 0;
        for (int i = 0; i < valores.length; i++) {
            if (valores[i][col] == 0)
                result++;
            if (valores[row][i] == 0)
                result--;
        }
        return result;
    }

    /**
     * Step 3.3
     * Color the neighbors of the cell at index [fila][col]. To know which direction to draw the lines, we pass maxVH value.
     *
     * @param fila   Row index for the target cell
     * @param col   Column index for the target cell
     * @param maxVH Value return by the maxVH method, positive means the line to draw passing by indexes [fila][col] is vertical, negative or zero means the line to draw passing by indexes [fila][col] is horizontal
     */
    private void marcar(int fila, int col, int maxVH) {
        if (lineas[fila][col] == 2) // if cell is colored twice before (intersection cell), don't color it again
            return;

        if (maxVH > 0 && lineas[fila][col] == 1) // if cell colored vertically and needs to be recolored vertically, don't color it again (Allowing this step, will color the same line (result won't change), but the num of line will be incremented (wrong value for the num of line drawn))
            return;

        if (maxVH <= 0 && lineas[fila][col] == -1) // if cell colored horizontally and needs to be recolored horizontally, don't color it again (Allowing this step, will color the same line (result won't change), but the num of line will be incremented (wrong value for the num of line drawn))
            return;

        for (int i = 0; i < valores.length; i++) { // Loop on cell at indexes [fila][col] and its neighbors
            if (maxVH > 0)    // if value of maxVH is positive, color vertically
                lineas[i][col] = lineas[i][col] == -1 || lineas[i][col] == 2 ? 2 : 1; // if cell was colored before as horizontal (-1), and now needs to be colored vertical (1), so this cell is an intersection (2). Else if this value was not colored before, color it vertically
            else            // if value of maxVH is zero or negative color horizontally
                lineas[fila][i] = lineas[fila][i] == 1 || lineas[fila][i] == 2 ? 2 : -1; // if cell was colored before as vertical (1), and now needs to be colored horizontal (-1), so this cell is an intersection (2). Else if this value was not colored before, color it horizontally
        }

        // increment line number
        numeroLineas++;
//		printMatrix(lines); // Monitor the line draw steps
    }//End step 3

    /**
     * Step 4
     * This step is not always executed. (Check the algorithm in the constructor)
     * Create additional zeros, by coloring the minimum value of uncovered cells (cells not colored by any line)
     */
    public void agregarCerosAdicionales() {
        int valorMinimoNoCubierto = 0; // We don't know the value of the first uncovered cell, so we put a joker value 0 (0 is safe, because before this step, all zeros were covered)

        // Find the min in the uncovered numbers
        for (int fila = 0; fila < valores.length; fila++) {
            for (int col = 0; col < valores.length; col++) {
                if (lineas[fila][col] == 0 && (valores[fila][col] < valorMinimoNoCubierto || valorMinimoNoCubierto == 0))
                    valorMinimoNoCubierto = valores[fila][col];
            }
        }

        // Subtract min form all uncovered elements, and add it to all elements covered twice
        for (int fila = 0; fila < valores.length; fila++) {
            for (int col = 0; col < valores.length; col++) {
                if (lineas[fila][col] == 0) // If uncovered, subtract
                    valores[fila][col] -= valorMinimoNoCubierto;

                else if (lineas[fila][col] == 2) // If covered twice, add
                    valores[fila][col] += valorMinimoNoCubierto;
            }
        }
    } // End step 4

    /**
     * Optimization, assign every fila a cell in a unique column. Since a fila can contain more than one zero,
     * we need to make sure that all rows are assigned one cell from one unique column. To do this, use brute force
     *
     * @param fila
     * @return true
     **/
    private boolean optimizacion(int fila) {
        if (fila == filas.length) // If all rows were assigned a cell
            return true;

        for (int col = 0; col < valores.length; col++) { // Try all columns
            if (valores[fila][col] == 0 && columnasOcupadas[col] == 0) { // If the current cell at column `col` has a value of zero, and the column is not reserved by a previous fila
                filas[fila] = col; // Assign the current fila the current column cell
                columnasOcupadas[col] = 1; // Mark the column as reserved
                if (optimizacion(fila + 1)) // If the next rows were assigned successfully a cell from a unique column, return true
                    return true;
                columnasOcupadas[col] = 0; // If the next rows were not able to get a cell, go back and try for the previous rows another cell from another column
            }
        }
        return false; // If no cell were assigned for the current fila, return false to go back one fila to try to assign to it another cell from another column
    }

    /**
     * Overload optimization(int row) method
     *
     * @return true
     */
    public boolean optimizacion() {
        return optimizacion(0);
    } //End optimization

    /**
     * Get the result by returning an array containing the cell assigned for each row
     *
     * @return Array of rows where each array index represent the row number, and the value at each index is the column assigned to the corresponding row
     */
    public int[] resultados() {
        return filas;
    }

    /**
     * Get the sum of the value of the assigned cells for all rows using the original passed matrix, and using the rows array to know the index of the column for each row.
     *
     * @return Total values
     */
    public int obtenerTotal() {
        int total = 0;
        for (int row = 0; row < valores.length; row++)
            total += valoresOriginales[row][filas[row]];
        return total;
    }

    /**
     * Clone the 2D array
     *
     * @return A copy of the 2D array
     */
    public int[][] copiarMatriz(int[][] matriz) {
        int[][] tmp = new int[matriz.length][matriz.length];
        for (int fila = 0; fila < matriz.length; fila++) {
            tmp[fila] = matriz[fila].clone();
        }
        return tmp;
    }

    /**
     * Print a 2D array
     *
     * @param matriz The target 2D array
     */
    public void printMatrix(int[][] matriz) {
        for (int[] ints : matriz) {
            for (int col = 0; col < matriz.length; col++) {
                System.out.print(ints[col] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }
}