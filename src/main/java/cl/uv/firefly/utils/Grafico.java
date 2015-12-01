/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.firefly.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
//import org.jfree.ui.ApplicationFrame;
//import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Grafico {
    private final String imagePath;
    private int valuesCount = 1;
    private String titulo;
    private final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    public Grafico(String imagePath, String nombreInstancia, int numeroEjecucion) {
        this.imagePath = imagePath;
        titulo = nombreInstancia+" - Ejec #"+numeroEjecucion;
    }
    //this.grafico = new Grafico(Config.graficosPath+this.id, nombre, numeroEjecucion);
//       
//    public void agregarValor(double value) {
//        dataset.addValue(value, "fitness", String.valueOf(valuesCount++));
//    }
    public void agregarValor(double value, int value2) {
        dataset.addValue(value, "fitness", String.valueOf(value2));
    }
    public void saveImage(){
        try {
            JFreeChart lineChart = ChartFactory.createLineChart(
                    titulo,
                    "Generacion", "Fitness",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true, true, false); 
            ChartUtilities.saveChartAsPNG(new java.io.File(imagePath), lineChart, 560, 367);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(Grafico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
//    protected GraficoJFreeChart(String applicationTitle, String chartTitle) {
//        //super(applicationTitle);
//        JFreeChart lineChart = ChartFactory.createLineChart(
//                chartTitle,
//                "Tiempo", "Fitness",
//                dataset,
//                PlotOrientation.VERTICAL,
//                true, true, false);
//
//        ChartPanel chartPanel = new ChartPanel(lineChart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
//        //setContentPane(chartPanel);
//    }



//    public static void generarGrafico() {
//        GraficoJFreeChart chart = new GraficoJFreeChart(
//                "Fitness vs Tiempo",
//                "Fitness vs Tiempo");
//        //chart.pack();
//        //RefineryUtilities.centerFrameOnScreen(chart);
//        //chart.setVisible(true);
//    }

}
