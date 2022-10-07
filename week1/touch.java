/*
 * Copyright (c) 2022.  - All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - indukurimr(Indukuri Maheswar Reddy).
 */


package week1;

import java.io.*;

/**
 * This touch class mimics the touch command in linux
 */

public class touch {
    public static void main(String[] args) {
        File file = new File("./Files");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File f = new File(args[0]);
            if (!f.exists() && f.createNewFile()) {
                System.out.println("File created: " + f.getName());
            } else
                System.out.println("File already exists");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
