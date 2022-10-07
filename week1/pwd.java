/*
 * Copyright (c) 2022.  - All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - indukurimr(Indukuri Maheswar Reddy).
 */


package week1;

/**
 * This pwd class mimics the pwd command in linux
 */

public class pwd {
    public static void main(String[] args) {
        String curDir = System.getProperty("user.dir");
        System.out.print(curDir);
    }
}
