/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.firefly.utils;

import cl.uv.firefly.Config;
import java.util.Random;

/**
 *
 * @author elrodox
 */
public class Utils {
    private static Random random;
    
    public static Random getRandom(){
        if(random!=null) return random;
        else{
            random = new Random(Config.seed);
            return random;
        }
    }
    
//    public static String arrayToString(int[] array){
//        String str ="";
//        for(int i:array){
//            str+=i+" ";
//        }
//        return str;
//    }
//    public static void printArray(int[] array){
//        Logs.normal.print(arrayToString(array));
//    }
}
