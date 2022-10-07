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
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 * This class is to Unzip the Zip file
 */
public class Unzipper {
    private static final Logger logger = LogManager.getLogger(Unzipper.class);
    private static final String INPUT_ZIP_FILE = "/users/azuga/documents/forrmdir/TrainingJava/Files/Reports.zip";
    private static final String OUTPUT_FOLDER = "/users/azuga/documents/forrmdir/TrainingJava/Unzip";
    public static void main( String[] args )
    {
        Unzipper unZip = new Unzipper();
        unZip.unZipIt(INPUT_ZIP_FILE,OUTPUT_FOLDER);
    }
    /**
     * To Unzip the Zip file
     * @param zipFile input zip file
     * @param outputFolder zip file output folder
     */
    public void unZipIt(String zipFile, String outputFolder){
        byte[] buffer = new byte[1024];
        try{
            //create output directory is not exists
            File folder = new File(OUTPUT_FOLDER);
            if(!folder.exists()){
                folder.mkdir();
            }
            //get the zip file content
            ZipInputStream zis =
                    new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();
            while(ze!=null){
                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);
                System.out.println("file unzip : "+ newFile.getAbsoluteFile());
                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            System.out.println("Done");
        }catch(IOException ex){
            logger.error("Error in reading {}",zipFile);
        }
    }
}