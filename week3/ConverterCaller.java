/*
 * Copyright (c) 2022.  - All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - indukurimr(Indukuri Maheswar Reddy).
 */


package week3;


import java.io.IOException;

/**
 * It Converts Csv file into Html, Pdf, Xml files.
 */

public class ConverterCaller {
    public static void main(String[] args) throws IOException, InterruptedException {
        Convertible converter = new Converter();
        converter.getHtml("./Files/fakestore.csv", "./Files/CsvToHtml.html");
        converter.getPdf("./Files/fakestore.csv");
        converter.getXml("./Files/fakestore.json");
    }
}
