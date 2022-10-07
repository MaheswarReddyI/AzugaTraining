/*
 * Copyright (c) 2022.  - All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - indukurimr(Indukuri Maheswar Reddy).
 */


package week1;

import java.io.*;
import java.util.logging.*;

/**
 * This Copy class mimics the cp command in linux
 */
public class Copy {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) throws IOException {
        File file = new File("./Files/forcopy.txt");
        if (!file.exists()) {
            LogManager.getLogManager().reset();
            logger.setLevel(Level.CONFIG);
            ConsoleHandler ch = new ConsoleHandler();
            ch.setLevel(Level.INFO);
            logger.addHandler(ch);
            FileHandler fh;
            try {
                fh = new FileHandler("./Files/copy.log");
                fh.setFormatter(new SimpleFormatter());
                fh.setLevel(Level.FINE);
                logger.addHandler(fh);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "File Logger not working ", e);
            }
            FileInputStream r = null;
            try {
                r = new FileInputStream(args[0]);
            } catch (FileNotFoundException e) {
                logger.log(Level.SEVERE, "Input File Path Not Found");
            }
            FileOutputStream w;
            try {
                File f = new File(args[1]);
                if (f.exists()) {
                    logger.severe("previous Data in" + f + " will lost");
                }
                w = new FileOutputStream(args[1]);
                int i;
                if (r != null) {
                    while ((i = r.read()) != -1) {  //r.read() gives ASCII value of character
                        w.write((char) i);
                    }
                }
                logger.log(Level.INFO, "Successful");
            } catch (FileNotFoundException e) {
                logger.log(Level.SEVERE, "Copy Path Not Found");
            }
        } else {
            System.out.println(args[0] + " not found");
        }
    }
}
