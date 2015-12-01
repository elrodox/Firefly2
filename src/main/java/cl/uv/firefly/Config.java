/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.firefly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;



/**
 *
 * @author elrodox
 */
public class Config {
    private static ArrayList<String> params;
    
    public static String instanciasPath="instancias/";
//    public static String logsPath="resultados/logs/";
//    public static String graficosPath="resultados/graficos/";
    public static String resultadosPath = "resultados/";
    
    public static long SEED = System.currentTimeMillis();
    public static int CANT_LUCIERNAGAS=25;
    public static int NUM_ITERACIONES=500000;
    public static int B0 = 1; 
    public static double ALFA = 0.9999999999999999; 
    public static double GAMMA = 5.4999999999999964;
    public static int PORCENTAJE_NO_CAMBIO_PERMITIDO=102;
    
    public static boolean activarLogsNormales = false;
    public static boolean activarColores = true;
    
    public static int ejecucionesPorInstancia = 20;
    
    
//    private static boolean SOLO_MOSTRAR_CONFIG = false;
    
//    private static Hashtable<String, String> presetsTable = new Hashtable();
//    static {
//        seed = System.currentTimeMillis();
////        seed = 1448793896422L;
//    }
  
    
    private static String getParamValue(String keyParamName){
        return params.get(params.indexOf(keyParamName)+1);
    }
    
    private static Object setConfigVariable(String keyParamName, Object configVariable){
        if( params.contains(keyParamName) ){
            if(configVariable instanceof Long){
                configVariable = Long.parseLong(getParamValue(keyParamName));
            }else if(configVariable instanceof Integer){
                configVariable = Integer.parseInt(getParamValue(keyParamName));
            }else if(configVariable instanceof Double){
                configVariable = Double.parseDouble(getParamValue(keyParamName));
            }else if(configVariable instanceof String){
                configVariable = getParamValue(keyParamName);
            }else if(configVariable instanceof Boolean){
                configVariable = true;
            }
            
        }
            
        return configVariable;
    }
    
    public static void leerParametros(String[] args){
        if (args.length==0){
//            String lineaParametros = "-seed 1448793896422 -luciernagas 25 -iteraciones 10 -b0 1 -alfa 0.1 -gamma 5.5 -porcentajeNoCambio 12";
//            String lineaParametros = "-colores";
//            args = lineaParametros.split(" ");
//            activarColores = true;
            return;
        };
        params = new ArrayList<String>(Arrays.asList(args));
        
//        presetsTable.put("-preset1", "-camionesPorEmpresa 46 -empresas 10 -lapsoEnvio 100ms -duracion 7s -rutas ficheroPosicionesFiltradas.txt");
//        presetsTable.put("-preset2", "-camionesPorEmpresa 46 -empresas 10 -contornos");
//        presetsTable.put("-preset3", "-camionesPorEmpresa");
//        if(params.contains("-config")){
//            SOLO_MOSTRAR_CONFIG = true;
//            return;
//        }
        
//        Enumeration<String> keys = presetsTable.keys();
//        while( keys.hasMoreElements() ){
//            String key = keys.nextElement();
//            if( params.contains(key) ){
//                args = presetsTable.get(key).split(" ");
//                params = new ArrayList<String>(Arrays.asList(args));
//            }
//            return;
//        }
        
        SEED = (long)setConfigVariable("-seed", SEED);
        CANT_LUCIERNAGAS = (int)setConfigVariable("-luciernagas", CANT_LUCIERNAGAS);
        NUM_ITERACIONES = (int)setConfigVariable("-iteraciones", NUM_ITERACIONES);
        B0 = (int)setConfigVariable("-b0", B0);
        ALFA = (double)setConfigVariable("-alfa", ALFA);
        GAMMA = (double)setConfigVariable("-gamma", GAMMA);
        PORCENTAJE_NO_CAMBIO_PERMITIDO = (int)setConfigVariable("-porcentajeNoCambio", PORCENTAJE_NO_CAMBIO_PERMITIDO);
        activarLogsNormales = (boolean)setConfigVariable("-logNormales", activarLogsNormales);
        activarColores = (boolean)setConfigVariable("-colores", activarColores);
        ejecucionesPorInstancia = (int)setConfigVariable("-ejecuciones", ejecucionesPorInstancia);
        

    }
    
    
}