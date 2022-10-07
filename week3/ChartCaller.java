/*
 * Copyright (c) 2022.  - All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - indukurimr(Indukuri Maheswar Reddy).
 */

package week3;

/**
 * It creates visual representation of csv Data in the form of charts.
 */

public class ChartCaller {
    public static void main(String[] args) {
        ChartMaker obj = new ChartMaker();
        obj.getBarChart();
        obj.getLineChart();
        obj.getPieChart();
    }
}