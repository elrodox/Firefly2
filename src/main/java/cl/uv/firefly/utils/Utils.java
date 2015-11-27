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
    public final static Random random = new Random(Config.seed);
}
