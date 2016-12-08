package com.pfi.advent;

import org.apache.commons.lang.StringEscapeUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        int totalTotal = 0, totalChars = 0, totalChars2 = 0;
        List<String> allLines = Files.readAllLines(Paths.get("input.txt"), StandardCharsets.ISO_8859_1);
        for (String line : allLines) {
            String normalized = normalizeLine(line);
            String normalized2 = StringEscapeUtils.escapeJava(line);
            int total = line.length();
            int chars = normalized.length();
            int chars2 = normalized2.length() + 2;

            totalTotal += total;
            totalChars += chars;
            totalChars2 += chars2;

            System.out.println(line + " -> " + normalized + " total: " + total + " chars: " + chars);
            System.out.println(line + " -> " + normalized2 + " total: " + total + " chars2: " + chars2);
        }

        System.out.println("total: " + totalTotal + " chars " + totalChars + " diff: " + (totalTotal - totalChars));
        System.out.println("total: " + totalTotal + " chars2 " + totalChars2 + " diff: " + (totalChars2 - totalTotal));
    }

    private static String normalizeLine(String line) {
        return line
                .replaceAll("\\\\{2}", "a")
                .replaceAll("\\\\\"", "a")
                .replaceAll("\\\\x..", "a")
                .replaceAll("\"", "")
                ;
    }
}
