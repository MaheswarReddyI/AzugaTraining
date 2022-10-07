/*
 * Copyright (c) 2022.  - All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - indukurimr(Indukuri Maheswar Reddy).
 */


package week4;

import week3.Converter;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


/**
 * ConverterTest class provides various unit test cases for Converter program.
 */

class ConverterTest {

    private static final Logger logger = LogManager.getLogger(ConverterTest.class);

    Converter me;

    @BeforeEach
    void setUp() {

        //Creating Object of Converter class to use for every test case
        me = new Converter();
    }


    /**
     * It tests whether the given input csv file exists are not
     */
    @Test
    void filesExistInHtml() {
        logger.info("Checking ./Files/fakestore.csv exists and ./Files/CsvToHtml.html created or not");
        try {
            me.getHtml("./Files/fakestore.csv", "./Files/CsvToHtml.html");
        } catch (InterruptedException e) {
            logger.error("{} in the getCsv() method", e);
        } catch (IOException e) {
            logger.error("{} in the getHtml() method", e);
        }
        assertTrue(me.fileExists);
        assertTrue(me.htmlCreated);
    }

    /**
     * It tests whether the content of created html file is as expected are not
     */
    @Test
    void htmlContentTest() {
        logger.info("Checking whether the created ./Files/CsvToHtml.html file has expected content or not");
        try {
            me.getHtml("./Files/fakestore.csv", "./Files/CsvToHtml.html");
        } catch (InterruptedException e) {
            logger.error("{} in the getCsv() method", e);
        } catch (IOException e) {
            logger.error("{} in the getHtml() method", e);
        }
        assertTrue(me.fileExists);
        assertTrue(me.htmlCreated);
        File Actual = new File("./Files/CsvToHtml.html");
        File Expected = new File("./GoldenFiles/fake.html");
        boolean isEqual = false;
        try {
            isEqual = FileUtils.contentEquals(Actual, Expected);
        } catch (IOException e) {
            logger.error("{} in checking contents of files {} and {}", e, Actual, Expected);
        }
        assertTrue(isEqual);
    }

    /**
     * It tests whether the given input csv file exists are not
     */
    @Test
    void fileExistsInXml() {
        logger.info("Checking ./Files/fakestore.json exists and ./Files/jtox.xml created or not");
        try {
            me.getXml("./Files/fakestore.json");
        } catch (FileNotFoundException e) {
            logger.error("{} in the getXml() method", e);
        }
        assertTrue(me.fileExists);
        assertTrue(me.xmlCreated);
    }

    /**
     * It tests whether the content of created xml file is as expected are not
     */

    @Test
    void xmlContentTest() {
        logger.info("Checking whether the created ./Files/jtox.xml file has expected content or not");
        try {
            me.getXml("./Files/fakestore.json");
        } catch (FileNotFoundException e) {
            logger.error("{} in the getXml() method", e);
        }
        assertTrue(me.fileExists);
        assertTrue(me.xmlCreated);
        File Actual = new File("./Files/jtox.xml");
        File Expected = new File("./GoldenFiles/fake.xml");
        boolean isEqual = false;
        try {
            isEqual = FileUtils.contentEquals(Actual, Expected);
        } catch (IOException e) {
            logger.error("{} in checking contents of files {} and {}", e, Actual, Expected);
        }
        assertTrue(isEqual);
    }

    /**
     * It tests whether the program gives FileNotFoundException or not if we give wrong input
     */
    @Test
    void negativeInputHtml() {
        logger.info("Checking to throw FileNotFoundException for wrong input ./Files/hello.csv");
        assertThrows(FileNotFoundException.class,
                () -> me.getHtml("./Files/hello.csv", "./Files/CsvToHtml.html"));
    }

    /**
     * It tests whether the program gives FileNotFoundException or not if we give wrong input
     */
    @Test
    void negativeInputFileXml() {
        logger.info("Checking to throw FileNotFoundException for wrong input ./Files/hello.json");
        assertThrows(FileNotFoundException.class,
                () -> me.getXml("./Files/hello.json"));
    }

    /**
     * It tests whether the program gives FileNotFoundException or not if we give wrong input for output file
     */

    @Test
    void negativeOutputFileHtml() {
        logger.info("Checking to throw IOException for not existing output path ./file/File.txt");
        assertThrows(IOException.class,
                () -> me.getHtml("./Files/fakestore.csv", "./file/File.txt"));
    }

    /**
     * It tests whether the program gives FileNotFoundException or not if we give wrong input
     */
    @Test
    void negativeInputFilePdf() {
        logger.info("Checking to throw FileNotFoundException for wrong input ./Files/hello.csv");
        assertThrows(FileNotFoundException.class,
                () -> me.getPdf("./Files/hello.csv"));
    }

    /**
     * It tests whether the content of created xml file is as expected are not
     */
    @Test
    void pdfContentTest() {
        logger.info("Checking whether the created ./Files/csvPdf.pdf file has expected content or not");
        String path1 = null;
        try {
            path1 = "./Files/csvPdf.pdf";
            String path2 = "/Users/azuga/Downloads/csvPdf.pdf";
            PDDocument document = PDDocument.load(new File(path1));
            PDFTextStripper pts = new PDFTextStripper();
            String Actual = pts.getText(document);
            logger.debug("Actual Pdf content : {}", Actual);
            PDDocument document1 = PDDocument.load(new File(path2));
            PDFTextStripper pts1 = new PDFTextStripper();
            String Expected = pts1.getText(document1);
            logger.debug("Expected Pdf content : {}", Expected);
            assertEquals(Expected, Actual);
            document.close();
            document1.close();
        } catch (IOException e) {
            logger.error("{} in reading {}", e, path1);
        }
    }

    /**
     * It tests whether the program gives IOException or not if we give wrong url.
     */
    @Test
    void negativeInputCsv() {
        logger.info("Checking IOException for wrong url https://fakesoreapi.com/products");
        assertThrows(IOException.class,
                () -> me.getCsv("https://fakesoreapi.com/products"));
    }

    /**
     * It tests whether the program gives InterruptedException or not if there is bad response from server.
     */
    @Ignore
    void negativeResponseCode() {
        logger.info("Checking InterruptedException for bad response from server");
        assertThrows(InterruptedException.class,
                () -> me.getCsv("https://fakestoreapi.com/products"));
    }
}