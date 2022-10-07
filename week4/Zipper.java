/*
 * Copyright (c) 2022.  - All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - indukurimr(Indukuri Maheswar Reddy).
 */


package week4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * This class is to zip the folder
 */

public class Zipper {
    private static final Logger logger = LogManager.getLogger(Zipper.class);
    List fileList;
    private static final String OUTPUT_ZIP_FILE = "/users/azuga/documents/forrmdir/TrainingJava/Files/Reports.zip";
    private static final String SOURCE_FOLDER = "/users/azuga/documents/forrmdir/TrainingJava/Files";

    Zipper() {
        fileList = new ArrayList();
    }

    public static void main(String[] args) {
        Zipper appZip = new Zipper();
        appZip.generateFileList(new File(SOURCE_FOLDER));
        appZip.zipIt(OUTPUT_ZIP_FILE);
    }

    /**
     * Zip it
     *
     * @param zipFile output ZIP file location
     */
    public void zipIt(String zipFile) {
        byte[] buffer = new byte[1024];
        try {
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for (Object file : this.fileList) {
                System.out.println("File Added : " + file);
                ZipEntry ze = new ZipEntry(String.valueOf(file));
                zos.putNextEntry(ze);
                FileInputStream in =
                        new FileInputStream(SOURCE_FOLDER + File.separator + file);
                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                in.close();
            }
            zos.closeEntry();
            //remember close it
            zos.close();
            System.out.println("Done");
        } catch (IOException ex) {
            logger.error("Error in creating {}",zipFile);
        }
    }

    /**
     * Traverse a directory and get all files,
     * and add the file into fileList
     *
     * @param node file or directory
     */
    public void generateFileList(File node) {
        //add file only
        if (node.isFile()) {
            fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
        }
        if (node.isDirectory()) {
            String[] subNote = node.list();
            if (subNote != null) {
                for (String filename : subNote) {
                    generateFileList(new File(node, filename));
                }
            }
        }
    }

    /**
     * Format the file path for zip
     *
     * @param file file path
     * @return Formatted file path
     */
    private String generateZipEntry(String file) {
        return file.substring(SOURCE_FOLDER.length() + 1);
    }
}