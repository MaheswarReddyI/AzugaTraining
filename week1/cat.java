/*
 * Copyright (c) 2022.  - All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - indukurimr(Indukuri Maheswar Reddy).
 */


package week1;

import java.io.File;
import java.util.Scanner;

/**
 * It prints the data present in the given file
 */

public class cat {
    public static void main(String[] args) throws Exception {

        File testsuite = new File(args[0]);
        if (testsuite.exists()) {
            Scanner scnr = new Scanner(testsuite);

            while (scnr.hasNextLine()) {
                System.out.println(scnr.nextLine());
            }
        } else {
            System.out.println(testsuite + " is not found");
        }
    }
}