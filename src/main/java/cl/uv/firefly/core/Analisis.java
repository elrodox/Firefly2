/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.firefly.core;

import cl.uv.firefly.Config;
import cl.uv.firefly.io.Input;
import cl.uv.firefly.io.Output;
import cl.uv.firefly.utils.Logs;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 *
 * @author elrodox
 */
@Deprecated
public class Analisis {
    @Deprecated
    static class MiniInstancia {
        public String resultadosPath;
        public double alfa;
        public double gamma;
        public int cambios;
        public int fitness;
@Deprecated
        public MiniInstancia(String resultadosPath, double alfa, double gamma, int cambios, int fitness) {
            this.resultadosPath = resultadosPath;
            this.alfa = alfa;
            this.gamma = gamma;
            this.cambios = cambios;
            this.fitness = fitness;
        }
        @Deprecated
        static public class ComparatorByFitness implements Comparator<MiniInstancia> {
            @Override
            public int compare(MiniInstancia i1, MiniInstancia i2) {
                return i1.fitness < i2.fitness ? -1
                       : i1.fitness == i2.fitness ? 0 : 1;
            }
        }
        @Deprecated
        static public class ComparatorByCambios implements Comparator<MiniInstancia> {
            @Override
            public int compare(MiniInstancia i1, MiniInstancia i2) {
                return i1.cambios > i2.cambios ? -1
                       : i1.cambios == i2.cambios ? 0 : 1;
            }
        }
    }
    @Deprecated
    public static void analizarParametros(String nombreArchivo){
        
        SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy - HH:mm:ss");
        String fecha;
        ArrayList<MiniInstancia> instancias = new ArrayList();
        int count=1;
        for ( double alfa=0; alfa <= 1; alfa+=0.1 ) {
            for ( double gamma=0.1; gamma <= 10; gamma+=0.1 ) {
                fecha = sdf.format(new Date());
                System.out.println(fecha);
                System.out.println(count+"/1100");
                System.out.println("alfa:  "+alfa);
                System.out.println("gamma: "+gamma);
                System.out.println("");
                
                Instancia instancia = new Instancia(nombreArchivo, count, alfa, gamma);
                //Logs.inicializarLog(instancia);
                instancia = Input.leerInstancia(instancia);
                instancia.ejecutarInstancia();
                //Logs.importante.cerrarLog();
                
                instancias.add(new MiniInstancia(
                        instancia.getResultadosEjecucionPath(),
                        instancia.ALFA,
                        instancia.GAMMA,
                        instancia.getCambiosParaMejor(),
                        instancia.getBestLuciernaga().getFitness()
                ));
                count++;
            }
        }
        Output fitnessOut = new Output("Instancias by Fitness.txt");
        Output cambiosOut = new Output("Instancias by cambios.txt");
        Collections.sort( instancias, new MiniInstancia.ComparatorByFitness() );
        for(MiniInstancia instancia: instancias){
                fitnessOut.println("================================================");
                fitnessOut.println("log: "+instancia.resultadosPath );
                fitnessOut.println("alfa "+instancia.alfa+" ; gamma "+instancia.gamma);
                fitnessOut.println("fitness: "+instancia.fitness );
                fitnessOut.println("cambios: "+instancia.cambios+"\n");
        }
        fitnessOut.close();
        
        Collections.sort( instancias, new MiniInstancia.ComparatorByCambios() );
        for(MiniInstancia instancia: instancias){
                cambiosOut.println("================================================");
                cambiosOut.println("log: "+instancia.resultadosPath );
                cambiosOut.println("alfa "+instancia.alfa+" ; gamma "+instancia.gamma);
                cambiosOut.println("fitness: "+instancia.fitness );
                cambiosOut.println("cambios: "+instancia.cambios+"\n");
        }
        cambiosOut.close();
        
    }
}
