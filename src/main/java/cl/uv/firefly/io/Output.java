/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.firefly.io;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class Output{
    private static FileWriter fichero = null;
    private static PrintWriter pw = null;
    public static void open(String path){
        try {
            fichero = new FileWriter(new File(path));
            pw = new PrintWriter(fichero);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void close(){
        try {
            if (null != fichero) fichero.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void print(String linea) {
        try {
            pw.print(linea);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void println(String linea) {
        print(linea+"\n\r");
    }
}