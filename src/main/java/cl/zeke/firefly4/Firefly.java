/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.zeke.firefly4;

/**
 *
 * @author gestion pc01
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Firefly {
    static Random random = new Random();
    static long seed;
    static int[][] matrix = null;
    static int cantRestricciones, cantCostos;
    static ArrayList<Integer> vectorCostos = new ArrayList<Integer>(); //vector de costos
    static ArrayList<Luciernaga> luciernagas = new ArrayList();
    static Luciernaga bestLuciernaga;
    static int cantLuciernagas=25;
    static int numIteraciones=1000;
    
    // valores recomendados segun investigaciones
    static int B0 = 1; 
    static double gamma=1.0;
    static double alfa=0.2;
    
    public static void main(String[] args) {
        Input.leerInstancia();
        System.out.println("Matriz de: "+matrix.length+"x"+matrix[0].length);
               
        seed = System.currentTimeMillis();
        random.setSeed(seed);
        
        for (int k = 0; k < cantLuciernagas; k++){
//            int[] solucion = generarSolucionAleatoria();
//            solucion = validarYReparar(solucion);
            Luciernaga luciernaga = new Luciernaga();
            luciernaga.validarYReparar();
            luciernagas.add(luciernaga);
        }
        Collections.sort(luciernagas, new LuciernagaComparator());
        for(Luciernaga l:luciernagas){
            System.out.println(l.getFitness());
        }    
        bestLuciernaga = luciernagas.get(0);
        int generacion=1;
        System.out.println("Comenzando iteraciones...");
        while(generacion <= numIteraciones){
            for (Luciernaga luciernaga:luciernagas) {
                System.out.print("Movimiento: "+luciernaga.getFitness()+" ==> ");
                Luciernaga nuevaLuciernaga = aplicarMovimiento(luciernaga, bestLuciernaga);
                if( nuevaLuciernaga.brillaMasQue(luciernaga) ) luciernaga = nuevaLuciernaga;
                if( nuevaLuciernaga.brillaMasQue(bestLuciernaga) ) bestLuciernaga = nuevaLuciernaga;
                luciernaga.calcularFitness();
                luciernaga.validarYReparar();
                System.out.println(luciernaga.getFitness());
//                luciernaga.setSolucion(validarYReparar(luciernaga.getSolucion()));
            }
            Collections.sort(luciernagas, new LuciernagaComparator());
            if (luciernagas.get(0).brillaMasQue( bestLuciernaga )){
                bestLuciernaga = luciernagas.get(0);
            }
            System.out.println("Generacion "+generacion+": "+bestLuciernaga.getFitness());
            generacion++;
        }
        System.out.println("\nMejor solucion:");
        for(int bit: bestLuciernaga.getSolucion() ){
            System.out.print(bit);
        }
        System.out.println("\nFitness: "+bestLuciernaga.getFitness() );
    }
    
    private static Luciernaga aplicarMovimiento(Luciernaga oldLuciernaga, Luciernaga bestLuciernaga) {
        int[] nuevaSolucion = new int[oldLuciernaga.getSolucion().length];
        int[] solucion = oldLuciernaga.getSolucion();
        int[] bestSolucion = bestLuciernaga.getSolucion();
        int r=0;
        double nuevoMvto=0;
        for (int i = 0; i < solucion.length; i++) {
            r += Math.pow(solucion[i]-bestSolucion[i],2);
        }
        for (int i = 0; i < solucion.length; i++) {
            nuevoMvto = solucion[i] + B0*Math.exp((double)(-gamma*r))* 
                    (bestSolucion[i]-solucion[i]) + alfa*
                    (random.nextDouble() - 0.5); 
            double s = 1/(1+Math.exp(-nuevoMvto));
            nuevaSolucion[i] = s<random.nextDouble() ? 1:0;
        }
        Luciernaga nuevaLuciernaga = new Luciernaga(nuevaSolucion);
        return nuevaLuciernaga;
    }
    
    static class LuciernagaComparator implements Comparator<Luciernaga> {

        @Override
        public int compare(Luciernaga l1, Luciernaga l2) {
            return l1.getFitness() < l2.getFitness() ? -1
                   : l1.getFitness() == l2.getFitness() ? 0 : 1;
        }
        
    }
//    public static long getFitness(int[] luciernaga){
//        int fitness=0;
//        for (int i = 0; i < luciernaga.length; i++) {
//            fitness += vectorCostos.get(i)*luciernaga[i];
//        }
//        return fitness;
//    }
    
//    public static int[] validarYReparar(int[] luciernaga){
//        boolean factible = false;
//        System.out.println("\nreparando:");
//        printLuciernaga(luciernaga);
//        int indiceJPrimerUno = -1;
//        for (int i = 0; i < cantRestricciones; i++) { // recorriendo restricciones
//            for (int j = 0; j < cantCostos; j++) { // recorriendo componentes de restriccion
//                if( matrix[i][j] == 1) {
//                    indiceJPrimerUno=j;
//                    if (luciernaga[j]==1 ){
//                        factible= true;
//                        break;
//                    }
//                }
//            }
//        }
//        if(!factible && indiceJPrimerUno!=-1){
//            luciernaga[indiceJPrimerUno] = 1;
//        }
//        System.out.println("\nreparada:");
//        printLuciernaga(luciernaga);
//        return luciernaga;
//    }
    
//    public static int[] generarSolucionAleatoria(){
//        int[] nuevaLuciernaga = new int[cantCostos];
//        for (int i = 0; i < cantCostos; i++) {
//            nuevaLuciernaga[i] = Math.round(random.nextFloat());
//        }
//        return nuevaLuciernaga;
//    }
    
    

}
