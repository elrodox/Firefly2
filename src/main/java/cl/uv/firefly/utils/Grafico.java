/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.firefly.utils;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Grafico extends ApplicationFrame {

    private static DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    protected Grafico(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Tiempo", "Fitness",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    public static void agregarValor(double value) {
        dataset.addValue(value, "fitness", String.valueOf(System.currentTimeMillis()));
    }
    public static void agregarValor(double value, int value2) {
        dataset.addValue(value, "fitness", String.valueOf(value2));
    }

    public static void generarGrafico() {
        Grafico chart = new Grafico(
                "Fitness vs Tiempo",
                "Fitness vs Tiempo");
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }

}
