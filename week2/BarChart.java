/*
 * copyright (c) 2022.   -  All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - Indukuri Maheswar Reddy ( indukurimr )
 * @version - java 11
 */


package week2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Barchart class produces Bar Chart for the data fetched from fakestoreAPI i.e., Object id VS price
 */


public class BarChart extends ApplicationFrame {

    private static final Logger logger = LogManager.getLogger(BarChart.class);

    public BarChart(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        logger.info("Constructor of BarChart is loaded");
        JFreeChart barChart;
        barChart = ChartFactory.createBarChart(
                chartTitle,
                "Items",
                "Price",
                createDataset(),
                PlotOrientation.HORIZONTAL,
                true, true, false);
        logger.trace("Axis label, data, Plot Orientation, legend, tooltips are added to the Bar Chart");

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1400, 800));
        logger.warn("Size of Bar Chart is set");
        setContentPane(chartPanel);
    }

    /**
     * This method Takes input of data fetched from API and returns Dataset object
     *
     * @return It returns Dataset object
     */
    private CategoryDataset createDataset() {
        logger.info("CreateDataset method in BarChart is invoked");
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Float> price = new ArrayList<>();
        List<String> title = new ArrayList<>();
        String loc = "./Files/fakestore.json";
        String result = null;
        try {
            result = new String(Files.readAllBytes(Paths.get(loc)));
            logger.info("Reading data from" + loc);
        } catch (IOException e) {
            logger.error("Error in reading ./Files/fakestore.json", e);
        }
        if (result != null) {
            JSONArray arr = new JSONArray(result);
            for (Object obj : arr) {
                JSONObject jo = (JSONObject) obj;
                Iterator<?> keys;
                keys = jo.keys();
                while (keys.hasNext()) {
                    String s = (String) keys.next();
                    if (s.equals("price")) {
                        price.add(Float.parseFloat((String) jo.get(s)));
                    } else if (s.equals("title")) {
                        title.add(((String) jo.get(s)));
                    }
                }
            }
        }
        logger.debug("Price Data : " + price);
        logger.debug("Title Data : " + title);

        for (int i = 0; i < 10; i++) {
            dataset.addValue(price.get(i), title.get(i), title.get(i));
        }
        logger.info("Values are added to Dataset");
        return dataset;
    }

    public static void main(String[] args) {
        BarChart chart = new BarChart("Bar Chart", "Price of Items");
        chart.pack();
        //RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible(true);
        logger.info("Bar Chart with Price vs title is created");
    }
}
