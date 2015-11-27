/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.firefly;

import cl.uv.firefly.core.Instancia;
import cl.uv.firefly.io.Input;
import cl.uv.firefly.utils.Logs;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author elrodox
 */
public class Main {
    
    public static void unaInstancia(String nombreArchivo){
        Instancia instancia = new Instancia(nombreArchivo);
        Logs.inicializarLog(instancia.getNombre());
        Logs.normal.println("Leyendo instancia: "+instancia.getNombre());
        instancia = Input.leerInstancia(instancia);
        Logs.normal.println("Se ha leido una Matriz de: "
                +instancia.getMatrix().length+"x"
                +instancia.getMatrix()[0].length);
        instancia.ejecutarInstancia();
        Logs.cerrarLog();
    }
    
    public static void todasLasInstancias(){
        File carpetaInstancias = new File(Config.instanciasPath);
        for(File archivoInstancia : carpetaInstancias.listFiles()){
            unaInstancia(archivoInstancia.getName());
        }
    }
    
    public static void main(String[] args){
        //todasLasInstancias();
        unaInstancia("test.txt");
        
    }
}
