/*
 * Copyright (c) 2022.  - All Rights Reserved
 *  * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *  * is strictly prohibited-
 *  * @Author -IndukuriMr(Indukuri Maheswar Reddy).
 */

package week2;

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

import java.io.*;
import java.util.Arrays;

/**
 * This class converts csv file of fakestore API to pdf file
 */


public class CsvToPdf {

    private static final Logger logger = LogManager.getLogger(CsvToPdf.class);

    public static void main(String[] args) throws IOException {
        //Read input CSV file in Java
        String inputCSVFile = "./Files/fakestore.csv";
        File f = new File(inputCSVFile);
        if (f.exists() && f.length() != 0) {
            ConvertToPdf(inputCSVFile);
        } else {
            logger.error("Provide valid csv file");
        }
    }

    private static void ConvertToPdf(String inputCSVFile) throws IOException {
        CSVReader read = null;
        try {
            read = new CSVReader(new FileReader(inputCSVFile));
            logger.info("Reading data from" + inputCSVFile + " ");
        } catch (FileNotFoundException e) {
            logger.error("./Files/fakestore.csv is not found", e);
        }
        String[] nextLine;
        Document document = new Document(PageSize.A3.rotate());

        try {
            PdfWriter.getInstance(document, new FileOutputStream("./Files/csv-pdf.pdf"));
        } catch (DocumentException e) {
            logger.error("Error while processing ./Files/csv-pdf.pdf");
        } catch (FileNotFoundException e) {
            logger.error("./Files/csv-pdf.pdf is not found");
        }
        File f1 = new File("./Files/csv-pdf.pdf");
        if (f1.exists()) {
            logger.warn("previous data in ./Files/csv-pdf.pdf will be lost");
        }
        document.open();
        int num = 8;
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
        if (read != null) {
            while ((nextLine = read.readNext()) != null) {
                c++;
                int i = 0;
                logger.debug("Line " + c + " in ./Files/fakestore.csv:  " + Arrays.toString(nextLine));
                while (i <= 7) {
                    pdfPCell = new PdfPCell(new Phrase(nextLine[i]));
                    Tables.addCell(pdfPCell);
                    i++;
                }
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
        logger.info("./Files/csv-pdf.pdf is successfully created");
    }
}
