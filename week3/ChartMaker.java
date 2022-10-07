/*
 * Copyright (c) 2022.  - All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - indukurimr(Indukuri Maheswar Reddy).
 */

package week3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;


/**
 * It creates visual representation of csv Data in the form of charts.
 */

public class ChartMaker extends ApplicationFrame implements Charitable {
    private static final Logger logger = LogManager.getLogger(ChartMaker.class);


    /**
     * It Creates Bar Chart with the given csv data
     */
    @Override
    public void getBarChart() {
        logger.info("getBarChart method is invoked");
        ChartMaker chart = new ChartMaker("Bar Chart", "Price of Items");
        chart.pack();
        //RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible(true);
        logger.info("Bar Chart with Price vs title is created");
    }

    ChartMaker(String applicationTitle, String chartTitle) {
        super(applicationTitle);
        logger.info("Constructor of chartMaker is loaded");
        JFreeChart barChart;
        barChart = ChartFactory.createBarChart(
                chartTitle,
                "Items",
                "Price",
                createDatasetbar(),
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
    private CategoryDataset createDatasetbar() {
        logger.info("CreateDataset method in BarChart is invoked");
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Float> price = new ArrayList<>();
        List<String> title = new ArrayList<>();
        ChartMaker obj2 = new ChartMaker();
        obj2.getJson();
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

    /**
     * It Creates Line Chart with the given csv data
     */
    public void getLineChart() {
        ChartMaker chart = new ChartMaker("Line Chart for FakestoreAPI",
                "Price vs Object ID", "items");
        chart.pack();
        // RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible(true);
        logger.info("Line Chart is Created for Price vs object ID");
    }


    /**
     * This method Takes input of data fetched from API and returns Dataset object
     *
     * @return It returns Dataset object
     */
    DefaultCategoryDataset createDatasetLine() {
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

    ChartMaker(String applicationTitle, String chartTitle, String ignoredItems) {
        super(applicationTitle);
        logger.info("Constructor of lineChart is loaded");
        JFreeChart lineChart;
        lineChart = ChartFactory.createLineChart(
                chartTitle,
                "Object Ids", "Price of items",
                createDatasetLine(),
                PlotOrientation.VERTICAL,
                true, true, false);
        logger.trace("Axis label, data, legend, tooltips are added to the  lineChart");
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        logger.warn("Size of set is fixed for lineChart");
        setContentPane(chartPanel);
    }


    /**
     * It Creates Pie Chart with the given csv data
     */
    public void getPieChart() {
        ChartMaker example = new ChartMaker("Pie Chart");
        SwingUtilities.invokeLater(() -> {
            example.setSize(700, 400);
            logger.warn("Size of pie chart is set");
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
            logger.info("Pie Chart is Created for Items category");
            File f = new File("./Files/fakestore.json");
            f.delete();
            logger.info("Temporary file created for program got deleted successfully");
        });
    }

    ChartMaker(String pieChartTitle) {
        super(pieChartTitle);
        logger.info("Constructor of Pie Chart is loaded");

        // Create piedata
        PieDataset piedata = createdatasetpie();

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


    /**
     * This method Takes input of data fetched from API and returns Dataset object
     *
     * @return It returns Dataset object
     */
    private PieDataset createdatasetpie() {

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

    ChartMaker() {
        super("");

    }


    /**
     * This method creates json file with the given url
     */
    public void getJson() {
        var url = "https://fakestoreapi.com/products";
        var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        var client = HttpClient.newBuilder().build();
        HttpResponse<String> response = null;
        try {
            logger.info("sending GET request to https://fakestoreapi.com/products");
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            logger.error("URL https://fakestoreapi.com/products is not found", e);
        } catch (InterruptedException e) {
            logger.error("Bad response from https://fakestoreapi.com/products", e);
        }
        if (response != null) {
            if (response.statusCode() == 200) {
                logger.info("response code 200 to api call https://fakestoreapi.com/products");
                String str = response.body();
                logger.debug("Response : " + str);
                JSONArray arr = new JSONArray(str);
                StringBuilder result = new StringBuilder("[{");
                int c = 0;
                int d = 0;

                //Iterating through every JSONObject in the JSONArray
                for (Object obj : arr) {

                    if (c == 0) {
                        logger.trace("FakestoreAPI is flattening");
                    }
                    StringBuilder res = new StringBuilder();
                    c++;
                    JSONObject jo = (JSONObject) obj;
                    Iterator<?> keys;
                    keys = jo.keys();

                    //Iterating through every key-value pair in JSONObject
                    while (keys.hasNext()) {
                        String s = (String) keys.next();
                        if (jo.get(s) instanceof JSONObject) {
                            JSONObject job = (JSONObject) jo.get(s);
                            Iterator<?> keys1;
                            keys1 = job.keys();

                            //Iterating through the JSONObject which is the value of one of the keys of above JSONObject
                            while (keys1.hasNext()) {
                                d++;
                                String s1 = (String) keys1.next();
                                res.append("\"").append(s).append("_").append(s1).append("\"").append(":").append("\"").append(job.get(s1)).append("\"");
                                if (keys1.hasNext()) {
                                    res.append(",");
                                } else {
                                    if (keys.hasNext()) {
                                        res.append(",");
                                    }
                                }
                            }
                        } else {
                            res.append("\"").append(s).append("\"").append(":").append("\"").append(jo.get(s)).append("\"");
                            if (keys.hasNext()) {
                                res.append(",");
                            } else {
                                res.append("}");
                            }
                        }
                    }
                    if (c != arr.length()) {
                        res.append(",{");
                    }
                    if (c == 15) {
                        logger.warn("Processing Large Data, program might get slow");
                    }
                    result.append(res);
                    logger.debug("Object " + c + " {" + res);
                    if (c == arr.length()) {
                        logger.info("Flattening is Done");
                    }
                }
                logger.info((d / c) + " columns are flattened");
                result.append("]");
                String sb2 = new String(result);
                File f = new File("./Files");
                if (!f.exists()) {
                    f.mkdir();
                }

                //It creates JSON file and data will be filled into it
                try (FileWriter fw = new FileWriter("./Files/fakestore.json")) {
                    fw.write(sb2);
                    logger.info("Data Filled into ./Files/fakestore.json");
                } catch (IOException e) {
                    logger.error("Unable to write into ./Files/fakestore.json", e);
                }

            } else {
                logger.error("response code " + response.statusCode() + " to api call https://fakestoreapi.com/products");
            }
        }
    }
}
