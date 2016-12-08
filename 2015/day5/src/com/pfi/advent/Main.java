package com.pfi.advent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {
        AtomicInteger nNice = new AtomicInteger(0);
        Files.lines(Paths.get("input.txt"))
                .parallel()
                .filter(s -> (hasThreeVowels(s) && hasDoubleLetter(s) && hasNoEvil(s)))
                .forEach(s -> nNice.incrementAndGet());

        System.out.println(nNice.get());
    }

    private static boolean hasThreeVowels(String s) {
        int numVowels = 0;
        List<Character> chars = s.chars().mapToObj(e -> ((char) e)).collect(Collectors.toList());
        numVowels += Collections.frequency(chars, 'a');
        numVowels += Collections.frequency(chars, 'e');
        numVowels += Collections.frequency(chars, 'i');
        numVowels += Collections.frequency(chars, 'o');
        numVowels += Collections.frequency(chars, 'u');


        boolean hasThreeVowels = numVowels >= 3;

        return hasThreeVowels;
    }

    private static boolean hasDoubleLetter(String s) {
        boolean hasDouble = false;
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == s.charAt(i+1)) {
                hasDouble = true;
                break;
            }
        }

        return hasDouble;
    }

    private static boolean hasNoEvil(String s) {
        boolean hasNoEvil = !s.contains("ab") && !s.contains("cd") && !s.contains("pq") && !s.contains("xy");

        return hasNoEvil;
    }
}
