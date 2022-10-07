/*
 * Copyright (c) 2022.  - All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - indukurimr(Indukuri Maheswar Reddy).
 */


package week3;

import au.com.bytecode.opencsv.CSVReader;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.*;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * It Converts Csv file into Html, Pdf, Xml files.
 */

public class Converter implements Convertible {
    private static final Logger logger = LogManager.getLogger(Converter.class);
    public boolean fileExists;
    /**
     * It creates Pdf file using the csv file
     *
     * @param csvFilePath,outputFilePath - It is used to take path of the csv file and pdf file
     */

    @Override
    public void getHtml(String csvFilePath, String outputFile) throws IOException, InterruptedException {


        // calling getCsv() method to make csv and json file available
        getCsv("https://fakestoreapi.com/products");
        File csvFile = new File(csvFilePath);
        fileExists = csvFile.exists() && csvFile.length() != 0;
        if (fileExists) {
            convertToHtml(csvFilePath, outputFile);
        } else {
            logger.error("Provide Valid {}", csvFilePath);
            throw new FileNotFoundException(csvFilePath + " not found");
        }
    }
    public boolean htmlCreated = false;
    StringBuilder csvHtml = new StringBuilder();
    /**
     * It creates pdf file using the csv file
     *
     * @param csvFilePath,outputFilePath - It is used to take path of the csv file and pdf file
     */

    private void convertToHtml(String csvFilePath, String outputFilePath) throws IOException {

        List<String> lines = new ArrayList<>();
        try (BufferedReader csvFileReader = new BufferedReader(new FileReader(csvFilePath))) {
            logger.info("Reading data from {}", csvFilePath);
            String currentLine;

            //Adding image to the HTML table
            while ((currentLine = csvFileReader.readLine()) != null) {
                String toRemoveQuotes = currentLine.replaceAll("\"", "");
                String toGetImage = toRemoveQuotes.replaceAll("https://fakestoreapi.com",
                        "<img src=https\\://fakestoreapi.com");
                String toSetImageSize = toGetImage.replaceAll("jpg",
                        "jpg style=\"width:100px;height:100px;\" >");

                lines.add(toSetImageSize);
            }
            logger.debug("image applied? :  " + lines);
            logger.info("image is applied");
        } catch (IOException e) {
            logger.error("Error while reading " + csvFilePath, e);
        }

        //embracing <td> and <tr> for lines and columns
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
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            File outputFile = new File(outputFilePath);
            if (outputFile.exists()) {
                logger.warn("previous data in ./Files/CsvToHtml.html will be lost");
            }
            for (String line : lines) {
                csvHtml.append(line).append("\n");
                writer.write(line + "\n");
            }
            logger.info("./Files/CsvToHtml.html is successfully created");
            htmlCreated = true;
        } catch (IOException e) {
            logger.error("Error while creating ./Files/CsvToHtml.html", e);
            throw new IOException("Error while creating " + outputFilePath);
        }
    }

    static boolean pdfCreated = false;

    /**
     * It creates pdf file using the csv file
     *
     * @param inputCSVFile - It is used to take path of the csv file
     */
    @Override
    public void getPdf(String inputCSVFile) throws FileNotFoundException {
        //Read input CSV file in Java
        File fileInputCsv = new File(inputCSVFile);
        fileExists = fileInputCsv.exists() && fileInputCsv.length() != 0;
        if (fileExists) {
            convertToPdf(inputCSVFile);
        } else {
            logger.error("Provide valid csv file");
            throw new FileNotFoundException(inputCSVFile + " not found");
        }
    }

    /**
     * It creates pdf file using the csv file
     *
     * @param inputCSVFile - It is used to take path of the csv file
     */
    private static void convertToPdf(String inputCSVFile) {
        CSVReader reader = null;
        CSVReader colLenReader = null;
        File file = new File(inputCSVFile);
        if (file.exists()) {
            try {
                reader = new CSVReader(new FileReader(inputCSVFile));
                colLenReader = new CSVReader(new FileReader(inputCSVFile));
            } catch (FileNotFoundException e) {
                logger.info("Reading data from" + inputCSVFile + " ");
            }
            logger.info("Reading data from" + inputCSVFile + " ");
        } else {
            logger.info("Provide valid csv file");
        }

        String[] nextLine = new String[0];
        Document document = new Document(PageSize.A3.rotate());

        try {
            PdfWriter.getInstance(document, new FileOutputStream("./Files/csvPdf.pdf"));
            PdfWriter.getInstance(document, new FileOutputStream("./GoldenFiles/csvPdf.pdf"));
        } catch (DocumentException e) {
            logger.error("Error while processing ./Files/csvPdf.pdf");
        } catch (FileNotFoundException e) {
            logger.error("./Files/csvPdf.pdf is not found");
        }
        File pdfFile = new File("./Files/csvPdf.pdf");
        if (pdfFile.exists()) {
            logger.warn("previous data in ./Files/csvPdf.pdf will be lost");
        }
        document.open();
        List<String> length = new ArrayList<>();
        try {
            if (colLenReader != null) {
                length = List.of(colLenReader.readNext());
            }
        } catch (IOException e) {
            logger.error("Error while reading ./Files/fakestore.csv");
        }
        int num = length.size();
        PdfPTable Tables = new PdfPTable(num);
        logger.trace("Number of Columns in pdf: " + num);
        //Fixing Column widths
        float[] columnWidths = new float[]{30f, 20f, 65f, 25f, 15f, 30f, 20f, 28f};
        logger.trace("Width of each column is set");
        try {
            Tables.setWidths(columnWidths);
            logger.trace("Width of each column is set");
        } catch (DocumentException e) {
            logger.warn("Width of columns is not set");
        }
        PdfPCell pdfPCell;
        int c = 0;
        while (true) {
            try {
                if (reader != null && (nextLine = reader.readNext()) == null) break;
            } catch (IOException e) {
                logger.error("Error while reading ./Files/fakestore.csv");
            }
            c++;
            int i = 0;
            logger.debug("Line " + c + " in ./Files/fakestore.csv:  " + Arrays.toString(nextLine));
            while (i <= 7) {
                pdfPCell = new PdfPCell(new Phrase(nextLine[i]));
                Tables.addCell(pdfPCell);
                i++;
            }
        }
        logger.trace("Pdf cells are added to table");
        //Attach table to PDF and close the document
        try {
            document.add(Tables);
        } catch (DocumentException e) {
            logger.error("Error in adding PDFTable to Document");
        }
        logger.trace("Pdf Tables are added to the Document");
        document.close();
        logger.info("./Files/csvPdf.pdf is successfully created");
        pdfCreated = true;
    }

    public boolean xmlCreated = false;

    /**
     * It creates the xml file using the csv file
     *
     * @param jsonPath - It is used to take path of the csv file
     */
    @Override
    public void getXml(String jsonPath) throws FileNotFoundException {
        File fileInputJson = new File(jsonPath);
        fileExists = fileInputJson.exists() && fileInputJson.length() != 0;
        if (fileExists) {
            convertToXml(jsonPath);
        } else {
            logger.error("Provide valid json file");
            throw new FileNotFoundException(jsonPath + " is not found");
        }
    }

    /**
     * It creates the xml file using the csv file
     *
     * @param jsonPath - It is used to take path of the csv file
     */
    public void convertToXml(String jsonPath) {

        // create variable jsonPath that store location of the Sample.json file

        String jsonString;
        try {

            // read byte data from the Sample.json file and convert it into String
            jsonString = new String(Files.readAllBytes(Paths.get(jsonPath)));
            logger.info("Reading data from" + jsonPath);
            //Convert JSON to XML
            jsonString = jsonString.substring(2, jsonString.length() - 2);
            String[] json = jsonString.split("},\\{");
            // This method converts json object to xml string
            StringBuilder xml = new StringBuilder();

            //String xml = convertToXML(jsonString.substring(1,jsonString.length()-1), "root");
            for (int i = 0; i < 20; i++, k++) {
                String xmlVal = convertToXML(json[i], "row");
                xml.append(xmlVal.replaceAll("><", ">\n    <"));
                //xmlVal.replaceAll("><", ">\n<");
            }
            xml.append("\n</root>");
            FileWriter file = new FileWriter("./Files/jtox.xml");

            // use write() method of File to write XML data into XMLData.txt
            file.write(String.valueOf(xml));
            file.flush();
            logger.info("./Files/jtox.xml is successfully created");
            xmlCreated = true;

            // close FileWriter

        } catch (IOException e1) {
            logger.error("Error in reading ./Files/fakestore.json or writing ./Files/jtox.xml", e1);
        }
    }

    public static int k = 0;


    // create convertToXML() method for converting JSOn data into XML
    public static String convertToXML(String jsonString, String root) {    // handle JSONException

        // create instance of JSONObject by passing jsonString to the constructor
        if ((k == 0)) {
            logger.info("convertToXML method invoked");
        }
        jsonString = "{" + jsonString + "}";
        JSONObject jsonObject = new JSONObject(jsonString);
        logger.debug("String Object " + (k + 1) + ":  " + jsonObject);


        // use XML.toString() method to convert JSON into XML and store the result into a string
        if ((k == 0)) {
            return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root>\n  <" + root + ">"
                    + "\n    " + XML.toString(jsonObject) + "\n  </" + root + ">";
        } else {

            return "\n  <row>" + "\n    " + XML.toString(jsonObject) + "\n  </row>";
        }
        // pass the XML data to the main() method
    }

    /**
     * It creates the csv file and json file using fakestoreApi server url
     */
    public void getCsv(String url) throws InterruptedException, IOException {
        var request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        var client = HttpClient.newBuilder().build();
        HttpResponse<String> response = null;
        try {
            logger.info("sending GET request to https://fakestoreapi.com/products");
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            logger.error("URL https://fakestoreapi.com/products is not found", e);
            throw new IOException();
        } catch (InterruptedException e) {
            logger.error("Bad response from https://fakestoreapi.com/products", e);
        }
        if (response != null) {
            if (response.statusCode() == 200) {
                logger.info("response code 200 to api call https://fakestoreapi.com/products");
                String content = response.body();
                logger.debug("Response : " + content);
                JSONArray json = new JSONArray(content);
                StringBuilder result = new StringBuilder("[{");
                int objects = 0;
                int UnFlattenedColumns = 0;

                //Iterating through every JSONObject in the JSONArray
                for (Object obj : json) {

                    if (objects == 0) {
                        logger.trace("FakestoreAPI is flattening");
                    }
                    StringBuilder resultAppender = new StringBuilder();
                    objects++;
                    JSONObject jo = (JSONObject) obj;
                    Iterator<?> keys;
                    keys = jo.keys();

                    //Iterating through key-value pairs in JSONObject
                    while (keys.hasNext()) {
                        String s = (String) keys.next();
                        if (jo.get(s) instanceof JSONObject) {
                            JSONObject job = (JSONObject) jo.get(s);
                            Iterator<?> keys1;
                            keys1 = job.keys();

                            //Iterating through the inner JSONObject
                            while (keys1.hasNext()) {
                                UnFlattenedColumns++;
                                String s1 = (String) keys1.next();
                                resultAppender.append("\"").append(s).append("_").append(s1).append("\"").append(":")
                                        .append("\"").append(job.get(s1)).append("\"");
                                if (keys1.hasNext()) {
                                    resultAppender.append(",");
                                } else {
                                    if (keys.hasNext()) {
                                        resultAppender.append(",");
                                    }
                                }
                            }
                        } else {
                            resultAppender.append("\"").append(s).append("\"").append(":").append("\"").append(jo.get(s)).append("\"");
                            if (keys.hasNext()) {
                                resultAppender.append(",");
                            } else {
                                resultAppender.append("}");
                            }
                        }
                    }
                    if (objects != json.length()) {
                        resultAppender.append(",{");
                    }
                    if (objects == 15) {
                        logger.warn("Processing Large Data, program might get slow");
                    }
                    result.append(resultAppender);
                    logger.debug("Object " + objects + " {" + resultAppender);
                    if (objects == json.length()) {
                        logger.info("Flattening is Done");
                    }
                }
                logger.info((UnFlattenedColumns / objects) + " columns are flattened");
                result.append("]");
                File f = new File("./Files");
                if (!f.exists()) {
                    f.mkdir();
                }

                //It creates JSON file and data will be filled into it
                try (FileWriter fileWriter = new FileWriter("./Files/fakestore.json")) {
                    fileWriter.write(String.valueOf(result));
                    logger.info("Data Filled into ./Files/fakestore.json");
                } catch (IOException e) {
                    logger.error("Unable to write into ./Files/fakestore.json", e);
                }
                InputStream file = null;
                try {
                    file = new FileInputStream("./Files/fakestore.json");
                } catch (FileNotFoundException e) {
                    logger.error("./Files/fakestore.json not found", e);
                }
                if (file != null) {
                    JSONTokener tokenizer = new JSONTokener(file);
                    JSONArray jsonArray = new JSONArray(tokenizer);
                    StringBuilder csv = new StringBuilder();
                    csv.append(CDL.toString(jsonArray));
                    try {

                        // Converting JSONArray into csv and save to file
                        Files.write(Path.of("./Files/fakestore.csv"), csv.toString().getBytes());
                        logger.info("./Files/fakestore.csv is created");
                        File file1 = new File("./Files/fakestore.csv");
                        if (file1.exists()) {
                            logger.warn("previous Data in {} will be lost", file1.getPath());
                        }
                    } catch (IOException e) {
                        logger.error("Error occurred in creation of ./Files/fakestore.csv", e);
                    }
                } else {
                    logger.error("Unable to read into ./Files/fakestore.json");
                }
            } else {
                logger.error("response code " + response.statusCode() +
                        " to api call https://fakestoreapi.com/products");
                throw new InterruptedException();
            }
        }
        logger.error("Unable to get the StatusCode from server");
    }
}
