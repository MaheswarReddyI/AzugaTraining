/*
 * Copyright (c) 2022.  - All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - indukurimr(Indukuri Maheswar Reddy).
 */


package week1;

import java.io.*;

/**
 * This Mkdir class mimics the mkdir command in linux
 */
public class Mkdir {
    public static void main(String[] args) {
        File f = new File(args[0]);
        if (f.exists()) {
            boolean bool = f.mkdir();
            if (bool) {
                System.out.println("Directory created");
            } else {
                System.out.println("Directory failed to create");
            }
        } else {
            System.out.println(f + " is not found");
        }
    }
}
