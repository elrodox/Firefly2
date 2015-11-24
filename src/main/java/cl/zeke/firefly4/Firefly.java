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

import cl.zeke.firefly4.Config;
import cl.zeke.firefly4.Utils;
import java.util.ArrayList;
import java.util.Collections;

public class Firefly {
    
    static int[][] matrix = null;
    static int cantRestricciones, cantCostos;
    static ArrayList<Integer> vectorCostos = new ArrayList<Integer>(); //vector de costos
    static ArrayList<Luciernaga> luciernagas = new ArrayList();
    static Luciernaga bestLuciernaga;
    
    
    public static void generarLuciernagas(){
        for (int k = 0; k < Config.CANT_LUCIERNAGAS; k++){
            Luciernaga luciernaga = new Luciernaga();
            luciernagas.add(luciernaga);
        }
        Collections.sort(luciernagas, new LuciernagaComparator());
        bestLuciernaga = luciernagas.get(0);
    }
    
    public static void main(String[] args) {
        Input.leerInstancia();
        Logs.println("Matriz de: "+matrix.length+"x"+matrix[0].length);
        generarLuciernagas();
        
        System.out.println("Semilla: "+Config.seed);
        
        Logs.println("Comenzando iteraciones...");
        for(int generacion=1; generacion <= Config.NUM_ITERACIONES; generacion++){
            for (int i = 1; i < luciernagas.size(); i++) {  
                Logs.print("Movimiento: "+luciernagas.get(i).getFitness()+" ==> ");
                Luciernaga nuevaLuciernaga = aplicarMovimiento(luciernagas.get(i), bestLuciernaga);
                
                if( nuevaLuciernaga.brillaMasQue(luciernagas.get(i)) ){ 
                    luciernagas.set(i, nuevaLuciernaga);
                    //luciernagas.get(i) = nuevaLuciernaga;
                }
                
                if( nuevaLuciernaga.brillaMasQue(bestLuciernaga) ){
                    bestLuciernaga = nuevaLuciernaga;
                }
                
                Logs.println(nuevaLuciernaga.getFitness());
            }
            Collections.sort(luciernagas, new LuciernagaComparator());
            if (luciernagas.get(0).brillaMasQue( bestLuciernaga )){
                bestLuciernaga = luciernagas.get(0);
            }
            System.out.println("Generacion "+generacion+": "+bestLuciernaga.getFitness());
            Grafico.agregarValor(bestLuciernaga.getFitness());
        }
        Grafico.generarGrafico();
        Logs.println("\nMejor solucion:");

        Logs.setActive(true);
        Logs.println("\nFitness: "+bestLuciernaga.getFitness() );
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
            nuevoMvto = solucion[i] + Config.B0*Math.exp((double)(-Config.GAMMA*r))* 
                    (bestSolucion[i]-solucion[i]) + Config.ALFA*
                    (Utils.random.nextDouble() - 0.5); 
            double s = 1/(1+Math.exp(-nuevoMvto));
            nuevaSolucion[i] = s<Utils.random.nextDouble() ? 1:0;
        }
        Luciernaga nuevaLuciernaga = new Luciernaga(nuevaSolucion);
        return nuevaLuciernaga;
    }
    
}
