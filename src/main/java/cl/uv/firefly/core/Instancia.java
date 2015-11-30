/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.firefly.core;

/**
 *
 * @author gestion pc01
 */

import cl.uv.firefly.Config;
import cl.uv.firefly.utils.Logs;
import cl.uv.firefly.io.Input;
import cl.uv.firefly.utils.Utils;
import java.util.ArrayList;
import java.util.Collections;

public class Instancia {
   
    public double GAMMA; // [0.1,10]
    public double ALFA;  // [0,1]
    
    
    private String nombreLog;
    private String nombreArchivo;
    private String nombre;
    private String path;
    private int cambiosParaMejor=0;
    

    public Instancia(String nombreArchivo, double alfa, double gamma) {
        this.nombreArchivo = nombreArchivo;
        this.path = Config.instanciasPath+nombreArchivo;
        this.nombre = nombreArchivo.substring(0, nombreArchivo.lastIndexOf("."));
        this.GAMMA = gamma;
        this.ALFA = alfa;
    }
    
    private int[][] matrix = null;
    private int cantRestricciones, cantCostos;
    private ArrayList<Integer> vectorCostos = new ArrayList<Integer>(); //vector de costos
    private ArrayList<Luciernaga> luciernagas = new ArrayList();
    private Luciernaga bestLuciernaga;

//    private Logs logNormal = Logs.normal;
    private Logs logImportante = Logs.importante;
    
    private void generarLuciernagas(){
        for (int k = 0; k < Config.CANT_LUCIERNAGAS; k++){
            Luciernaga luciernaga = new Luciernaga(this);
            luciernagas.add(luciernaga);
        }
        Collections.sort(luciernagas, new LuciernagaComparator());
        bestLuciernaga = luciernagas.get(0);
    }
    
    public void ejecutarInstancia() {
        
//        logNormal.println("Generando luciernagas");
        generarLuciernagas();
//        logImportante.print( bestLuciernaga.solucionString() );
        Logs.importante.println("Generacion 0: "+bestLuciernaga.getFitness());
//        logNormal.println(": [MVTO] best desp\n");
//        logNormal.println("===================================================================");
        
//        logNormal.println("Comenzando iteraciones...");
        Luciernaga nuevaLuciernaga;
//        ArrayList<Luciernaga> luciernagasAux = new ArrayList();
        
        int contadorNoCambioBestLuciernaga=0, numeroCambiosObligatorios=0;
        for(int generacion=1; generacion <= Config.NUM_ITERACIONES; generacion++){
//            luciernagasAux = new ArrayList();
            
//            logNormal.println(": [MVTO] best prev");
            for (int i = 1; i < luciernagas.size(); i++) { 
                
//                logNormal.println("Movimiento "+i+":");
                
//                Utils.printArray(luciernagas.get(i).getSolucion());
//                logNormal.print(": [MVTO] valor previo");
                
                nuevaLuciernaga = luciernagas.get(i).aplicarMovimiento(bestLuciernaga);
                
                
//                Utils.printArray(luciernagas.get(i).getSolucion());
//                logNormal.println(": [MVTO] 'mismo valor'");
                
//                Utils.printArray(nuevaLuciernaga.getSolucion());
//                logNormal.println(": [MVTO] 'nuevo valor'");
                
                if( nuevaLuciernaga.brillaMasQue(luciernagas.get(i)) ){
//                    luciernagasAux.add(nuevaLuciernaga);
//                    logNormal.print(" :DD ");
                    luciernagas.set(i, nuevaLuciernaga);
                    //luciernagas.get(i) = nuevaLuciernaga;
                }else{
//                    luciernagasAux.add(luciernagas.get(i));
//                    logNormal.print(" --- ");
                }
                
//                if( nuevaLuciernaga.brillaMasQue(bestLuciernaga) ){
//                    bestLuciernaga = nuevaLuciernaga;
//                }
                
//                logNormal.println(nuevaLuciernaga.getFitness());
            }
            
//            luciernagasAux.add(luciernagas.get(0));
//            luciernagas = luciernagasAux;
            Collections.sort(luciernagas, new LuciernagaComparator());
            
            if (luciernagas.get(0).brillaMasQue( bestLuciernaga )){
                bestLuciernaga = luciernagas.get(0);
                Logs.importante.println("Generacion "+generacion+": "+bestLuciernaga.getFitness());
//                logImportante.println("### :DD ###");
                contadorNoCambioBestLuciernaga=0;
                cambiosParaMejor++;
            }else contadorNoCambioBestLuciernaga++;
            
            if (contadorNoCambioBestLuciernaga >= Config.PORCENTAJE_NO_CAMBIO_PERMITIDO*Config.NUM_ITERACIONES/100){
                numeroCambiosObligatorios++;
                contadorNoCambioBestLuciernaga=0;
                bestLuciernaga = new Luciernaga(this);
                luciernagas.set(0, bestLuciernaga);
                logImportante.println("!---> EXPLORANDO. NUEVA BEST LUCIERNAGA: "+bestLuciernaga+"");
            }
//            logImportante.print( bestLuciernaga.solucionString() );
            
            
            
            
//            logNormal.println(": [MVTO] best desp");
//            logNormal.println("");
//            logNormal.println("===================================================================");
            
            //Grafico.agregarValor(bestLuciernaga.getFitness());
        }
        //Grafico.generarGrafico();
        Logs.importante.println("\n----------------------------------------------------------");
        Logs.importante.println("\nMejor Solucion: "+bestLuciernaga.getFitness() );
        Logs.importante.println("Cambios obligatorios:"+ numeroCambiosObligatorios+"/"+Config.NUM_ITERACIONES);
        Logs.importante.println("Cambios para mejor:"+ cambiosParaMejor+"/"+Config.NUM_ITERACIONES);
        
        
    }
    
    
    
    ///....

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getCantRestricciones() {
        return cantRestricciones;
    }

    public void setCantRestricciones(int cantRestricciones) {
        this.cantRestricciones = cantRestricciones;
    }

    public int getCantCostos() {
        return cantCostos;
    }

    public void setCantCostos(int cantCostos) {
        this.cantCostos = cantCostos;
    }

    public ArrayList<Integer> getVectorCostos() {
        return vectorCostos;
    }

    public void setVectorCostos(ArrayList<Integer> vectorCostos) {
        this.vectorCostos = vectorCostos;
    }

    public ArrayList<Luciernaga> getLuciernagas() {
        return luciernagas;
    }

    public void setLuciernagas(ArrayList<Luciernaga> luciernagas) {
        this.luciernagas = luciernagas;
    }

    public Luciernaga getBestLuciernaga() {
        return bestLuciernaga;
    }

    public void setBestLuciernaga(Luciernaga bestLuciernaga) {
        this.bestLuciernaga = bestLuciernaga;
    }

//    public Logs getLogNormal() {
//        return logNormal;
//    }
//
//    public void setLogNormal(Logs logNormal) {
//        this.logNormal = logNormal;
//    }
//
//    public Logs getLogImportante() {
//        return logImportante;
//    }
//
//    public void setLogImportante(Logs logImportante) {
//        this.logImportante = logImportante;
//    }

    public int getCambiosParaMejor() {
        return cambiosParaMejor;
    }

    public String getNombreLog() {
        return nombreLog;
    }

    public void setNombreLog(String nombreLog) {
        this.nombreLog = nombreLog;
    }
    
    
    
    
    
    
}
