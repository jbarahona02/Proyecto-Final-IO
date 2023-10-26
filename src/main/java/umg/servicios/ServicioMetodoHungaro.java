/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package umg.servicios;

import umg.dto.Minimo;
import umg.utilidades.Utilidades;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Javier Barahona
 */
public class ServicioMetodoHungaro {
    private Integer cantidadPuestos;
    private Integer cantidadTrabajadores;
    private Integer[][] matrizAsignacion;
    private Integer[][] matrizAsignacionOriginal;

    public ServicioMetodoHungaro() {

    }

    public void mostrarMenu() {
        boolean noEsValido;

        do {

            String opcion = (JOptionPane.showInputDialog(null,
                    """
                            Menú Principal\s
                             1. Método Húngaro\s

                            """
                    , "Investigación de operaciones",
                    JOptionPane.INFORMATION_MESSAGE, null, null, "")).toString();

            if (Utilidades.validarNumero(opcion)) {
                if (Integer.parseInt(opcion) == 1) {
                    ingresarValoresDeMatriz();
                    noEsValido = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de ingresar una opción válida.", "Error", JOptionPane.ERROR_MESSAGE);
                    noEsValido = true;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Debe de ingresar un número.", "Error", JOptionPane.ERROR_MESSAGE);
                noEsValido = true;
            }
        } while (noEsValido);
    }

    public void ingresarValoresDeMatriz() {
        try {
            ingresarNumeroFilas();
            ingresarNumeroColumnas();
            creacionDeMatrizDeAsignacion();
        } catch (Exception e) {
            System.out.println("Error ingresando valores de matriz " + e.getMessage());
        }
    }

    private void ingresarNumeroFilas() {

        String cantidadFilas = (JOptionPane.showInputDialog(null, "Ingrese el número de trabajadores:", "Cantidad de trabajadores", JOptionPane.INFORMATION_MESSAGE, null, null, "")).toString();

        if (Utilidades.validarNumero(cantidadFilas)) {
            if (Integer.parseInt(cantidadFilas) > 1) {
                this.cantidadTrabajadores = Integer.parseInt(cantidadFilas);
            } else {
                JOptionPane.showMessageDialog(null, "Debe de ingresar un número mayor a 1.", "Error", JOptionPane.ERROR_MESSAGE);
                ingresarNumeroFilas();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe de ingresar un número.", "Error", JOptionPane.ERROR_MESSAGE);
            ingresarNumeroFilas();
        }
    }

    private void ingresarNumeroColumnas() {

        String cantidadColumnas = (JOptionPane.showInputDialog(null, "Ingrese el número de puestos:", "Cantidad de puestos", JOptionPane.INFORMATION_MESSAGE, null, null, "")).toString();

        if (Utilidades.validarNumero(cantidadColumnas)) {
            if (Integer.parseInt(cantidadColumnas) > 1) {
                this.cantidadPuestos = Integer.parseInt(cantidadColumnas);
            } else {
                JOptionPane.showMessageDialog(null, "Debe de ingresar un número mayor a 1.", "Error", JOptionPane.ERROR_MESSAGE);
                ingresarNumeroColumnas();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe de ingresar un número.", "Error", JOptionPane.ERROR_MESSAGE);
            ingresarNumeroColumnas();
        }

    }

    public void creacionDeMatrizDeAsignacion() {
        this.matrizAsignacion = new Integer[this.cantidadTrabajadores][this.cantidadPuestos];
        boolean esValido;

        for (int i = 0; i < this.cantidadTrabajadores; i++) {
            for (int j = 0; j < this.cantidadPuestos; j++) {
                do {
                    String costo = (JOptionPane.showInputDialog(null, "Ingrese el costo del puesto " + (j + 1) + " para el trabajador " + (i + 1) + ":",
                            "Cantidad de trabajadores", JOptionPane.INFORMATION_MESSAGE,
                            null, null, "")).toString();

                    if (Utilidades.validarNumero(costo)) {
                        if (Integer.parseInt(costo) > 0) {
                            this.matrizAsignacion[i][j] = Integer.parseInt(costo);
                            esValido = true;
                        } else {
                            esValido = false;
                            JOptionPane.showMessageDialog(null, "Debe de ingresar un número mayor a cero.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        esValido = false;
                        JOptionPane.showMessageDialog(null, "Debe de ingresar un número.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } while (!esValido);
            }
        }

        try {
            this.creacionDeMatrizDeAsignacionOriginal(matrizAsignacion);
            mostrarMatriz(this.matrizAsignacion);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrió un error, vuelve a intentarlo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrarMatriz(Integer[][] matriz) {
        String[] columnas = new String[this.cantidadPuestos + 1];

        columnas[0] = "    ";
        for (int i = 1; i <= this.cantidadPuestos; i++) {
            columnas[i] = "Puesto " + i;
        }

        String[][] matrizAMostrar = new String[this.cantidadTrabajadores][this.cantidadPuestos + 1];

        for (int i = 0; i < this.cantidadTrabajadores; i++) {
            matrizAMostrar[i][0] = "Trabajador " + (i + 1);
        }

        for (int i = 0; i < this.cantidadTrabajadores; i++) {
            for (int j = 1; j <= this.cantidadPuestos; j++) {
                matrizAMostrar[i][j] = String.valueOf(matriz[i][j - 1]);
            }
        }

        JTable tablaDeDatos = new JTable(matrizAMostrar, columnas) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };

        tablaDeDatos.setRowSelectionAllowed(false);
        tablaDeDatos.setFont(new Font("Arial", Font.BOLD, 15));
        tablaDeDatos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        tablaDeDatos.getTableHeader().setReorderingAllowed(false);
        tablaDeDatos.getTableHeader().setResizingAllowed(false);
        tablaDeDatos.setRowHeight(30);
        tablaDeDatos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        DefaultTableCellRenderer alineacionTexto = new DefaultTableCellRenderer();
        alineacionTexto.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < this.cantidadPuestos + 1; i++) {
            tablaDeDatos.getColumnModel().getColumn(i).setCellRenderer(alineacionTexto);
        }


        JScrollPane panel = new JScrollPane();
        panel.setViewportView(tablaDeDatos);
        panel.setPreferredSize(new Dimension(cantidadTrabajadores * 200, cantidadPuestos * 55));

        JOptionPane.showMessageDialog(null, panel, "Método Húngaro", JOptionPane.INFORMATION_MESSAGE);
        resultado();
    }

    void resultado() {
        var resultadosFila = buscarMinimoPorFila(this.matrizAsignacion);
        for (var resultadoFila : resultadosFila) {
            var i = resultadoFila.getI();
            for (int j = 0; j < this.cantidadTrabajadores; j++) {
                var valor = matrizAsignacion[i][j];
                matrizAsignacion[i][j] = valor - resultadoFila.getValor();
            }
        }

        var resultadoColumna = buscarMinimoPorColumna(this.matrizAsignacion);
        for (var resultadosColumna : resultadoColumna) {
            var j = resultadosColumna.getI();
            for (int i = 0; i < this.cantidadPuestos; i++) {
                var valor = matrizAsignacion[i][j];
                matrizAsignacion[i][j] = valor - resultadosColumna.getValor();
            }
        }

        var factible = esFactible(matrizAsignacion);
        if (factible) {
            var ceros = buscarCeros(matrizAsignacion);
            mostrarResultado(ceros);
        }
    }

    private void mostrarResultado(List<Minimo> resultados) {
        var mensaje = new StringBuilder("Asignación óptima:\n");
        Integer costoTotal = 0;
        for (var resultado : resultados) {
            var i = resultado.getI();
            var j = resultado.getJ();
            var valor =  matrizAsignacionOriginal[i][j];
            costoTotal += valor;
            mensaje.append("Trabajador ").append(i + 1).append(" -> Tarea ").append(j + 1 + " = ").append(matrizAsignacionOriginal[i][j]).append("\n");
        }

        mensaje.append("Costo total ").append(costoTotal);
        JOptionPane.showMessageDialog(null, mensaje.toString());
    }

    private boolean esFactible(Integer[][] matrizAsignacion) {
        var matrizFactible = creacionDeMatrizDeAsignacionFactible(matrizAsignacion);
        for (int i = 0; i < this.cantidadTrabajadores; i ++) {
            for (int j = 0; j < matrizFactible[i].length; j++) {
                var valor = matrizFactible[i][j];
                if (valor != null && valor == 0) {
                    setValoresNulosFila(matrizFactible, i);
                    setValoresNulosColumna(matrizFactible, j);
                    break;
                }
            }
        }

        System.out.println(Arrays.deepToString(matrizFactible));
        boolean todosNulos = true;
        for (Integer[] fila : matrizFactible) {
            for (Integer elemento : fila) {
                if (elemento != null) {
                    todosNulos = false;
                    break;
                }
            }
            if (!todosNulos) {
                break;
            }
        }

        return todosNulos;
    }

    private void setValoresNulosFila(Integer[][] matriz, Integer i) {
        Arrays.fill(matriz[i], null);
    }

    private void setValoresNulosColumna(Integer[][] matriz, Integer j) {
        for (int i = 0; i < this.cantidadTrabajadores; i++) {
            matriz[i][j] = null;
        }
    }

    private List<Minimo> buscarCeros(Integer[][] matriz) {
        var minimos = new ArrayList<Minimo>();
        for (int i = 0; i < this.cantidadPuestos; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                var valor = matriz[i][j];
                if (valor == 0) {
                    minimos.add(new Minimo(i, j, valor));
                    break;
                }
            }
        }
        return minimos;
    }

    private List<Minimo> buscarMinimoPorFila(Integer[][] matriz) {
        var minimos = new ArrayList<Minimo>();
        int menorTemporal;
        int j2 = 0;

        for (int i = 0; i < this.cantidadTrabajadores; i++) {
            menorTemporal = matriz[i][0];
            for (int j = 0; j < this.cantidadPuestos; j++) {
                if (matriz[i][j] < menorTemporal) {
                    menorTemporal = matriz[i][j];
                    j2 = j;
                }
            }
            var minimo = new Minimo(i, j2, menorTemporal);
            minimos.add(minimo);
        }

        return minimos;
    }


    public List<Minimo> buscarMinimoPorColumna(Integer[][] matriz) {
        var resultados = new ArrayList<Minimo>();
        int minimoTemporal;
        int j2 = 0;

        for (int i = 0; i < this.cantidadPuestos; i++) {
            minimoTemporal = matriz[0][i];
            for (int j = 0; j < this.cantidadTrabajadores; j++) {
                if (matriz[j][i] < minimoTemporal) {
                    minimoTemporal = matriz[j][i];
                    j2 = j;
                }
            }
            var minimo = new Minimo(i, j2, minimoTemporal);
            resultados.add(minimo);
        }

        return resultados;
    }

    private static void mostrarConBordes(Integer[][] matriz) {
        for (Integer[] integers : matriz) {
            bordesCeldas(matriz[0].length); //Borde superior para las celdas
            for (int col = 0; col < matriz[0].length; col++) {
                System.out.printf("|%10d", integers[col]);//Cada valor ocupa 10 espacios
            }
            System.out.println("|");//Cerramos borde lateral para esta fila de celdas
        }
        bordesCeldas(matriz[0].length); //Cerramos el borde inferior de la tabla ya completada
    }

    private static void bordesCeldas(int numCeldas) {
        for (int c = 0; c < numCeldas; c++)
            System.out.print("+----------");
        System.out.println("+");
    }

    private void creacionDeMatrizDeAsignacionOriginal(Integer[][] matrizAsignacion) {
        matrizAsignacionOriginal = new Integer[this.cantidadTrabajadores][this.cantidadPuestos];
        for (int i = 0; i < this.cantidadTrabajadores; i++) {
            System.arraycopy(matrizAsignacion[i], 0, matrizAsignacionOriginal[i], 0, this.cantidadPuestos);
        }
    }

    private Integer[][] creacionDeMatrizDeAsignacionFactible(Integer[][] matrizAsignacion) {
        var matrizFactible = new Integer[this.cantidadTrabajadores][this.cantidadPuestos];
        for (int i = 0; i < this.cantidadTrabajadores; i++) {
            System.arraycopy(matrizAsignacion[i], 0, matrizFactible[i], 0, this.cantidadPuestos);
        }
        return matrizFactible;
    }


}   

    

