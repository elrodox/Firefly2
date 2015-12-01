/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.firefly;

import cl.uv.firefly.core.AnalisisInstancia;
import cl.uv.firefly.core.MiniInstancia;
import cl.uv.firefly.core.Instancia;
import cl.uv.firefly.io.Input;
import cl.uv.firefly.io.Output;
import cl.uv.firefly.utils.Logs;
import cl.uv.firefly.utils.Utils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author elrodox
 */
public class Main {
    
    public static void main(String[] args){
//        String lineaParametros = "-seed 1448793896422 -luciernagas 25 -iteraciones 10 -b0 1 -alfa 0.1 -gamma 5.5 -porcentajeNoCambio 12";
        String lineaParametros = "-iteraciones 100 -ejecuciones 5";
        args = lineaParametros.split(" ");
        Config.leerParametros(args);
        
        //unaInstanciaUnaVez("scp41.txt");
        //unaInstanciaVariasVeces("scp41.txt");
        System.out.println("Inicio: "+Utils.getStringDate());
        todasLasInstanciasVariasVeces();
    }

    
    public static void todasLasInstanciasUnaVez(){
        File carpetaInstancias = new File(Config.instanciasPath);
        for(File archivoInstancia : carpetaInstancias.listFiles()){
            unaInstanciaUnaVez(archivoInstancia.getName());
        }
    }
    
   public static void unaInstanciaUnaVez(String nombreArchivo){
        Instancia instancia = new Instancia(nombreArchivo, 1, Config.ALFA, Config.GAMMA);
//        Logs.inicializarLog(instancia);
//        Logs.normal.println("Leyendo instancia: "+instancia.getNombre());
        instancia = Input.leerInstancia(instancia);
//        Logs.normal.println("Se ha leido una Matriz de: "
//                +instancia.getMatrix().length+"x"
//                +instancia.getMatrix()[0].length);
        instancia.ejecutarInstancia();
//        Logs.importante.cerrarLog();
   }
    
   
    public static void todasLasInstanciasVariasVeces(){
        
        Output out = new Output(Config.resultadosPath+"/analisis general.txt");
        Logs log = new Logs(out, true);
        Logs console = new Logs(true);
        log.println("Instancia \t Min \t\t Max \t\t Avg");
//        for(MiniInstancia instancia: instancias){
//            analisisGeneralOut.println("================================================");
//            analisisGeneralOut.println("fitness: "+instancia.fitness );
//            analisisGeneralOut.println("results path: "+instancia.resultadosEjecucionPath );
//        }
        
        
        File carpetaInstancias = new File(Config.instanciasPath);
        File[] archivosInputInstancias = carpetaInstancias.listFiles();
        AnalisisInstancia analisis;
        for (int i = 0; i < archivosInputInstancias.length; i++) {
            analisis = unaInstanciaVariasVeces(archivosInputInstancias[i].getName());
            log.print(
                    analisis.getNombreInstancia()+"\t\t"
                    +analisis.getMinFitness()+"\t\t"
                    +analisis.getMaxFitness()+"\t\t"
                    +analisis.getAvgFitness()
            );
            console.print( "\t" + ( (i+1)*100/archivosInputInstancias.length ) + "%");
            log.println("");
        }
        log.cerrarLog();
    }
    
    public static AnalisisInstancia unaInstanciaVariasVeces(String nombreArchivo){
        ArrayList<MiniInstancia> instancias = new ArrayList();
        
        String nombreInstancia="";
        int avg=0;
        for ( int ejecucion=1; ejecucion <= Config.ejecucionesPorInstancia; ejecucion++ ) {
            Instancia instancia = new Instancia(nombreArchivo, ejecucion, Config.ALFA, Config.GAMMA);
            nombreInstancia = instancia.getNombre();
            instancia = Input.leerInstancia(instancia);
            instancia.ejecutarInstancia();

            instancias.add(new MiniInstancia(
                instancia.getResultadosEjecucionPath(),
                instancia.getBestLuciernaga().getFitness()
            ));
            avg += instancia.getBestLuciernaga().getFitness();
        }
        
        // ANALISIS
        Collections.sort(instancias, new MiniInstancia.ComparatorByFitness() );
        avg /= instancias.size();
        int min = instancias.get(0).fitness,
            max = instancias.get(instancias.size()-1).fitness;
                
        Output analisisInstanciaOut = new Output(Config.resultadosPath+nombreInstancia+"/analisis de instancia en general.txt");
        analisisInstanciaOut.println(nombreInstancia+", ejecutada "+instancias.size()+" veces");
        analisisInstanciaOut.println("min: "+min+"\nmax: "+max+"\navg: "+avg);
        analisisInstanciaOut.println("Resultados de mejor a peor segun fitness:");
        for(MiniInstancia instancia: instancias){
            analisisInstanciaOut.println("=========================");
            analisisInstanciaOut.println("fitness: "+instancia.fitness );
            analisisInstanciaOut.println("results path: "+instancia.resultadosEjecucionPath );
        }
        analisisInstanciaOut.close();
        
        
        return new AnalisisInstancia(nombreInstancia, min, max, avg);
    }
    
}
