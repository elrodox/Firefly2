/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.firefly.core;

import java.util.Comparator;

/**
 *
 * @author elrodox
 */
public class MiniInstancia {
//    public String resultadosInstanciaPath;
    public String resultadosEjecucionPath;
    public int fitness;

    public MiniInstancia(String resultadosEjecucionPath, int fitness) {
        this.resultadosEjecucionPath = resultadosEjecucionPath;
        this.fitness = fitness;
    }

    

   
    
    static public class ComparatorByFitness implements Comparator<MiniInstancia> {
        @Override
        public int compare(MiniInstancia i1, MiniInstancia i2) {
            return i1.fitness < i2.fitness ? -1
                   : i1.fitness == i2.fitness ? 0 : 1;
        }
    }
//    static public class ComparatorByCambios implements Comparator<AnalisisInstancia> {
//        @Override
//        public int compare(AnalisisInstancia i1, AnalisisInstancia i2) {
//            return i1.cambios > i2.cambios ? -1
//                   : i1.cambios == i2.cambios ? 0 : 1;
//        }
//    }
}
