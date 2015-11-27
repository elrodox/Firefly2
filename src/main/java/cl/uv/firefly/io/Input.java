/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.firefly.io;

import cl.uv.firefly.Config;
import cl.uv.firefly.core.Instancia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import static cl.uv.firefly.core.Instancia.*;
/**
 *
 * @author elrodox
 */
public class Input {
    public static Instancia leerInstancia(Instancia instancia){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        int cantidad = 0; //cantidad de filas que lleva el texto
        int canNum = 0; // cantidad de numeros entrando al vector de costos
        int colRest = 0;//numero de restricciones por columna
        int i = 0;// columnas recorridas de la matriz
        int j = 0;// filas recorridas de la matriz
        int cantRestricciones=-1, cantCostos=-1;
        int[][] matrix = new int[0][0];
        ArrayList<Integer> vectorCostos = new ArrayList<Integer>();
        ArrayList<String> arrayTemp = new ArrayList<String>();
        
        String delimitadores = "[ .,;?!¡¿\'\"\\[\\]]+";
        try {
            archivo = new File(instancia.getPath());
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                if (cantidad < 1) {
                    String[] datos = linea.split(delimitadores);
                    cantRestricciones = Integer.parseInt(datos[1]); //se le asigna el primer valor del txt
                    cantCostos = Integer.parseInt(datos[2]); //se le asigna el segundo valor del txt

                    matrix = new int[cantRestricciones][cantCostos];
                } else {
                    if (canNum < cantCostos) { //agrega valores a vector de costos
                        String[] temp = linea.split(delimitadores);
                        canNum = canNum + temp.length - 1;
                        for (int x = 1; x <= temp.length - 1; x++) {
                            vectorCostos.add( Integer.parseInt(temp[x]) );
                        }
                    } else if (colRest == 0) {//recibir la cantidad de columnas con restricciones
                        String[] temp2 = linea.split(delimitadores);
                        colRest = Integer.parseInt(temp2[1]);
                        //System.out.println("cantidad: "+colRest);
                    } else {//agregamos posiciones de restricciones a arraylist temporal
                        String[] temp3 = linea.split(delimitadores);
                        for (int x = 1; x <= temp3.length - 1; x++) {
                            //System.out.println("valor: "+ temp3[x]);
                            arrayTemp.add(temp3[x]);
                        }
                        if (temp3.length - 1 < colRest) {//traspaso de array a matriz
                            colRest = colRest - (temp3.length - 1);
                        } else if (temp3.length - 1 == colRest) {//si el arreglo se llena, pasa a la siguiente restriccion
                            for (int x = 0; x < arrayTemp.size(); x++) {
                                j = Integer.parseInt(arrayTemp.get(x));
                                matrix[i][j - 1] = 1;
                                //System.out.println("segundo if, posicion [" + i + "],[" + j + "]: " + matrizRest[i][j-1]);

                            }
                            arrayTemp.clear();
                            i++;
                            colRest = 0;
                        }

                    }
                }
                cantidad++;
            }
            instancia.setCantRestricciones(cantRestricciones);
            instancia.setCantCostos(cantCostos);
            instancia.setMatrix(matrix);
            instancia.setVectorCostos(vectorCostos);
            return instancia;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
    
}
