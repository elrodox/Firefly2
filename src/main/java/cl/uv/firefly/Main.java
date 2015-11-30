/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.firefly;

import cl.uv.firefly.core.Instancia;
import cl.uv.firefly.io.Input;
import cl.uv.firefly.io.Output;
import cl.uv.firefly.utils.Logs;
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
    
    static class MiniInstancia {
        public String nombreLog;
        public double alfa;
        public double gamma;
        public int cambios;
        public int fitness;

        public MiniInstancia(String nombreArchivo, double alfa, double gamma, int cambios, int fitness) {
            this.nombreLog = nombreArchivo;
            this.alfa = alfa;
            this.gamma = gamma;
            this.cambios = cambios;
            this.fitness = fitness;
        }
        
        static public class ComparatorByFitness implements Comparator<MiniInstancia> {
            @Override
            public int compare(MiniInstancia i1, MiniInstancia i2) {
                return i1.fitness < i2.fitness ? -1
                       : i1.fitness == i2.fitness ? 0 : 1;
            }
        }
        static public class ComparatorByCambios implements Comparator<MiniInstancia> {
            @Override
            public int compare(MiniInstancia i1, MiniInstancia i2) {
                return i1.cambios > i2.cambios ? -1
                       : i1.cambios == i2.cambios ? 0 : 1;
            }
        }
    }
    
    public static void analisisDeParametros(String nombreArchivo){
        Output fitnessOut = new Output(Config.logsPath+"Instancias by Fitness.txt");
        Output cambiosOut = new Output(Config.logsPath+"Instancias by cambios.txt");
        SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy - HH:mm:ss");
        String fecha;
//        int bestFitness = 50000;
//        int maxCambios = 0;
//        Instancia bestFitnesInstancia, maxCambiosFitnessInstancia, bestInstancia;
        ArrayList<MiniInstancia> instancias = new ArrayList();
        int count=1;
        for ( double alfa=0; alfa <= 1; alfa+=0.1 ) {
            for ( double gamma=0.1; gamma <= 10; gamma+=0.1 ) {
                fecha = sdf.format(new Date());
                System.out.println(fecha);
                System.out.println(count+"/1000");
                System.out.println("alfa:  "+alfa);
                System.out.println("gamma: "+gamma);
                System.out.println("");
                
                Instancia instancia = new Instancia(nombreArchivo, alfa, gamma);
                Logs.inicializarLog(instancia);
                
//                Logs.normal.println("Leyendo instancia: "+instancia.getNombre());
                instancia = Input.leerInstancia(instancia);
//                Logs.normal.println("Se ha leido una Matriz de: "
//                        +instancia.getMatrix().length+"x"
//                        +instancia.getMatrix()[0].length);
                instancia.ejecutarInstancia();
                Logs.importante.cerrarLog();
                
                instancias.add(new MiniInstancia(
                        Logs.importante.getOutput().getPath(),
                        instancia.ALFA,
                        instancia.GAMMA,
                        instancia.getCambiosParaMejor(),
                        instancia.getBestLuciernaga().getFitness()
                ));
                count++;
//                out.println("================================================");
//                out.println("alfa "+alfa+" ; gamma "+gamma);
//                out.println("fitness: "+instancia.getBestLuciernaga().getFitness());
//                out.println("cambios: "+instancia.getCambiosParaMejor()+"\n");
//                
//                if(instancia.getBestLuciernaga().getFitness() < bestFitness){
//                    bestFitnesInstancia = instancia;
//                    bestFitness = instancia.getBestLuciernaga().getFitness();
//                }
//                if(instancia.getCambiosParaMejor() > maxCambios){
//                    maxCambiosFitnessInstancia = instancia;
//                    maxCambios = instancia.getCambiosParaMejor();
//                }
            }
        }
        Collections.sort( instancias, new MiniInstancia.ComparatorByFitness() );
        for(MiniInstancia instancia: instancias){
                fitnessOut.println("================================================");
                fitnessOut.println("log: "+instancia.nombreLog );
                fitnessOut.println("alfa "+instancia.alfa+" ; gamma "+instancia.gamma);
                fitnessOut.println("fitness: "+instancia.fitness );
                fitnessOut.println("cambios: "+instancia.cambios+"\n");
        }
        fitnessOut.close();
        
        Collections.sort( instancias, new MiniInstancia.ComparatorByCambios() );
        for(MiniInstancia instancia: instancias){
                cambiosOut.println("================================================");
                cambiosOut.println("log: "+instancia.nombreLog );
                cambiosOut.println("alfa "+instancia.alfa+" ; gamma "+instancia.gamma);
                cambiosOut.println("fitness: "+instancia.fitness );
                cambiosOut.println("cambios: "+instancia.cambios+"\n");
        }
        cambiosOut.close();
        
    }
    
    public static void todasLasInstancias(){
        File carpetaInstancias = new File(Config.instanciasPath);
        for(File archivoInstancia : carpetaInstancias.listFiles()){
            unaInstancia(archivoInstancia.getName());
        }
    }
    
   public static void unaInstancia(String nombreArchivo){
        Instancia instancia = new Instancia(nombreArchivo, Config.ALFA, Config.GAMMA);
        Logs.inicializarLog(instancia);
//        Logs.normal.println("Leyendo instancia: "+instancia.getNombre());
        instancia = Input.leerInstancia(instancia);
//        Logs.normal.println("Se ha leido una Matriz de: "
//                +instancia.getMatrix().length+"x"
//                +instancia.getMatrix()[0].length);
        instancia.ejecutarInstancia();
        Logs.importante.cerrarLog();
   }
    
    public static void main(String[] args){
        //todasLasInstancias();
        unaInstancia("scp41.txt");
        
    }
}
