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
public class Logs {
    private static boolean active = Config.activeLog;

    public static void setActive(boolean active) {
        Logs.active = active;
    }
    
    static void println(String text) {
        if(active) System.out.println(text);
    }
    static void print(String text) {
        if(active) System.out.print(text);
    }
    
    static void println(int text) {
        println(""+text);
    }
    static void print(int text) {
        print(""+text);
    }
    
}
