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
import cl.uv.firefly.utils.Utils;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Instancia {
   
    public double GAMMA; // [0.1,10]
    public double ALFA;  // [0,1]
    
    private String nombreLog;
    private String nombreArchivo;
    private String nombre;
    private String path;
    private int cambiosParaMejor=0;
            
    private int[][] matrix = null;
    private int cantRestricciones, cantCostos;
    private ArrayList<Integer> vectorCostos = new ArrayList<Integer>(); //vector de costos
    private ArrayList<Luciernaga> luciernagas = new ArrayList();
    private Luciernaga bestLuciernaga;

//    private Logs logNormal = Logs.normal;
    //private Logs logImportante = Logs.importante;
    private Logs console = new Logs(true);

    public Instancia(String nombreArchivo, double alfa, double gamma) {
        this.nombreArchivo = nombreArchivo;
        this.path = Config.instanciasPath+nombreArchivo;
        this.nombre = nombreArchivo.substring(0, nombreArchivo.lastIndexOf("."));
        this.GAMMA = gamma;
        this.ALFA = alfa;
    }
    
    private void generarLuciernagas(){
        for (int k = 0; k < Config.CANT_LUCIERNAGAS; k++){
            Luciernaga luciernaga = new Luciernaga(this);
            luciernagas.add(luciernaga);
        }
        Collections.sort(luciernagas, new LuciernagaComparator());
        bestLuciernaga = luciernagas.get(0);
    }
    
    public void ejecutarInstancia() {
        
        generarLuciernagas();
        Logs.importante.println("Generacion 0: "+bestLuciernaga.getFitness());
        Luciernaga nuevaLuciernaga;
        
        int contadorNoCambioBestLuciernaga=0, numeroCambiosObligatorios=0;
        Date inicio, fin;
        long millisPorCienGeneraciones;
        int generacionesRestantes;
        inicio = new Date();
        
        for(int generacion=1; generacion <= Config.NUM_ITERACIONES; generacion++){
            
            for (int i = 0; i < luciernagas.size(); i++) { 
                for (int j = 0; j < luciernagas.size(); j++) {
                    if( luciernagas.get(j).brillaMasQue(luciernagas.get(i)) ){
                        nuevaLuciernaga = luciernagas.get(i).aplicarMovimiento(luciernagas.get(j));
                        if( nuevaLuciernaga.brillaMasQue(luciernagas.get(i)) ){
                            luciernagas.set(i, nuevaLuciernaga);
                        }
                    }
                        
                }
//                nuevaLuciernaga = luciernagas.get(i).aplicarMovimiento(bestLuciernaga);
//                if( nuevaLuciernaga.brillaMasQue(luciernagas.get(i)) ){
//                    luciernagas.set(i, nuevaLuciernaga);
//                }
            }
            
            Collections.sort(luciernagas, new LuciernagaComparator());
            
            if (luciernagas.get(0).brillaMasQue( bestLuciernaga )){
                bestLuciernaga = luciernagas.get(0);
                contadorNoCambioBestLuciernaga=0;
                cambiosParaMejor++;
                Logs.importante.println("Generacion "+generacion+": "+bestLuciernaga.getFitness());
            }else{
                contadorNoCambioBestLuciernaga++;
                if(generacion%100 == 0){
//                    console.println("Generacion:"+generacion+"/"+Config.NUM_ITERACIONES);
                    fin = new Date();
                    millisPorCienGeneraciones = fin.getTime() - inicio.getTime();
                    generacionesRestantes = Config.NUM_ITERACIONES - generacion;
                    long millisRestantesEstimados = generacionesRestantes * millisPorCienGeneraciones/100;
                    String tiempoRestante = Utils.millisToTime(millisRestantesEstimados);

                    double porcentaje = (double)generacion*100/Config.NUM_ITERACIONES;
                    DecimalFormat formatter = new DecimalFormat("#0.00"); 
                    SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy - HH:mm:ss");
                    
                    console.println(
                            "["+sdf.format(fin)+"]: "
                            +"Tiempo estimado: "+tiempoRestante
                            +" --- Gen: "+generacion+"/"+Config.NUM_ITERACIONES
                            +" --- "+formatter.format(porcentaje)+"%"
                    );
                    
                    inicio = new Date();
                }
                    
                
            }
            
            
            if (contadorNoCambioBestLuciernaga >= Config.PORCENTAJE_NO_CAMBIO_PERMITIDO*Config.NUM_ITERACIONES/100){
                numeroCambiosObligatorios++;
                contadorNoCambioBestLuciernaga=0;
                bestLuciernaga = new Luciernaga(this);
                luciernagas.set(0, bestLuciernaga);
                Logs.importante.println("!---> EXPLORANDO. NUEVA BEST LUCIERNAGA: "+bestLuciernaga+"");
            }
            
//            long seconds = TimeUnit.MILLISECONDS.toSeconds(millisPorUnaGeneracion);
//            long minutes = TimeUnit.MILLISECONDS.toMinutes(millisPorUnaGeneracion);
            
//            console.println("Inicio: "+inicio.getTime());
//            console.println("Fin: "+fin.getTime());
//            console.println("Generacion Actual: "+generacion);
//            console.println("Generaciones Total: "+Config.NUM_ITERACIONES);
//            console.println("Generaciones Restantes: "+generacionesRestantes);
            
//            console.println("");
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
