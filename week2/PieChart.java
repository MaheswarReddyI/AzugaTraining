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
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;

/**
 * This class gives data visualisation as a pie chart from data fetched from FakestoreApi
 */

public class PieChart extends JFrame {
    private static final long serialVersionUID = 6294689542092367723L;
    private static final Logger logger = LogManager.getLogger(PieChart.class);

    public PieChart(String pieChartTitle) {
        super(pieChartTitle);
        logger.info("Constructor of Pie Chart is loaded");

        // Create piedata
        PieDataset piedata = createDataset();

        // Create chart
        JFreeChart chart = ChartFactory.createPieChart(
                "Types of items in store",
                piedata,
                true,
                true,
                false);
        logger.trace("Title, Pie Data, legend and tooltips are added to  pie Chart");

        //Format Label
        PieSectionLabelGenerator sectionLabelGenerator = new StandardPieSectionLabelGenerator(
                " {0} : ({2})", new DecimalFormat("0"), new DecimalFormat("0%"));
        ((PiePlot) chart.getPlot()).setLabelGenerator(sectionLabelGenerator);

        // Create Panel
        ChartPanel chartPanel = new ChartPanel(chart);
        setContentPane(chartPanel);
    }

    private PieDataset createDataset() {

        logger.info("CreateDataset method is invoked");
        List<String> category = new ArrayList<>();
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
                if ((s.equals("category"))) {
                    category.add((String) jo.get(s));
                }
            }
        }
        HashMap<String, Integer> languageMap = new HashMap<>();
        for (String s : category) {
            if (languageMap.containsKey(s)) {
                int c = languageMap.get(s);
                languageMap.replace(s, c + 1);
            } else {
                languageMap.put(s, 1);
            }
        }
        logger.debug("Category of items  " + languageMap);
        DefaultPieDataset dataset = new DefaultPieDataset();
        Iterator iterator = languageMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) iterator.next();
            dataset.setValue((Comparable) mapElement.getKey(), (Number) mapElement.getValue());
        }

        logger.info("Items are added to Dataset");
        return dataset;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PieChart example;
            example = new PieChart("Pie Chart");
            example.setSize(700, 400);
            logger.warn("Size of pie chart is set");
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
            logger.info("Pie Chart is Created for Items category");
        });
    }
}