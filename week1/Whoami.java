/*
 * Copyright (c) 2022.  - All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - indukurimr(Indukuri Maheswar Reddy).
 */


package week1;

/**
 * This Whoami class mimics the whoami command in linux
 */
class Whoami {
    public static void main(String[] args) {
        String username = System.getProperty("user.name");
        System.out.println(username);
    }
}
