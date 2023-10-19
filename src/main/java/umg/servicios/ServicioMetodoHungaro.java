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
            this.cantidadPuestos = Integer.parseInt(cantidadFilas);
        } else {
            JOptionPane.showMessageDialog(null,"Debe de ingresar un número.","Error",JOptionPane.ERROR_MESSAGE);
            ingresarNumeroFilas();
        }
    }
    
    private void ingresarNumeroColumnas(){
     
        String cantidadColumnas = (JOptionPane.showInputDialog(null,"Ingrese el número de puestos:","Cantidad de puestos",JOptionPane.INFORMATION_MESSAGE,null,null,"")).toString();
          
        if(Utilidades.validarNumero(cantidadColumnas)){
              this.cantidadTrabajadores = Integer.parseInt(cantidadColumnas);
        } else {
              JOptionPane.showMessageDialog(null,"Debe de ingresar un número.","Error",JOptionPane.ERROR_MESSAGE);
              ingresarNumeroColumnas();
        }
        
    }
    
    public void creacionDeMatrizDeAsignacion(){
        balancearMatriz();
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
        
        if(this.cantidadTrabajadores > this.cantidadPuestos){
            balancearPuestos();
        } else if (this.cantidadTrabajadores < this.cantidadPuestos) {
            balancearTrabajadores();
        }
       
        try {
            mostrarMatriz(this.matrizAsignacion);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null,"Ocurrió un error, vuelve a intentarlo.","Error",JOptionPane.ERROR_MESSAGE);
        }
    } 
    
    public void balancearMatriz(){
        int cantidadTrabajadoresBalanceados = 0;
        int cantidadPuestosBalanceados = 0;
        
        if(this.cantidadTrabajadores == this.cantidadPuestos) {
            cantidadTrabajadoresBalanceados = this.cantidadTrabajadores;
            cantidadPuestosBalanceados = this.cantidadPuestos;
        } else if (this.cantidadTrabajadores > this.cantidadPuestos) {
            cantidadTrabajadoresBalanceados = this.cantidadTrabajadores;
            cantidadPuestosBalanceados = this.cantidadPuestos + (this.cantidadTrabajadores - this.cantidadPuestos);
        } else {
            cantidadTrabajadoresBalanceados = this.cantidadTrabajadores + (this.cantidadPuestos - this.cantidadTrabajadores);
            cantidadPuestosBalanceados = this.cantidadPuestos;
        }
        
        this.matrizAsignacion = new int[cantidadTrabajadoresBalanceados][cantidadPuestosBalanceados];
    }
    
    public void balancearTrabajadores(){
        for(int i=0;i<this.cantidadPuestos; i++){
          this.matrizAsignacion[this.cantidadPuestos][i] = 0;
        }
    }
    
    public void balancearPuestos(){
        for(int i=0;i<this.cantidadTrabajadores; i++){
            this.matrizAsignacion[i][this.cantidadTrabajadores] = 0;
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
        panel.setPreferredSize(new Dimension(cantidadTrabajadores * 150,cantidadPuestos * 40));

        JOptionPane.showMessageDialog(null, panel, "Método Húngaro",
            JOptionPane.INFORMATION_MESSAGE);
   }
    
   public int[] buscarMinimoDeFila(int[][] matriz) {
       int[] minimoPorFila = new int[this.cantidadTrabajadores];
       /*ArrayList<int> listaNumeros = new ArrayList<int>();
       for(int i = 0; i<this.cantidadTrabajadores; i++){
           for(int j =0; j<this.cantidadPuestos; j++){
               listaNumeros[j] = matriz[i][j];
           }
           
           listaNumeros.
       }*/
      return minimoPorFila;
   }
}   

    

