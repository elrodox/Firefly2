/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.firefly.core;

import cl.uv.firefly.Config;
import cl.uv.firefly.utils.Utils;
import java.util.ArrayList;
import java.util.Comparator;







/**
 *
 * @author Usuario
 */
public class Luciernaga {
    
    private Instancia instancia;
    // datos de instancia
//    private int cantRestricciones=-1, cantCostos=-1;
//    private int[][] matrix = new int[0][0];
//    private ArrayList<Integer> vectorCostos = new ArrayList<Integer>();
    
    private int[] solucion;
    private int fitness;
    
    public Luciernaga(Instancia instancia) {
        this.instancia = instancia;
        this.solucion = generarSolucionAleatoria();
        this.validarYReparar();
        this.calcularFitness();
    }
    public Luciernaga(int[] solucion, Instancia instancia) {
        this.instancia = instancia;
        this.solucion = solucion;
        this.validarYReparar();
        this.calcularFitness();
    }
    
    public int[] generarSolucionAleatoria(){
        int[] nuevaLuciernaga = new int[instancia.getCantCostos()];
        for (int i = 0; i < instancia.getCantCostos(); i++) {
            nuevaLuciernaga[i] = Utils.random.nextInt(2);
        }
        return nuevaLuciernaga;
    }
    
    private void calcularFitness(){
        fitness=0;
        for (int i = 0; i < solucion.length; i++) {
            fitness += instancia.getVectorCostos().get(i)*solucion[i];
        }
    }
    
    private void validarYReparar(){
        boolean factible = true;
//        System.out.println("\nreparando:");
//        printSolucion();
        int indiceJPrimerUno = -1;
        for (int i = 0; i < instancia.getCantRestricciones(); i++) { // recorriendo restricciones
            indiceJPrimerUno = -1;
            for (int j = 0; j < instancia.getCantCostos(); j++) { // recorriendo componentes de restriccion
                if( instancia.getMatrix()[i][j]==1 && solucion[j]==0) {
//                  solucion[j] = 1;
                    indiceJPrimerUno=j;
                    factible= false;
                }else factible=true;
            }
            if(!factible && indiceJPrimerUno!=-1){
                solucion[indiceJPrimerUno] = 1;
            }
        }
        
        this.calcularFitness();
//        System.out.println("\nreparada:");
//        printSolucion();
    }
    
    
    public Luciernaga aplicarMovimiento(Luciernaga bestLuciernaga) {
        int[] nuevaSolucion = new int[this.getSolucion().length];
        int[] solucion = this.getSolucion();
        int[] bestSolucion = bestLuciernaga.getSolucion();
        int r=0;
        double nuevoMvto=0;
        for (int i = 0; i < solucion.length; i++) {
            r += Math.pow(solucion[i]-bestSolucion[i],2);
        }
        for (int i = 0; i < solucion.length; i++) {
            nuevoMvto = solucion[i] + Config.B0*Math.exp((double)(-Config.GAMMA*r))* 
                    (bestSolucion[i]-solucion[i]) + Config.ALFA*
                    (Utils.random.nextDouble() - 0.5); 
            double s = 1/(1+Math.exp(-nuevoMvto));
            nuevaSolucion[i] = s<Utils.random.nextDouble() ? 1:0;
        }
        Luciernaga nuevaLuciernaga = new Luciernaga(nuevaSolucion, this.instancia);
        return nuevaLuciernaga;
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
    
    public String toString(){
        return String.valueOf(fitness);
    }
    
}

class LuciernagaComparator implements Comparator<Luciernaga> {

    @Override
    public int compare(Luciernaga l1, Luciernaga l2) {
        return l1.getFitness() < l2.getFitness() ? -1
               : l1.getFitness() == l2.getFitness() ? 0 : 1;
    }

}