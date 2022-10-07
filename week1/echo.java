/*
 * Copyright (c) 2022.  - All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - indukurimr(Indukuri Maheswar Reddy).
 */


package week1;

/**
 * This echo class mimics the echo command in linux
 */

public class echo {
    public static void main(String[] args) {
        for (String i : args) {
            System.out.print(i + " ");
        }
    }
}
