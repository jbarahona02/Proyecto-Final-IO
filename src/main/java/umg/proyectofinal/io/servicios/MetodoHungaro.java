package umg.proyectofinal.io.servicios;

public class MetodoHungaro {
    int[] filas;
    int[] columnasOcupadas;
    private int[][] valoresOriginales;
    private int[][] valores;
    private int[][] lineas;
    private int numeroLineas;

    public MetodoHungaro(int[][] matrix) {
        valoresOriginales = matrix;
        valores = copiarMatriz(matrix);
        filas = new int[valores.length];
        columnasOcupadas = new int[valores.length];

        obtenerMinimosFila();
        subtractColMinimal();
        convertirCeros();
        while (numeroLineas < valores.length) {
            agregarCerosAdicionales();
            convertirCeros();
        }
        optimizacion();
    }


    public void obtenerMinimosFila() {
        int[] filaValorMinimo = new int[valores.length];

        for (int fila = 0; fila < valores.length; fila++) {
            filaValorMinimo[fila] = valores[fila][0];
            for (int col = 1; col < valores.length; col++) {
                if (valores[fila][col] < filaValorMinimo[fila])
                    filaValorMinimo[fila] = valores[fila][col];
            }
        }
        for (int fila = 0; fila < valores.length; fila++) {
            for (int col = 0; col < valores.length; col++) {
                valores[fila][col] -= filaValorMinimo[fila];
            }
        }
    }

    public void subtractColMinimal() {
        int colValorMinimo[] = new int[valores.length];
        for (int col = 0; col < valores.length; col++) {
            colValorMinimo[col] = valores[0][col];
            for (int row = 1; row < valores.length; row++) {
                if (valores[row][col] < colValorMinimo[col])
                    colValorMinimo[col] = valores[row][col];
            }
        }
        for (int col = 0; col < valores.length; col++) {
            for (int fila = 0; fila < valores.length; fila++) {
                valores[fila][col] -= colValorMinimo[col];
            }
        }
    }
    public void convertirCeros() {
        numeroLineas = 0;
        lineas = new int[valores.length][valores.length];

        for (int fila = 0; fila < valores.length; fila++) {
            for (int col = 0; col < valores.length; col++) {
                if (valores[fila][col] == 0) {
                    marcar(fila, col, maxVH(fila, col));
                }
            }
        }
    }

    private int maxVH(int fila, int col) {
        int result = 0;
        for (int i = 0; i < valores.length; i++) {
            if (valores[i][col] == 0)
                result++;
            if (valores[fila][i] == 0)
                result--;
        }
        return result;
    }


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
    }
    public void agregarCerosAdicionales() {
        int valorMinimoNoCubierto = 0;

        for (int fila = 0; fila < valores.length; fila++) {
            for (int col = 0; col < valores.length; col++) {
                if (lineas[fila][col] == 0 && (valores[fila][col] < valorMinimoNoCubierto || valorMinimoNoCubierto == 0))
                    valorMinimoNoCubierto = valores[fila][col];
            }
        }

        for (int fila = 0; fila < valores.length; fila++) {
            for (int col = 0; col < valores.length; col++) {
                if (lineas[fila][col] == 0) // If uncovered, subtract
                    valores[fila][col] -= valorMinimoNoCubierto;

                else if (lineas[fila][col] == 2) // If covered twice, add
                    valores[fila][col] += valorMinimoNoCubierto;
            }
        }
    }
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


    public boolean optimizacion() {
        return optimizacion(0);
    }


    public int[] resultados() {
        return filas;
    }


    public int obtenerTotal() {
        int total = 0;
        for (int row = 0; row < valores.length; row++)
            total += valoresOriginales[row][filas[row]];
        return total;
    }


    public int[][] copiarMatriz(int[][] matriz) {
        int[][] tmp = new int[matriz.length][matriz.length];
        for (int fila = 0; fila < matriz.length; fila++) {
            tmp[fila] = matriz[fila].clone();
        }
        return tmp;
    }

}