/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.firefly.core;

import cl.uv.firefly.Config;
import cl.uv.firefly.utils.Logs;
import cl.uv.firefly.utils.Utils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;







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
    //private Random random;
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
            nuevaLuciernaga[i] = instancia.getRandom().nextInt(2);
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
        int countVecesReparada = 0;
//        Logs.normal.println("\nVALIDADNDO--------------");
//        Utils.printArray(solucion); Logs.normal.println(": [REP] antes de reparar");
        int indiceJPrimerUno = -1;
        for (int i = 0; i < instancia.getMatrix().length; i++) { // recorriendo restricciones
//            Utils.printArray(instancia.getMatrix()[i]); Logs.normal.println(": [REP] restriccion");
            indiceJPrimerUno = -1;
            factible = true;
            for (int j = 0; j < instancia.getMatrix()[i].length; j++) { // recorriendo componentes de restriccion
                if( instancia.getMatrix()[i][j]==1 && solucion[j]==0) {
//                  solucion[j] = 1;
                    if(factible) indiceJPrimerUno=j;
                    factible = false;
                }else if( instancia.getMatrix()[i][j]==1 && solucion[j]==1){
                    factible = true;
                    break;
                }
            }
            if(!factible && indiceJPrimerUno!=-1){
//                countVecesReparada++;
                solucion[indiceJPrimerUno] = 1;
            }
        }
//        if(countVecesReparada>0){
//            Utils.printArray(solucion); Logs.normal.println(": [REP] despues de reparar");
//            Logs.normal.println("[REP] Reparada "+countVecesReparada+" veces!");
//        }else{
//            Logs.normal.println("[REP] La solucion es factible, no se reparó ninguna vez");
//        }
        
//        Logs.normal.println("FIN VALIDADNDO--------------");
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
//        Random r1 = new Random(System.currentTimeMillis());
//        Random r2 = new Random(System.currentTimeMillis());
//        Random r1 = random;
//        Random r2 = random;
       
        
        for (int i = 0; i < solucion.length; i++) {
            r += Math.pow(solucion[i]-bestSolucion[i],2);
        }
        for (int i = 0; i < solucion.length; i++) {
            nuevoMvto = solucion[i] + Config.B0*Math.exp((double)(-this.instancia.GAMMA*r))* 
                    (bestSolucion[i]-solucion[i]) + this.instancia.ALFA*
                    (instancia.getRandom().nextDouble() - 0.5); 
            //double s = 1/(1+Math.exp(-nuevoMvto));
            //nuevaSolucion[i] = s < Math.abs(instancia.getRandom().nextDouble()-0.5) ? 1:0;
            double tanh = Math.tanh( (Math.exp(2*Math.abs(nuevoMvto)-1)) / (Math.exp(2*Math.abs(nuevoMvto)+1)) );
            nuevaSolucion[i] = tanh > instancia.getRandom().nextFloat() ? 1 : 0;
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
    
//    public void printSolucion(){
//        for(int bit: solucion ){
//            System.out.print(bit+" ");
//        }
//    }
    
//    public String solucionString(){
//        String str ="";
//        for(int i:solucion){
//            str+=i+" ";
//        }
//        return str;
//    }
    
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