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
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * This Class Visualizes the Data fetched from API as Line Chart
 */

public class LineChart extends ApplicationFrame {

    private static final Logger logger = LogManager.getLogger(LineChart.class);

    public LineChart(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        logger.info("Constructor of lineChart is loaded");
        JFreeChart lineChart;
        lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Object Ids", "Price of items",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);
        logger.trace("Axis label, data, legend, tooltips are added to the  lineChart");
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        logger.warn("Size of set is fixed for lineChart");
        setContentPane(chartPanel);
    }

    private DefaultCategoryDataset createDataset() {
        logger.info("CreateDataset method is invoked");
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Float> price = new ArrayList<>();
        List<Float> objectId = new ArrayList<>();
        String loc = "./Files/fakestore.json";
        String result = null;
        try {
            result = new String(Files.readAllBytes(Paths.get(loc)));
            logger.info("Reading data from" + loc);
        } catch (IOException e) {
            logger.error("Error in reading ./Files/fakestore.json", e);
        }
        JSONArray arr = new JSONArray(Objects.requireNonNull(result));
        for (Object obj : arr) {
            JSONObject jo = (JSONObject) obj;
            Iterator<?> keys;
            keys = jo.keys();
            while (keys.hasNext()) {
                String s = (String) keys.next();
                if (s.equals("price")) {
                    price.add(Float.parseFloat((String) jo.get(s)));
                } else if (s.equals("id")) {
                    objectId.add(Float.parseFloat((String) jo.get(s)));
                }
            }
        }
        logger.debug("Data of Price  " + price);
        logger.debug("Data of objectID  " + objectId);
        for (int i = 0; i < 10; i++) {
            dataset.addValue(price.get(i), "Price", "" + (i + 1));
        }
        logger.info("Values of Price and objectID are added to Dataset");
        return dataset;
    }

    public static void main(String[] args) {
        LineChart chart = new LineChart(
                "Line Chart for FakestoreAPI",
                "Price vs Object ID");

        chart.pack();
        chart.setVisible(true);
        logger.info("Line Chart is Created for Price vs object ID");
    }
}
