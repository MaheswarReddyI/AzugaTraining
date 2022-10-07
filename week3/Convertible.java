/*
 * Copyright (c) 2022.  - All Rights Reserved
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited-
 * @Author - indukurimr(Indukuri Maheswar Reddy).
 */

package week3;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Convertible {
    void getHtml(String csvFile, String outputFile) throws IOException, InterruptedException;

    void getPdf(String inputFile) throws FileNotFoundException;

    void getXml(String loc) throws FileNotFoundException;
}
