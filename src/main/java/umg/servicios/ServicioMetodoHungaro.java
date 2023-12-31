/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package umg.servicios;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import umg.utilidades.Utilidades;

/**
 *
 * @author Javier Barahona
 */
public class ServicioMetodoHungaro {
    int cantidadPuestos;
    int cantidadTrabajadores;
    int matrizAsignacion[][];
    
    public ServicioMetodoHungaro(){
    
    }
    
    public void mostrarMenu() {
       boolean noEsValido = true;
       
       do {
           
            String opcion = (JOptionPane.showInputDialog(null,
                        "Menú Principal \n "
                        + "1. Método Húngaro \n"
                        + "\n"
                        ,"Investigación de operaciones",
                        JOptionPane.INFORMATION_MESSAGE,null,null,"")).toString(); 
            
            if(Utilidades.validarNumero(opcion)){
                if(Integer.parseInt(opcion) == 1){
                    ingresarValoresDeMatriz();
                    noEsValido = false;
                } else {
                    JOptionPane.showMessageDialog(null,"Debe de ingresar una opción válida.","Error",JOptionPane.ERROR_MESSAGE);
                    noEsValido = true;
                }
            } else {
               JOptionPane.showMessageDialog(null,"Debe de ingresar un número.","Error",JOptionPane.ERROR_MESSAGE); 
               noEsValido = true;
            }
       } while (noEsValido);
    }
    
    public void ingresarValoresDeMatriz(){
        try {
            ingresarNumeroFilas();
            ingresarNumeroColumnas();
            creacionDeMatrizDeAsignacion();
        } catch(Exception e) {
            
        }
    }
    
    private void ingresarNumeroFilas(){
       
        String cantidadFilas = (JOptionPane.showInputDialog(null,"Ingrese el número de trabajadores:","Cantidad de trabajadores",JOptionPane.INFORMATION_MESSAGE,null,null,"")).toString();
          
          if(Utilidades.validarNumero(cantidadFilas)){
            if(Integer.parseInt(cantidadFilas) > 1){
                this.cantidadTrabajadores = Integer.parseInt(cantidadFilas);
            } else {
                JOptionPane.showMessageDialog(null,"Debe de ingresar un número mayor a 1.","Error",JOptionPane.ERROR_MESSAGE);
                ingresarNumeroFilas();
            }   
        } else {
            JOptionPane.showMessageDialog(null,"Debe de ingresar un número.","Error",JOptionPane.ERROR_MESSAGE);
            ingresarNumeroFilas();
        }
    }
    
    private void ingresarNumeroColumnas(){
     
        String cantidadColumnas = (JOptionPane.showInputDialog(null,"Ingrese el número de puestos:","Cantidad de puestos",JOptionPane.INFORMATION_MESSAGE,null,null,"")).toString();
          
        if(Utilidades.validarNumero(cantidadColumnas)){
            if(Integer.parseInt(cantidadColumnas) > 1){
                this.cantidadPuestos = Integer.parseInt(cantidadColumnas);
            } else {
                JOptionPane.showMessageDialog(null,"Debe de ingresar un número mayor a 1.","Error",JOptionPane.ERROR_MESSAGE);
                ingresarNumeroColumnas();
            }   
        } else {
              JOptionPane.showMessageDialog(null,"Debe de ingresar un número.","Error",JOptionPane.ERROR_MESSAGE);
              ingresarNumeroColumnas();
        }
        
    }
    
    public void creacionDeMatrizDeAsignacion(){
        this.matrizAsignacion = new int[this.cantidadTrabajadores][this.cantidadPuestos];
        boolean esValido = false;
      
        for(int i=0; i<this.cantidadTrabajadores; i++){
            for(int j=0; j<this.cantidadPuestos; j++){
               do {
                    String costo = (JOptionPane.showInputDialog(null,"Ingrese el costo del puesto " +(j+1)+" para el trabajador "+(i+1)+":",
                                "Cantidad de trabajadores",JOptionPane.INFORMATION_MESSAGE,
                                null,null,"")).toString();
                
                    if(Utilidades.validarNumero(costo)){
                        if(Integer.parseInt(costo) > 0){
                            this.matrizAsignacion[i][j]= Integer.parseInt(costo);
                            esValido = true;
                        } else {
                            esValido = false;
                            JOptionPane.showMessageDialog(null,"Debe de ingresar un número mayor a cero.","Error",JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        esValido = false;
                        JOptionPane.showMessageDialog(null,"Debe de ingresar un número.","Error",JOptionPane.ERROR_MESSAGE);
                    } 
               } while (!esValido);
            }
        }
        
        try {
            mostrarMatriz(this.matrizAsignacion);
           
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Ocurrió un error, vuelve a intentarlo.","Error",JOptionPane.ERROR_MESSAGE);
        }
    } 
    
    public void mostrarMatriz(int[][] matriz) {
        String columnas[] = new String[this.cantidadPuestos+1];
      
        columnas[0] = "    ";
        for(int i = 1; i <= this.cantidadPuestos; i++ ){
           columnas[i] = "Puesto " + i;
        }

        String[][] matrizAMostrar = new String[this.cantidadTrabajadores][this.cantidadPuestos + 1];
      
        for(int i = 0; i < this.cantidadTrabajadores; i++){
            matrizAMostrar[i][0] = "Trabajador " + (i + 1);
        }
      
        for (int i = 0; i < this.cantidadTrabajadores; i++) {
            for (int j = 1; j <= this.cantidadPuestos; j++) {
                matrizAMostrar[i][j] = String.valueOf(matriz[i][j-1]);
            }
        }
        
        JTable tablaDeDatos = new JTable(matrizAMostrar,columnas) {
          public boolean editCellAt(int row, int column, java.util.EventObject e) {
            return false;
         }
        };
      
        tablaDeDatos.setRowSelectionAllowed(false);
        tablaDeDatos.setFont(new Font("Arial", Font.CENTER_BASELINE, 15));
        tablaDeDatos.getTableHeader().setFont(new Font("Arial", Font.CENTER_BASELINE, 15));
        tablaDeDatos.getTableHeader().setReorderingAllowed(false);
        tablaDeDatos.getTableHeader().setResizingAllowed(false);
        tablaDeDatos.setRowHeight(30);
        tablaDeDatos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        DefaultTableCellRenderer alineacionTexto = new DefaultTableCellRenderer();
        alineacionTexto.setHorizontalAlignment( JLabel.CENTER );
        for(int i=0; i<this.cantidadPuestos + 1; i++){
            tablaDeDatos.getColumnModel().getColumn(i).setCellRenderer(alineacionTexto);
        }
        
     
        JScrollPane panel = new JScrollPane();
        panel.setViewportView(tablaDeDatos);
        panel.setPreferredSize(new Dimension(cantidadTrabajadores * 200,cantidadPuestos * 55));

        JOptionPane.showMessageDialog(null, panel, "Método Húngaro",
            JOptionPane.INFORMATION_MESSAGE);
        
         System.out.println("Menores  de fila");
         
         for(int i=0; i<buscarMinimoPorFila(this.matrizAsignacion).length; i++){
             System.out.println("Fila " + (i+1) + " " + buscarMinimoPorFila(this.matrizAsignacion)[i]);
         }
         
         System.out.println("Minimo de columna");
         
         for(int i=0; i<buscarMinimoPorColumna(this.matrizAsignacion).length; i++){
             System.out.println("Columna " + (i+1) + " " + buscarMinimoPorColumna(this.matrizAsignacion)[i]);
         }
   }
    
    
   public int[] buscarMinimoPorFila(int[][] matriz) {
       int[] minimoPorFila = new int[this.cantidadTrabajadores];
       int menorTemporal = 0;
       
        for(int i=0; i<this.cantidadTrabajadores; i++){
            menorTemporal = matriz[i][0];
            for(int j=0; j<this.cantidadPuestos;j++){
                if(matriz[i][j] < menorTemporal) {
                    menorTemporal = matriz[i][j];
                }
            }
            minimoPorFila[i] = menorTemporal;
        }
       
      return minimoPorFila;
   }
   
   public int[] buscarMinimoPorColumna(int[][] matriz) {
       int[] minimoPorColumna = new int[this.cantidadPuestos];
       int minimoTemporal = 0;
       
        for(int i=0; i<this.cantidadPuestos; i++){
            minimoTemporal = matriz[0][i];
            for(int j=0; j<this.cantidadTrabajadores;j++){
                if(matriz[j][i] < minimoTemporal) {
                    minimoTemporal = matriz[j][i];
                }
            }
            minimoPorColumna[i] = minimoTemporal;
        }
       
      return minimoPorColumna;
   }
}   

    

