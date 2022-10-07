/*
 *
 *   Copyright (c) 2022.  - All Rights Reserved
 *   Unauthorized copying or redistribution of this file in source and binary forms via any medium
 *   is strictly prohibited-
 *   @Author -Maheswar.
 *
 */

package week4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import week1.Pipe;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * PipeTest class tests various unit test cases on Pipe program.
 */

public class PipeTest {

    private static final Logger logger = LogManager.getLogger(PipeTest.class);

    Pipe pipe = new Pipe();

    /**
     * It tests whether the program performs "cat /Users/azuga/Desktop/months.txt | head -7 | tail -4" correctly.
     */
    @Test
    void CatHeadTail1() {
        logger.info("Checking cat /Users/azuga/Desktop/months.txt | head -7 | tail -4 command to give expected output");
        String expected = "April\n" + "May\n" + "June\n" + "July";
        String actual = pipe.pipeCommand("cat /Users/azuga/Desktop/months.txt | head -7 | tail -4");

        assertEquals(expected, actual);
    }

    /**
     * It tests whether the program performs "cat /Users/azuga/Desktop/months.txt | tail -5" correctly.
     */
    @Test
    void CatTail1() {
        logger.info("Checking cat /Users/azuga/Desktop/months.txt | tail -5 command to give expected output");
        String expected = "August\n" + "September\n" + "October\n" + "November\n" + "December";
        String actual = pipe.pipeCommand("cat /Users/azuga/Desktop/months.txt | tail -5");

        assertEquals(expected, actual);
    }

    /**
     * It tests whether the program performs "cat /Users/azuga/Desktop/months.txt | head -5" correctly.
     */
    @Test
    void CatHead1() {
        logger.info("Checking cat /Users/azuga/Desktop/months.txt | head -5 command to give expected output");
        String expected = "January\n" + "February\n" + "March\n" + "April\n" + "May";
        String actual = pipe.pipeCommand("cat /Users/azuga/Desktop/months.txt | head -5");

        assertEquals(expected, actual);
    }

    /**
     * It tests whether the program performs "cat /Users/azuga/Desktop/months.txt | wc" correctly.
     */
    @Test
    void CatWc1() {
        logger.info("Checking cat /Users/azuga/Desktop/months.txt | wc command to give expected output");
        logger.info("It performs Cat wc command");
        String expected = "11\t12\t85";
        String actual = pipe.pipeCommand("cat /Users/azuga/Desktop/months.txt | wc");

        assertEquals(expected, actual);
    }

    /**
     * It tests whether the program performs "cat /Users/azuga/Desktop/forcopy.txt | wc" correctly.
     */
    @Test
    void CatWc2() {
        logger.info("Checking cat /Users/azuga/Desktop/forcopy.txt | wc command to give expected output");
        String expected = "14\t15\t95";
        String actual = pipe.pipeCommand("cat /Users/azuga/Desktop/forcopy.txt | wc");

        assertEquals(expected, actual);
    }

    /**
     * It tests whether the program performs "cat /Users/azuga/Desktop/forcopy.txt | head -5" correctly.
     */
    @Test
    void CatHead2() {
        logger.info("Checking cat /Users/azuga/Desktop/forcopy.txt | head -5 command to give expected output");
        String expected = "Red\n" + "Blue\n" + "White\n" + "Green\n" + "Yellow";
        String actual = pipe.pipeCommand("cat /Users/azuga/Desktop/forcopy.txt | head -5");

        assertEquals(expected, actual);
    }

    /**
     * It tests whether the program performs "cat /Users/azuga/Desktop/forcopy.txt | tail -5" correctly.
     */
    @Test
    void CatTail2() {
        logger.info("Checking cat /Users/azuga/Desktop/forcopy.txt | tail -5 command to give expected output");
        String expected = "Copy\n" + "Move\n" + "Delete\n" + "Add\n" + "Select";
        String actual = pipe.pipeCommand("cat /Users/azuga/Desktop/forcopy.txt | tail -5");

        assertEquals(expected, actual);
    }

    /**
     * It tests whether the program performs "cat /Users/azuga/Desktop/forcopy.txt | head -7 | tail -4" correctly.
     */
    @Test
    void CatHeadTail2() {
        logger.info("Checking cat /Users/azuga/Desktop/forcopy.txt | head -7 | tail -4 command to give expected output");
        logger.info("It performs Cat head Tail command");
        String expected = "Green\n" + "Yellow\n" + "Violet\n" + "Indigo";
        String actual = pipe.pipeCommand("cat /Users/azuga/Desktop/forcopy.txt | head -7 | tail -4");

        assertEquals(expected, actual);
    }

    /**
     * It tests whether the program performs "cet /Users/azuga/Desktop/forcopy.txt | head -7 | tail -4" correctly.
     */
    @Test
    void negativeWrongCatInput() {
        logger.info("Checking program to give Command not Found for wrong input");
        String expected = "Command not Found";
        String actual = pipe.pipeCommand("cet /Users/azuga/Desktop/forcopy.txt | head -7 | tail -4");
        assertEquals(expected, actual);
    }

    /**
     * It tests whether the program performs "" correctly.
     */
    @Test
    void negativeNullInput() {
        logger.info("Checking program to give empty output for empty input");
        String expected = "";
        String actual = pipe.pipeCommand("");
        assertEquals(expected, actual);
    }

    /**
     * It tests whether the program performs "cat /Users/azuga/Desktop/for.txt | head -7 | tail -4" correctly.
     */
    @Test
    void negativeWrongCatFileInput() {
        logger.info("Checking program to give No such file or directory for wrong input file name");
        String expected = "Cat : /Users/azuga/Desktop/for.txt: No such file or directory";
        String actual = pipe.pipeCommand("cat /Users/azuga/Desktop/for.txt | head -7 | tail -4");
        assertEquals(expected, actual);
    }
}