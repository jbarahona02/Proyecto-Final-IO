/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package umg.proyectofinal.io.servicios;

import umg.proyectofinal.io.utilidades.Utilidades;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ServicioMetodoHungaro {
    private int cantidadPuestos;
    private int cantidadTrabajadores;
    private int[][] matrizAsignacion;

    public ServicioMetodoHungaro() {

    }

    public void mostrarMenu() {
        boolean noEsValido = true;
        do {

            String opcion = (JOptionPane.showInputDialog(null,
                    """
                            Menú Principal\s
                            1. Método Húngaro\s
                            """
                    ,"Investigación de operaciones",
                    JOptionPane.INFORMATION_MESSAGE,null,null,"")).toString();

            if(Utilidades.validarNumero(opcion)){
                if(Integer.parseInt(opcion) == 1){
                    ingresarValoresDeMatriz();
                    noEsValido = false;
                } else {
                    JOptionPane.showMessageDialog(null,"Debe de ingresar una opción válida.","Error",JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,"Debe de ingresar un número.","Error",JOptionPane.ERROR_MESSAGE);
            }
        } while (noEsValido);
    }

    private void ingresarValoresDeMatriz() {
        try {
            ingresarNumeroFilasYColumnas();
            creacionDeMatrizDeAsignacion();
        } catch (Exception e) {
            System.out.println("Error ingresando valores de matriz " + e.getMessage());
        }
    }

    private void ingresarNumeroFilasYColumnas() {
        var valor = JOptionPane.showInputDialog(null, "Ingrese el número de trabajadores y tareas: ", "Cantidad de trabajadores y tareas", JOptionPane.INFORMATION_MESSAGE, null, null, "");
        var xy = String.valueOf(valor);

        if (!Utilidades.validarNumero(xy)) {
            JOptionPane.showMessageDialog(null,"Debe de ingresar un número.","Error",JOptionPane.ERROR_MESSAGE);
            ingresarNumeroFilasYColumnas();
        }

        var valorEntero = Integer.parseInt(xy);
        if (valorEntero <= 1) {
            JOptionPane.showMessageDialog(null, "Debe de ingresar un número mayor a 1.", "Error", JOptionPane.ERROR_MESSAGE);
            ingresarNumeroFilasYColumnas();
        }

        this.cantidadTrabajadores = valorEntero;
        this.cantidadPuestos = valorEntero;
    }

    private void creacionDeMatrizDeAsignacion() {
        this.matrizAsignacion = new int[this.cantidadTrabajadores][this.cantidadPuestos];
        boolean esValido;

        for (int i = 0; i < this.cantidadTrabajadores; i++) {
            for (int j = 0; j < this.cantidadPuestos; j++) {
                do {
                    var costo = (JOptionPane.showInputDialog(null, "Ingrese el costo del puesto " + (j + 1) + " para el trabajador " + (i + 1) + ":",
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
            mostrarMatriz(this.matrizAsignacion);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "Ocurrió un error, vuelve a intentarlo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarMatriz(int[][] matriz) {
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
        var metodoHungaro = new MetodoHungaro(matrizAsignacion);
        var resultados = metodoHungaro.resultados();
        var total = metodoHungaro.obtenerTotal();

        mostrarResultado(resultados, total);
    }

    private void mostrarResultado(int[] resultados, int total) {
        StringBuilder resultado = new StringBuilder("Asignación óptima:\n");
        for (int i = 0; i < resultados.length; i++) {
            resultado.append(String.format("Trabajador %d => Puesto %d (%d)%n", i + 1, resultados[i] + 1, matrizAsignacion[i][resultados[i]]));
        }
        resultado.append(String.format("\nTotal: %d%n", total)); // Total
        JOptionPane.showMessageDialog(null, resultado.toString());
    }




}   

    

