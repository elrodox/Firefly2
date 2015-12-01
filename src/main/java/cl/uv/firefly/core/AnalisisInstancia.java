/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.firefly.core;

import java.util.ArrayList;

/**
 *
 * @author elrodox
 */
public class AnalisisInstancia {
    private String nombreInstancia;
    private int minFitness;
    private int maxFitness;
    private int avgFitness;
//    private ArrayList<MiniInstancia> instancias;

    public AnalisisInstancia(String nombreInstancia, int minFitness, int maxFitness, int avgFitness) {
        this.nombreInstancia = nombreInstancia;
        this.minFitness = minFitness;
        this.maxFitness = maxFitness;
        this.avgFitness = avgFitness;
    }

    public String getNombreInstancia() {
        return nombreInstancia;
    }


    public int getMinFitness() {
        return minFitness;
    }

    public int getMaxFitness() {
        return maxFitness;
    }

    public int getAvgFitness() {
        return avgFitness;
    }

    
    
    
}
