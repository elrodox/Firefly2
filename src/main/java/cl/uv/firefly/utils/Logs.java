/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.firefly.utils;

import cl.uv.firefly.Config;
import cl.uv.firefly.core.Instancia;
import cl.uv.firefly.io.Output;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author elrodox
 */
public class Logs {
    
    public static Logs normal;
    public static Logs importante;
    private String lastLine;
    private boolean active;
    private Output output;

//    public Logs(String outputPath){
//        output.open(outputPath);
//        active = Config.activarLogsNormales;
//    }
//    public Logs(String outputPath, boolean active) {
//        output.open(outputPath);
//        this.active = active;
//    }
    
    
    public Logs(Output out){
        output = out;
        active = Config.activarLogsNormales;
    }
    public Logs(Output out, boolean active) {
        output = out;
        this.active = active;
    }

     public Logs(boolean active) {
        output = null;
        this.active = active;
    }
    

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public void println(String text) {
        print(text+"\n");
//        if(active){
//            System.out.println(text);
//            output.println(text);
//        }
    }
//    public void infoColorLn(String text){
//        text =(char)27 + "[36m" + text;
//        print(text+"\n");
//    }
    //private void setImportantColor(String text)
    public void print(String text) {
        if(active){
            lastLine = text;
            if(output!=null) output.print(text);
            if(Config.activarColores){
                if(!this.equals(importante))
                    text =(char)27 + "[37;40m" + text;
                else
                    text =(char)27 + "[30m" + text;
            }
            System.out.print(text);
        }
    }
    
//    public void eraseLastLine(){
//        String eraseLine="";
//        for (int i = 0; i < lastLine.length(); i++) {
//            eraseLine += "\b";
//        }
//        System.out.println(eraseLine);
//    }
    
    public void println(int text) {
        println(""+text);
    }
    public void print(int text) {
        print(""+text);
    }

    public Output getOutput() {
        return output;
    }
    
    
    
    public static void inicializarLog(Instancia instancia){
        
        SimpleDateFormat sdf = new SimpleDateFormat ("dd-MM-yyyy _ HH-mm-ss");
        String fecha = sdf.format(new Date());
        String nombreLog = "Log _ "+fecha+".txt";
        String basePathLog = Config.logsPath+instancia.getNombre()+"/";
        File baseFileLog = new File(basePathLog);
        if (!baseFileLog.exists()) baseFileLog.mkdirs();
        
        
        Output out = new Output(basePathLog+nombreLog);
        normal = new Logs(out);
        importante = new Logs(out, true);
        Logs out2 = new Logs(out, true);
        
        out2.println("----------------------------------------------------------");
        out2.println("---- INSTANCIA '"+instancia.getNombre()+"' ----");
        out2.println("---- CONFIGURACION INICIAL ---");
        out2.println(fecha);
        out2.println("");
        out2.println("Semilla: "+Config.SEED);
        out2.println("Cantidad de luciernagas: "+Config.CANT_LUCIERNAGAS);
        out2.println("Numero de iteraciones: "+Config.NUM_ITERACIONES);
        out2.println("B0: "+Config.B0);
        out2.println("Gamma: "+instancia.GAMMA);
        out2.println("Alfa: "+instancia.ALFA);
        out2.println("");
        out2.println("Porcentaje de no cambio permitido: "+Config.PORCENTAJE_NO_CAMBIO_PERMITIDO);
        out2.println("Logs normales: "+Config.activarLogsNormales);
        out2.println("");
        out2.println("----------------------------------------------------------");
        out2.println("----------------------------------------------------------");
        out2.println("");
    }
    
    public void cerrarLog(){
        output.close();
    }
}
