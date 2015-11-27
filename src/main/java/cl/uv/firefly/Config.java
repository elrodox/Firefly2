/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.firefly;



/**
 *
 * @author elrodox
 */
public class Config {
    public static final String instanciasPath="instancias/";
    public static final String logsPath="logs/";
    
    public static long seed;
    public static final int CANT_LUCIERNAGAS=20;
    public static final int NUM_ITERACIONES=100;
    public static final int B0 = 1; 
    public static final double GAMMA=1.0;
    public static final double ALFA=0.2;
        
    public static final int PORCENTAJE_NO_CAMBIO_PERMITIDO=3;
    public static final boolean activarLogsNormales=true;
    static {
        seed = System.currentTimeMillis();
    }
}