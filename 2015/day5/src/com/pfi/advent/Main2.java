package com.pfi.advent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main2 {

    static Pattern p2_p1 = Pattern.compile("(..).*\\1");
    static Pattern p2_p2 = Pattern.compile("(.).\\1");

    public static void main(String[] args) throws Exception {
        AtomicInteger nNice = new AtomicInteger(0);
        Files.lines(Paths.get("input.txt"))
                .parallel()
                .filter(s -> (duplicateNoOverlap(s) && duplicateOneInBetween(s)))
                .forEach(s -> nNice.incrementAndGet());

        System.out.println(nNice.get());
    }

    private static boolean duplicateNoOverlap(String s) {
        boolean hasDouble = false;
        List<String> subStrings = new ArrayList<>();
        String lastAddedSubstring = null;
        for (int i = 0; i < s.length() - 1; i++) {
            subStrings.add(s.substring(i, i+2));
        }

        for (int i = 1; i < subStrings.size() - 1; i++) {
            if (!subStrings.get(i).equals(subStrings.get(i+1)) && !subStrings.get(i-1).equals(subStrings.get(i)) && Collections.frequency(subStrings, subStrings.get(i)) > 1) {
                System.out.println("s " + s + " pair good: " + subStrings.get(i));
                hasDouble = true;
                break;
            }
        }

        boolean hasDouble2 = p2_p1.matcher(s).find();
        if (hasDouble2 && !hasDouble) System.out.println("s " + s + " 2 good");
        return hasDouble2;
    }

    private static boolean duplicateOneInBetween(String s) {
        boolean hasDouble = false;
        for (int i = 0; i < s.length() - 2; i++) {
            if (s.charAt(i) == s.charAt(i+2)) {
                System.out.println("s " + s + " dupe phrase " + s.substring(i, i + 3));
                hasDouble = true;
                break;
            }
        }

        //hasDouble = p2_p2.matcher(s).find();

        return hasDouble;
    }


}
