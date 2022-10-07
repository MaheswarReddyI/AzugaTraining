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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Arrays;
import java.util.Date;

/**
 * This ls class performs ls options of linux commands
 */

public class ls {

    private static final Logger logger = LogManager.getLogger(ls.class);

    public static void main(String[] args) {

        // Switch case to perform ls options
        switch (args[0]) {

            case "-a":
                lsa(args[1]);
                break;
            case "-x":
                lsx(args[1]);
                break;
            case "-r":
                lsr(args[1]);
                break;
            case "-1":
                ls1(args[1]);
                break;
            case "-m":
                lsm(args[1]);
                break;
            case "-la":
                lsla(args[1]);
                break;
            case "-l":
                lsl(args[1]);
                break;
            default:
                System.out.println("Enter correct ls command");
        }
    }

    /**
     * It prints the files and directories present in the present working directory
     * separated by comma on to the console
     *
     * @param s - It is used to take path of the directory
     */
    public static void lsm(String s) {
        logger.info("lsm method is invoked in ls class");
        File f = new File(s);
        String[] newArr = f.list();
        if (newArr != null) {
            Arrays.sort(newArr);
        }
        if (newArr != null) {
            for (String i : newArr) {
                if (i.charAt(0) != '.') {
                    System.out.print(i + ", ");
                }
            }
        }
        logger.info("files and directories present in the present working directory are successfully " +
                "printed as separated by comma through lsm method in ls class");
    }

    /**
     * It prints the files and directories present in the present working directory
     * in separate line on to the console
     *
     * @param s - It is used to take path of the directory
     */
    public static void ls1(String s) {
        logger.info("ls1 method is invoked in ls class");
        File f = new File(s);
        String[] newArr = f.list();
        if (newArr != null) {
            Arrays.sort(newArr);
        }
        if (newArr != null) {
            for (String i : newArr) {
                if (i.charAt(0) != '.') {
                    System.out.println(i);
                }
            }
        }
        logger.info("files and directories present in the present working directory are successfully " +
                "printed in each line through ls1 method in ls class");
    }

    /**
     * It prints the files and directories present in the present working directory
     * in reverse sorted order on to the console
     *
     * @param s - It is used to take path of the directory
     */
    public static void lsr(String s) {
        logger.info("lsr method is invoked in ls class");
        File f = new File(s);
        String[] newArr = f.list();
        if (newArr != null) {
            Arrays.sort(newArr);
        }
        int i = 0;
        if (newArr != null) {
            i = newArr.length - 1;
        }
        while (i >= 0) {
            if (newArr != null && newArr[i].charAt(0) != '.') {
                System.out.printf(newArr[i] + "\t");
            }
            i = i - 1;
        }
        logger.info("files and directories present in the present working directory are successfully " +
                "printed in reverse sorted order through lsr method in ls class");
    }

    /**
     * It prints the files and directories present in the present working directory on to the console
     *
     * @param s - It is used to take path of the directory
     */
    public static void lsx(String s) {
        logger.info("lsx method is invoked in ls class");
        File f = new File(s);
        String[] newArr = f.list();
        Arrays.sort(newArr != null ? newArr : new String[0]);
        if (newArr != null) {
            for (String i : newArr) {
                if (i.charAt(0) != '.') {
                    System.out.printf(i + "\t");
                }
            }
        }
        logger.info("files and directories present in the present working directory are successfully " +
                "printed through lsx method in ls class");
    }

    /**
     * It prints the files and directories present in the present working directory
     * including hidden files on to the console
     *
     * @param s - It is used to take path of the directory
     */
    public static void lsa(String s) {
        logger.info("lsa method is invoked in ls class");
        File f = new File(s);
        String[] newArr = f.list();
        if (newArr != null) {
            Arrays.sort(newArr);
        }
        if (newArr != null) {
            for (String i : newArr) {
                System.out.printf(i + "\t");
            }
        }
        logger.info("files and directories present in the present working directory are successfully " +
                "printed including hidden files through lsa method in ls class");
    }

    /**
     * It prints all the files and directories present in the present working directory
     * in a long listing format on to the console
     *
     * @param s - It is used to take path of the directory
     */
    public static void lsla(String s) {
        logger.info("lsla method is invoked in ls class");
        File f = new File(s);
        File[] arr = f.listFiles();
        try {
            if (arr != null) {
                for (File f1 : arr) {
                    Path p = Path.of(f1.getPath());
                    PosixFileAttributes ats = Files.readAttributes(p, PosixFileAttributes.class);
                    System.out.print(PosixFilePermissions.toString(ats.permissions()) + " ");
                    System.out.print(ats.owner().getName() + "   ");
                    System.out.print(ats.group().getName() + "   ");
                    System.out.print(ats.size() / 1024 + "kb   ");
                    System.out.print(new Date(f1.lastModified()) + "   ");
                    System.out.print(f1.getName());
                    System.out.println();
                }
            }

        } catch (Exception e) {
            logger.error("File path given is not found for lsla method in ls class", e);
        }
        logger.info("files and directories present in the present working directory are successfully " +
                "printed including in long list format through lsla method in ls class");
    }

    /**
     * It prints the files and directories present in the present working directory
     * in a long listing format on to the console
     *
     * @param s - It is used to take path of the directory
     */
    public static void lsl(String s) {
        logger.info("lsl method is invoked in ls class");
        File f = new File(s);
        File[] arr = f.listFiles();
        try {
            if (arr != null) {
                for (File f1 : arr) {
                    if (!f1.isHidden()) {
                        Path p = Path.of(f1.getPath());
                        PosixFileAttributes ats = Files.readAttributes(p, PosixFileAttributes.class);
                        System.out.print(PosixFilePermissions.toString(ats.permissions()) + " ");
                        System.out.print(ats.owner().getName() + "   ");
                        System.out.print(ats.group().getName() + "   ");
                        System.out.print(ats.size() / 1024 + "kb   ");
                        System.out.print(new Date(f1.lastModified()) + "   ");
                        System.out.print(f1.getName());
                        System.out.println();
                    }
                }
            }

        } catch (Exception e) {
            logger.error("File path given is not found for lsl method in ls class", e);
        }
        logger.info("files and directories present in the present working directory are successfully " +
                "printed in long list format through lsl method in ls class");
    }
}
