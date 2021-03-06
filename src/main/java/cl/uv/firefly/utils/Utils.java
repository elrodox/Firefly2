/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.firefly.utils;

import cl.uv.firefly.Config;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author elrodox
 */
public class Utils {
    private static Random random;
    private static SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy - HH:mm:ss");
    private static SimpleDateFormat sdf_file = new SimpleDateFormat ("dd-MM-yyyy _ HH-mm-ss");
    
    public static String getStringDate(){
        return sdf.format(new Date());
    }
    public static String getStringDateForFile(){
        return sdf_file.format(new Date());
    }
//    public static Random getRandom(){
//        if(random!=null) return random;
//        else{
//            random = new Random(Config.SEED);
//            return random;
//        }
//    }
    public static String millisToTime(long millis){
        return String.format("%02d hrs %02d mins", 
            TimeUnit.MILLISECONDS.toHours(millis),
            TimeUnit.MILLISECONDS.toMinutes(millis) -  
            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)) );
        
//                                        ,TimeUnit.MILLISECONDS.toSeconds(millisRestantesEstimados) - 
//                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisRestantesEstimados))
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
