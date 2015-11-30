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
    public static final int CANT_LUCIERNAGAS=25;
    public static final int NUM_ITERACIONES=300000;
    public static int B0 = 1; 
    public static double ALFA = 0.9999999999999999; 
    public static double GAMMA = 5.4999999999999964; 

        
    public static final int PORCENTAJE_NO_CAMBIO_PERMITIDO=101;
    public static final boolean activarLogsNormales=false;
    static {
        //seed = System.currentTimeMillis();
        seed = 1448793896422L;
    }
}