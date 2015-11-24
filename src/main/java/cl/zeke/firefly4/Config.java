/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.zeke.firefly4;



/**
 *
 * @author elrodox
 */
public class Config {
    public static final int CANT_LUCIERNAGAS=25;
    public static final int NUM_ITERACIONES=10000;
    public static final int B0 = 2; 
    public static final double GAMMA=0.05;
    public static final double ALFA=0.02;
    public static long seed;
    public static final boolean activeLog=false;
    static {
        seed = System.currentTimeMillis();
    }
}
