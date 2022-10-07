/*
 * Copyright (c) 2022.  - All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - indukurimr(Indukuri Maheswar Reddy).
 */


package week1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Pipe Class mimics pipe command in linux.
 */

public class Pipe {


    String ans = null;
    static final Logger logger = LogManager.getLogger(Pipe.class);


    /**
     * Previous answer becomes input for head with -n.
     */

    private static String head(String tempStored, int num) {
        logger.info("input - " + tempStored);
        logger.info("head count - " + num);

        if (num == 0 || tempStored.equals("") || tempStored.equals("Illegal Input")) {
            logger.error("HEAD INPUT ERROR");
            return "Illegal Input";
        }
        StringBuilder data = new StringBuilder();
        String[] arr = tempStored.split("\n\r|\n|\r");

        if (num > arr.length)
            num = arr.length;
        for (int j = 0; j < num; j++) {
            if (j != num - 1) {
                data.append(arr[j]).append("\n");
                logger.trace("Head command - DATA - " + arr[j]);
            } else {
                data.append(arr[j]);
            }
        }
        logger.debug("");
        logger.info("Head method executed");
        return data.toString();
    }

    /**
     * Previous answer becomes input for tail with -n.
     */

    private static String tail(String tempStored, int num) {
        logger.info("input - " + tempStored);
        logger.info("tail count - " + num);

        if (num == 0 || tempStored.equals("") || tempStored.equals("Illegal Input")) {
            logger.error("TAIL INPUT ERROR");
            return "Illegal Input";
        }
        StringBuilder data = new StringBuilder();
        String[] reverse = tempStored.split("\n\r|\n|\r");
        int length = reverse.length;

        if (num > reverse.length)
            num = reverse.length;
        for (int j = length - num; j < length; j++) {
            if (j != length - 1) {
                data.append(reverse[j]).append("\n");
                logger.debug("Tail Data - " + reverse[j]);
            } else {
                data.append(reverse[j]);
            }
        }
        logger.info("Tail method executed");
        return data.toString();
    }

    /**
     * Search file in pwd and return contents in form of String.
     */

    private static String cat(String filename)  {
        String data = null;
        try {
            if (!new File(filename).exists()){
                return "Cat : "+filename+": No such file or directory";
            }
            logger.info("File Path- " + filename);
            data = Files.readString(Path.of(filename));
            logger.debug("DATA -" + data);
            logger.info("Cat executed");
        } catch (IOException e) {
            logger.error("CAT INPUT ERROR - " + e);
        }
        return data;
    }

    /**
     * List files in pwd.
     */
    private static String ls() {
        String path = System.getProperty("user.dir");
        logger.warn("System Default Path - " + path);
        File f = new File(path);
        String[] arr = f.list();

        StringBuilder data = new StringBuilder();
        for (int i = 0; i < Objects.requireNonNull(arr).length; i++) {
            data.append(arr[i]).append("\t\t\t");
            logger.debug("LS -DATA - " + arr[i]);
        }
        logger.info("LS executed");
        return data.toString();
    }

    /**
     * Filter searches for a particular input, and displays all lines that contain that pattern.
     */

    private static String grep(String tempStored, String str) {
        logger.info("input -" + tempStored);
        if (tempStored.equals("") || tempStored.equals("Illegal Input")) {
            logger.error("grep input error");
            return "Illegal Input";
        }
        StringBuilder data = new StringBuilder();
        String[] arr = tempStored.split("\n\r|\n|\r");

        for (String s : arr) {
            if (s.contains(str))
                data.append(s).append("\n");
            logger.debug("grep append - " + s);
        }
        logger.info("grep executed");
        return data.toString();
    }

    /**
     * Filters out the repeated lines in answer variable.
     */
    private static String uniq(String tempStored) {
        logger.info("input- " + tempStored);
        if (tempStored.equals("") || tempStored.equals("Illegal Input")) {
            logger.error("uniq - input error");
            return "Illegal Input";
        }
        StringBuilder data = new StringBuilder();
        String[] arr = tempStored.split("\n\r|\n|\r");

        data.append(arr[0]).append("\n");
        for (int i = 1; i < arr.length - 1; i++) {
            if (arr[i].equals(arr[i - 1]))
                continue;
            else
                data.append(arr[i]).append("\n");
            logger.debug("uniq append - " + arr[i]);
        }
        logger.info("uniq executed");
        return data.toString();
    }

    /**
     * Number of lines, word count, byte/characters count of answer variable.
     */

    private static String wc(String tempStored) {
        logger.info("input - " + tempStored);
        if (tempStored.equals("") || tempStored.equals("Illegal Input")) {
            logger.error("wc input error");
            return "Illegal Input";
        }
        long wordCount, lineCount;
        String[] arr = tempStored.split("\n\r|\n|\r");
        lineCount = arr.length;

        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(tempStored);
        ArrayList<String> array = new ArrayList<>();
        while (m.find()) {
            logger.debug(m.group());
            array.add(m.group());
        }
        wordCount = array.size();

        String data = (lineCount == 0 ? 1 : lineCount - 1) + "\t" + wordCount + "\t" + tempStored.length();


        logger.info("wc -executed");
        return data;
    }

    /**
     * Sorts the contents of answer variable, line by line.
     */

    private static String sort(String tempStored) {
        logger.info("input - " + tempStored);
        if (tempStored.equals("") || tempStored.equals("Illegal Input")) {
            logger.error("sort input error");
            return "Illegal Input";
        }
        StringBuilder data = new StringBuilder();
        String[] arr = tempStored.split("\n\r|\n|\r");

        Arrays.sort(arr, Comparator.comparingInt(str -> str.charAt(0)));

        for (String s : arr) {
            data.append(s).append("\n");
            logger.debug("sort - " + s);
        }
        logger.info("sort executed");
        return data.toString();
    }

    /**
     * Select is used to call respective methods.
     */

    private String selection(String str)  {


        String[] myList = str.split("\\s");
        logger.debug("Command - " + Arrays.toString(myList));

        switch (myList[0]) {
            case "cat": {
                logger.info("cat method invoked");
                ans = cat(myList[1]);
                logger.debug("cat result: " + ans);
            }
            break;
            case "head": {
                logger.info("head method invoked");
                String tempVal = ans;
                int num = Character.getNumericValue(myList[1].charAt(1));

                ans = head(tempVal, num);
                logger.debug("head result: " + ans);
            }
            break;
            case "tail": {
                logger.info("tail method invoked");
                String tempVal = ans;
                int num = Character.getNumericValue(myList[1].charAt(1));

                ans = tail(tempVal, num);
                logger.debug("tail result: " + ans);
            }
            break;
            case "ls": {
                logger.info("ls method invoked");
                ans = ls();
                logger.debug("ls result: " + ans);
            }
            break;
            case "wc": {
                logger.info("wc method invoked");
                String tempVal = ans;

                ans = wc(tempVal);
                logger.debug("wc result: " + ans);
            }
            break;
            case "sort": {
                logger.info("sort method invoked");
                String tempVal = ans;

                ans = sort(tempVal);
                logger.debug("sort result: " + ans);
            }
            break;
            case "uniq": {
                logger.info("uniq method invoked");
                String tempVal = ans;

                ans = uniq(tempVal);
                logger.debug("uniq result: " + ans);
            }
            break;
            case "grep": {
                logger.info("grep method invoked");
                String tempVal = ans;

                ans = grep(tempVal, myList[1]);
                logger.debug("grep result: " + ans);
            }
            break;
            case "": {
                return "";
            }
            default:
                logger.error("command not matched");
                return "Command not Found";
        }
        return ans;

    }

    public String pipeCommand(String args) {
        String res = null;
        Pipe pipes = new Pipe();
        try {
            logger.info("Pipe program invoked");
            logger.info("Command input from CLI");
            String[] command = args.split("\\|");
            ArrayList<String> myList = new ArrayList<>();


            // First: Splitting the input from pipe " | " and Trimming spaces

            logger.info("CLI input string manipulation");
            for (String s : command) {
                String leftTrim = s.replaceAll("^\\s+", "");
                String rightTrim = leftTrim.replaceAll("\\s+$", "");
                String center = rightTrim.replaceAll("\\s+", " ");
                myList.add(center);
                logger.debug("Pipe Command - " + center);
            }
            logger.debug("Method parameters - " + myList);
            // Evaluate all commands one by one.
            logger.info("Invoking switch case and passing parameter");
            int i = 0;
            while (i < myList.size()) {
                res = pipes.selection(myList.get(i));
                i++;
                logger.debug("Executing -PIPE - " + myList.get(i));
            }
        } catch (Exception e) {
            logger.error("INPUT ERROR - main - " + e.getMessage());
        }
        logger.debug("Result - " + res);
        logger.info("Pipe executed");
        return res;
    }
}