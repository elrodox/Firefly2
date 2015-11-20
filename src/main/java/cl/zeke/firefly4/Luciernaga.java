/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.zeke.firefly4;

import static cl.zeke.firefly4.Firefly.cantCostos;
import static cl.zeke.firefly4.Firefly.cantRestricciones;
import static cl.zeke.firefly4.Firefly.matrix;
import static cl.zeke.firefly4.Firefly.random;
import static cl.zeke.firefly4.Firefly.vectorCostos;






/**
 *
 * @author Usuario
 */
public class Luciernaga {
    private int[] solucion;
    private int fitness;

    public Luciernaga() {
        this.solucion = generarSolucionAleatoria();
        calcularFitness();
    }
    public Luciernaga(int[] solucion) {
        this.solucion = solucion;
        calcularFitness();
    }
    
    public static int[] generarSolucionAleatoria(){
        int[] nuevaLuciernaga = new int[cantCostos];
        for (int i = 0; i < cantCostos; i++) {
            nuevaLuciernaga[i] = random.nextInt(2);
        }
        return nuevaLuciernaga;
    }
    
    public void calcularFitness(){
        fitness=0;
        for (int i = 0; i < solucion.length; i++) {
            fitness += vectorCostos.get(i)*solucion[i];
        }
    }
    
    public void validarYReparar(){
        boolean factible = false;
//        System.out.println("\nreparando:");
//        printSolucion();
        int indiceJPrimerUno = -1;
        for (int i = 0; i < cantRestricciones; i++) { // recorriendo restricciones
            for (int j = 0; j < cantCostos; j++) { // recorriendo componentes de restriccion
                if( matrix[i][j] == 1) {
                    indiceJPrimerUno=j;
                    if (solucion[j]==1 ){
                        factible= true;
                        break;
                    }
                }
            }
        }
        if(!factible && indiceJPrimerUno!=-1){
            solucion[indiceJPrimerUno] = 1;
        }
//        System.out.println("\nreparada:");
//        printSolucion();
    }

    public int[] getSolucion() {
        return solucion;
    }

    public int getFitness() {
        return fitness;
    }
    
    public boolean brillaMasQue(Luciernaga l2){
        return this.fitness < l2.fitness;
    }

    public void setSolucion(int[] solucion) {
        this.solucion = solucion;
    }
    
    public void printSolucion(){
        for(int bit: solucion ){
            System.out.print(bit);
        }
        System.out.println("");
    }
    
}
