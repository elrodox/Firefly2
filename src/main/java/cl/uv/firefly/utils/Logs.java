/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.firefly.utils;

import cl.uv.firefly.Config;
import cl.uv.firefly.io.Output;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author elrodox
 */
public class Logs {
    
    public final static Logs normal = new Logs();
    public final static Logs importante = new Logs(true);
    
    private boolean active;

    public Logs(){
        active = Config.activarLogsNormales;
    }

    public Logs(boolean active) {
        this.active = active;
    }
    

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public void println(String text) {
        if(active){
            System.out.println(text);
            Output.println(text);
        }
    }
    public void print(String text) {
        if(active){
            System.out.print(text);
            Output.print(text);
        }
    }
    
    public void println(int text) {
        println(""+text);
    }
    public void print(int text) {
        print(""+text);
    }
    
    public static void inicializarLog(String nombreInstancia){
        SimpleDateFormat sdf = new SimpleDateFormat ("dd-MM-yyyy _ HH-mm-ss");
        String fecha = sdf.format(new Date());
        String nombreLog = "Log _ "+fecha+".txt";
        String basePathLog = Config.logsPath+nombreInstancia+"/";
        File baseFileLog = new File(basePathLog);
        if (!baseFileLog.exists()) baseFileLog.mkdirs();
        Output.open(basePathLog+nombreLog);
        Logs log = new Logs(true);
        log.println("----------------------------------------------------------");
        log.println("---- INSTANCIA '"+nombreInstancia+"' ----");
        log.println("---- CONFIGURACION INICIAL ---");
        log.println(fecha);
        log.println("");
        log.println("Semilla: "+Config.seed);
        log.println("Cantidad de luciernagas: "+Config.CANT_LUCIERNAGAS);
        log.println("Numero de iteraciones: "+Config.NUM_ITERACIONES);
        log.println("B0: "+Config.B0);
        log.println("Gamma: "+Config.GAMMA);
        log.println("Alfa: "+Config.ALFA);
        log.println("");
        log.println("Porcentaje de no cambio permitido: "+Config.PORCENTAJE_NO_CAMBIO_PERMITIDO);
        log.println("Logs normales: "+Config.activarLogsNormales);
        log.println("");
        log.println("----------------------------------------------------------");
        log.println("----------------------------------------------------------");
        log.println("");
    }
    
    public static void cerrarLog(){
        Output.close();
    }
}
