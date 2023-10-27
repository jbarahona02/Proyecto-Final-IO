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
        if (lineas[fila][col] == 2)
            return;

        if (maxVH > 0 && lineas[fila][col] == 1)
            return;

        if (maxVH <= 0 && lineas[fila][col] == -1)
            return;

        for (int i = 0; i < valores.length; i++) {
            if (maxVH > 0)
                lineas[i][col] = lineas[i][col] == -1 || lineas[i][col] == 2 ? 2 : 1;
            else
                lineas[fila][i] = lineas[fila][i] == 1 || lineas[fila][i] == 2 ? 2 : -1;
        }


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
                if (lineas[fila][col] == 0)
                    valores[fila][col] -= valorMinimoNoCubierto;

                else if (lineas[fila][col] == 2)
                    valores[fila][col] += valorMinimoNoCubierto;
            }
        }
    }
    private boolean optimizacion(int fila) {
        if (fila == filas.length)
            return true;

        for (int col = 0; col < valores.length; col++) {
            if (valores[fila][col] == 0 && columnasOcupadas[col] == 0) {
                filas[fila] = col;
                columnasOcupadas[col] = 1;
                if (optimizacion(fila + 1))
                    return true;
                columnasOcupadas[col] = 0;
            }
        }
        return false;
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