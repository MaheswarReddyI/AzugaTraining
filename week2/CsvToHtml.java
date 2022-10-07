/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -IndukuriMr(Indukuri Maheswar Reddy).
 */

package week2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class converts csv file of fakestore API into html file
 */

public class CsvToHtml {

    private static final Logger logger = LogManager.getLogger(CsvToHtml.class);

    public static void main() {


        String csvFile = "./Files/fakestore.csv";
        String outputFile = "./Files/CsvToHtml.html";

        // read lines of csv to a string array list
        File file = new File(csvFile);
        if (file.exists() && file.length() != 0) {
            ConvertToHtml(csvFile, outputFile);
        } else {
            logger.error("Provide Valid csv file");
        }
    }

    private static void ConvertToHtml(String csvFile, String outputFile) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            logger.info("Reading data from" + csvFile);
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String s3 = currentLine.replaceAll("\"", "");
                String s = s3.replaceAll("https://fakestoreapi.com", "<img src=https\\://fakestoreapi.com");
                String s1 = s.replaceAll("jpg", "jpg style=\"width:100px;height:100px;\" >");

                lines.add(s1);
            }
            logger.debug("image applied? :  " + lines);
            logger.info("image is applied");
            //logger.trace("Images will be displayed in the HTML table for the csv file of FakestoreAPI with url https://fakestoreapi.com/products");
        } catch (FileNotFoundException e) {
            logger.error("./Files/fakestore.csv is not found", e);
        } catch (IOException e) {
            logger.error("Error while reading ./Files/fakestore.csv", e);
        }

        //embrace <td> and <tr> for lines and columns
        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, "<tr><td>" + lines.get(i) + "</td></tr>");
            lines.set(i, lines.get(i).replaceAll(",", "</td><td>"));
        }
        logger.info("Cells are added to HTML table");

        // embrace <table> and </table>
        lines.set(0, "<table border>" + lines.get(0));
        lines.set(lines.size() - 1, lines.get(lines.size() - 1) + "</table>");

        logger.debug("HTML format(String):  " + lines);
        logger.info("table border and table are added HTML Table");

        // output result
        try (FileWriter writer = new FileWriter(outputFile)) {
            File f1 = new File(outputFile);
            if (f1.exists()) {
                logger.warn("previous data in ./Files/CsvToHtml.html will be lost");
            }
            for (String line : lines) {
                writer.write(line + "\n");
            }
            logger.info("./Files/CsvToHtml.html is successfully created");
        } catch (IOException e) {
            logger.error("Error while creating ./Files/CsvToHtml.html", e);
        }
    }
}