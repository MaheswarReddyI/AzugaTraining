/*
 * Copyright (c) 2022.  - All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - indukurimr(Indukuri Maheswar Reddy).
 */

package week1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;


/**
 * This class performs wc command options in linux
 */

public class wcm {
    private static final Logger logger = LogManager.getLogger(wcm.class);

    public static void main(String[] args) {
        if (args.length == 1) {
            wcl(args[0]);
            wcw(args[0]);
            wcc(args[0]);
            System.out.println(args[0]);
        } else {

            // Switch case to perform wc options.
            switch (args[0]) {

                case "-l":
                    wcl(args[1]);
                    break;
                case "-w":
                    wcw(args[1]);
                    break;
                case "-L":
                    wll(args[1]);
                    break;
                case "-c":
                    wcc(args[1]);
                    break;
                default:
                    System.out.println("Enter correct wc command");
            }
        }
    }

    /**
     * It Gives object of BufferReader from input file
     *
     * @param args It takes path of the input file
     * @return It returns the object of BufferReader from taken input
     */
    public static BufferedReader reader(String args) {
        FileReader file = null;
        try {
            file = new FileReader(args);
        } catch (FileNotFoundException e) {
            logger.error(" " + args + " not found", e);
        }
        if (file != null) {
            return new BufferedReader(file);
        }
        return null;
    }

    /**
     * It prints Number of lines present in the given file on to the console
     *
     * @param args -It is used to take the input file path
     */
    public static void wcl(String args) {
        logger.info("wc method is invoked");
        int lc = 0;
        BufferedReader reader = reader(args);
        try {
            String curLine = null;
            if (reader != null) {
                curLine = reader.readLine();
            }
            while (curLine != null) {
                lc++;
                curLine = reader.readLine();
            }
            System.out.print("       " + lc + "     ");
            logger.info("Number of lines in " + args + " is printed");
        } catch (IOException e) {
            logger.error("Error in reading " + args + " ", e);
        }
    }

    /**
     * It prints Number of words present in the given file on to the console
     *
     * @param args -It is used to take the input file path
     */
    public static void wcw(String args) {
        logger.info("wcw method is invoked");
        int wc = 0;
        BufferedReader reader = reader(args);
        try {
            String curLine = null;
            if (reader != null) {
                curLine = reader.readLine();
            }
            while (curLine != null) {
                String[] words = curLine.split(" ");
                wc += words.length;
                curLine = reader.readLine();
            }
            System.out.print(wc + "     ");
            logger.info("Number of words in " + args + " is printed");
        } catch (IOException e) {
            logger.error("Error in reading " + args + " ", e);
        }

    }

    /**
     * It prints Number of characters or bytes present in the given file on to the console
     *
     * @param args -It is used to take the input file path
     */
    public static void wcc(String args) {
        logger.info("wcc method is invoked");
        int c = 0;
        try {
            FileInputStream r = new FileInputStream(args);
            while (r.read() != -1) {
                c += 1;
            }
            System.out.println(c);
            logger.info("Number of characters in " + args + " printed");
        } catch (IOException e) {
            logger.error("Error in reading " + args + " ", e);
        }

    }

    /**
     * It prints length of the longest line present in the given file on to the console.
     *
     * @param args -It is used to take the input file path.
     */
    public static void wll(String args) {
        logger.info("wll method is invoked");
        BufferedReader reader = reader(args);
        try {
            String curLine = null;
            if (reader != null) {
                curLine = reader.readLine();
            }
            int l = 0;
            while (curLine != null) {
                if (curLine.length() > l) {
                    l = curLine.length();
                }
                curLine = reader.readLine();
            }
            logger.debug("length of longest line :   " + l);
            System.out.print("       " + l + "     ");
            logger.info("length of longest line in  " + args + "  is printed");
        } catch (IOException e) {
            logger.error("Error in reading " + args + " ", e);
        }
    }
}
