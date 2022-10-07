/*
 * Copyright (c) 2022.  - All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - indukurimr(Indukuri Maheswar Reddy).
 */


package week1;

import java.io.*;

/**
 * This rmdir class mimics the rmdir command in linux
 */

public class rmdir {
    public static void main(String[] args) {
        File f = new File(args[0]);
        String[] arr = f.list();
        if (arr != null) if (arr.length != 0) {
            System.out.println("Cannot be deleted, It contains some directories or files");
        } else {
            boolean is = f.delete();
            if (is) {
                System.out.println("deleted Successfully");
            } else {
                System.out.println("Failed delete");
            }
        }
    }
}
