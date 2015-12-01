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
import cl.uv.firefly.io.Output;
import cl.uv.firefly.utils.Grafico;
import cl.uv.firefly.utils.Logs;
import cl.uv.firefly.utils.Utils;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Instancia {
   
    public double GAMMA; // [0.1,10]
    public double ALFA;  // [0,1]
    
//    private String nombreLog;
    
    private Logs logPrincipal;
    private String nombreInputArchivo;
    private String nombre;
    private String inputPath;
    private int cambiosParaMejor=0;
    
    private Grafico grafico;
    private String resultadosInstanciaPath;
    
    private String id;
    private String resultadosEjecucionPath;
    private int numeroEjecucion;
    
            
    private int[][] matrix = null;
    private int cantRestricciones, cantCostos;
    private ArrayList<Integer> vectorCostos = new ArrayList<Integer>(); //vector de costos
    private ArrayList<Luciernaga> luciernagas = new ArrayList();
    private Luciernaga bestLuciernaga;
    

//    private Logs logNormal = Logs.normal;
    //private Logs logImportante = Logs.importante;
    private Logs console;
    
    

    public Instancia(String nombreArchivo, int numeroEjecucion, double alfa, double gamma) {
        this.id = Utils.getStringDateForFile();
        this.nombreInputArchivo = nombreArchivo;
        this.inputPath = Config.instanciasPath+nombreArchivo;
        this.nombre = nombreArchivo.substring(0, nombreArchivo.lastIndexOf("."));
        this.GAMMA = gamma;
        this.ALFA = alfa;
        this.numeroEjecucion = numeroEjecucion;
        
        resultadosInstanciaPath = Config.resultadosPath+nombre+"/";
        resultadosEjecucionPath = resultadosInstanciaPath+numeroEjecucion+") "+id+"/";
        File resultados = new File(resultadosEjecucionPath);
        if(!resultados.exists()) resultados.mkdirs();
        
        this.grafico = new Grafico(resultadosEjecucionPath+"Grafico _ "+this.id+".png", nombre, numeroEjecucion);
        
        String nombreLog = "Log _ "+this.id+".txt";
        Output out = new Output(resultadosEjecucionPath+nombreLog);
        logPrincipal = new Logs(out, false);
        console = new Logs(false);
        imprimirConfiguracionInstancia();
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
        logPrincipal.println("Generacion 0: "+bestLuciernaga.getFitness());
        grafico.agregarValor(bestLuciernaga.getFitness(), 0);
        Luciernaga nuevaLuciernaga;
        
        int contadorNoCambioBestLuciernaga=0, numeroCambiosObligatorios=0;
        Date inicio, fin;
        long millisPorXGeneraciones;
        int generacionesRestantes, xGeneraciones=1;
        inicio = new Date();
        
        for(int generacion=1; generacion <= Config.NUM_ITERACIONES; generacion++){
            
//            for (int i = 0; i < luciernagas.size(); i++) { 
//                for (int j = 0; j < luciernagas.size(); j++) {
//                    if( luciernagas.get(j).brillaMasQue(luciernagas.get(i)) ){
//                        nuevaLuciernaga = luciernagas.get(i).aplicarMovimiento(luciernagas.get(j));
//                        if( nuevaLuciernaga.brillaMasQue(luciernagas.get(i)) ){
//                            luciernagas.set(i, nuevaLuciernaga);
//                        }
//                    }
//                        
//                }
//            }
            
            for (int i = 1; i < luciernagas.size(); i++) { 
                nuevaLuciernaga = luciernagas.get(i).aplicarMovimiento(bestLuciernaga);
                if( nuevaLuciernaga.brillaMasQue(luciernagas.get(i)) ){
                    luciernagas.set(i, nuevaLuciernaga);
                }
            }
            
            Collections.sort(luciernagas, new LuciernagaComparator());
            
            if (luciernagas.get(0).brillaMasQue( bestLuciernaga )){
                bestLuciernaga = luciernagas.get(0);
                contadorNoCambioBestLuciernaga=0;
                cambiosParaMejor++;
                logPrincipal.println("Generacion "+generacion+": "+bestLuciernaga.getFitness());
                grafico.agregarValor(bestLuciernaga.getFitness(), generacion);
                
//                fin = new Date();
//                millisPorXGeneraciones = fin.getTime() - inicio.getTime();
//                generacionesRestantes = Config.NUM_ITERACIONES - generacion;
//                long millisRestantesEstimados = generacionesRestantes * millisPorXGeneraciones/xGeneraciones;
//                String tiempoRestante = Utils.millisToTime(millisRestantesEstimados);
//                double porcentaje = (double)generacion*100/Config.NUM_ITERACIONES;
//                DecimalFormat formatter = new DecimalFormat("#0.00"); 
//                SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy - HH:mm:ss");
//                console.println(
//                        "["+sdf.format(fin)+"]: "
//                        +"Tiempo estimado: "+tiempoRestante
//                        +" --- Gen: "+generacion+"/"+Config.NUM_ITERACIONES
//                        +" --- "+formatter.format(porcentaje)+"%"
//                );
//                xGeneraciones=0;
//                inicio = new Date();
                
            }else{
//                if(generacion%100==0){
//                    
//                }
                
                
                contadorNoCambioBestLuciernaga++;
            }
            
            
            if (contadorNoCambioBestLuciernaga >= Config.PORCENTAJE_NO_CAMBIO_PERMITIDO*Config.NUM_ITERACIONES/100){
                numeroCambiosObligatorios++;
                contadorNoCambioBestLuciernaga=0;
                bestLuciernaga = new Luciernaga(this);
                luciernagas.set(0, bestLuciernaga);
                logPrincipal.println("!---> EXPLORANDO. NUEVA BEST LUCIERNAGA: "+bestLuciernaga+"");
            }
            
//            long seconds = TimeUnit.MILLISECONDS.toSeconds(millisPorUnaGeneracion);
//            long minutes = TimeUnit.MILLISECONDS.toMinutes(millisPorUnaGeneracion);
            
//            console.println("Inicio: "+inicio.getTime());
//            console.println("Fin: "+fin.getTime());
//            console.println("Generacion Actual: "+generacion);
//            console.println("Generaciones Total: "+Config.NUM_ITERACIONES);
//            console.println("Generaciones Restantes: "+generacionesRestantes);
            
//            console.println("");
            
            xGeneraciones++;
        }
        
        logPrincipal.println("\n----------------------------------------------------------");
        logPrincipal.println("\nMejor Solucion: "+bestLuciernaga.getFitness() );
        logPrincipal.println("Cambios obligatorios: "+ numeroCambiosObligatorios+"/"+Config.NUM_ITERACIONES);
        logPrincipal.println("Cambios para mejor: "+ cambiosParaMejor+"/"+Config.NUM_ITERACIONES);
        logPrincipal.println("----------------------------------------------------------");
        
        logPrincipal.cerrarLog();
        grafico.saveImage();
        
    }
    
    private void imprimirConfiguracionInstancia(){
        logPrincipal.println("----------------------------------------------------------");
        logPrincipal.println("------------"+this.id+"------------");
        logPrincipal.println("---- INSTANCIA '"+this.nombre+"' ----");
        logPrincipal.println("---- CONFIGURACION INICIAL ---");
        logPrincipal.println("");
        logPrincipal.println("Semilla: "+Config.SEED);
        logPrincipal.println("Cantidad de luciernagas: "+Config.CANT_LUCIERNAGAS);
        logPrincipal.println("Numero de iteraciones: "+Config.NUM_ITERACIONES);
        logPrincipal.println("B0: "+Config.B0);
        logPrincipal.println("Gamma: "+this.GAMMA);
        logPrincipal.println("Alfa: "+this.ALFA);
        logPrincipal.println("");
        logPrincipal.println("Porcentaje de no cambio permitido: "+Config.PORCENTAJE_NO_CAMBIO_PERMITIDO);
        logPrincipal.println("Logs normales: "+Config.activarLogsNormales);
        logPrincipal.println("");
        logPrincipal.println("----------------------------------------------------------");
        logPrincipal.println("----------------------------------------------------------");
        logPrincipal.println("");
    }
 
    
    ///....

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    
    

//    public String getNombreLog() {
//        return nombreLog;
//    }
//
//    public void setNombreLog(String nombreLog) {
//        this.nombreLog = nombreLog;
//    }

    public String getResultadosEjecucionPath() {
        return resultadosEjecucionPath;
    }

    public String getInputPath() {
        return inputPath;
    }

    public String getResultadosInstanciaPath() {
        return resultadosInstanciaPath;
    }

    

    
    
    
    
    
}
