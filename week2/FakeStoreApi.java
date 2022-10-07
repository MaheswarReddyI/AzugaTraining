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
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Objects;


/**
 * FakeStoreApi class call data from Fake store api and convert the JSON to csv file
 */

public class FakeStoreApi {

    private static final Logger logger = LogManager.getLogger(FakeStoreApi.class);

    public static void main(String[] args) {
        api("https://fakestoreapi.com/products");
    }

    public static void api(String site) {
        var request = HttpRequest.newBuilder().GET().uri(URI.create(site)).build();
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
                InputStream file = null;
                try {
                    file = new FileInputStream("./Files/fakestore.json");
                } catch (FileNotFoundException e) {
                    logger.error("./Files/fakestore.json not found", e);
                }
                JSONTokener tokenizer = new JSONTokener(Objects.requireNonNull(file));
                JSONArray jsonArray = new JSONArray(tokenizer);
                StringBuilder csv = new StringBuilder();
                csv.append(CDL.toString(jsonArray));
                try {

                    // Converting JSONArray into csv and save to file
                    Files.write(Path.of("./Files/fakestore.csv"), csv.toString().getBytes());
                    logger.info("./Files/fakestore.csv is created");
                } catch (IOException e) {
                    logger.error("Error occurred in creation of ./Files/fakestore.csv", e);
                }
            } else {
                logger.error("response code " + response.statusCode() + " to api call https://fakestoreapi.com/products");
            }
        }
    }
}