package com.pfi.advent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static AuntSue truth = new AuntSue();
    static {
        truth.children = 3;
        truth.cats = 7;
        truth.samoyeds = 2;
        truth.pomeranians = 3;
        truth.akitas = 0;
        truth.vizslas = 0;
        truth.goldfish = 5;
        truth.trees = 3;
        truth.cars = 2;
        truth.perfumes = 1;
    }

    private static List<AuntSue> aunts = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("input.txt"));

        for (String line : lines) {
            String split[] = line.split(" ");
            AuntSue auntSue = new AuntSue();
            auntSue = parseField(auntSue, split[2], split[3]);
            auntSue = parseField(auntSue, split[4], split[5]);
            auntSue = parseField(auntSue, split[6], split[7]);

            aunts.add(auntSue);
        }


        // step 1 simple
        int index = findMatch();
        System.out.println(index + 1);

        // step 2 simple
        index = findMatch2();
        System.out.println(index + 1);

    }

    private static int findMatch() {
        for (int i = 0; i < aunts.size(); i++) {
            AuntSue sue = aunts.get(i);
            int found = 0;
            if (sue.children != null && sue.children == truth.children) found++;
            if (sue.cats != null && sue.cats == truth.cats) found++;
            if (sue.samoyeds != null && sue.samoyeds == truth.samoyeds) found++;
            if (sue.pomeranians != null && sue.pomeranians == truth.pomeranians) found++;
            if (sue.akitas != null && sue.akitas == truth.akitas) found++;
            if (sue.vizslas != null && sue.vizslas == truth.vizslas) found++;
            if (sue.goldfish != null && sue.goldfish == truth.goldfish) found++;
            if (sue.trees != null && sue.trees == truth.trees) found++;
            if (sue.cars != null && sue.cars == truth.cars) found++;
            if (sue.perfumes != null && sue.perfumes == truth.perfumes) found++;

            if (found == 3) return i;
        }

        return 999;
    }

    private static int findMatch2() {
        for (int i = 0; i < aunts.size(); i++) {
            AuntSue sue = aunts.get(i);
            int found = 0;
            if (sue.children != null && sue.children == truth.children) found++;
            if (sue.cats != null && sue.cats > truth.cats) found++;
            if (sue.samoyeds != null && sue.samoyeds == truth.samoyeds) found++;
            if (sue.pomeranians != null && sue.pomeranians < truth.pomeranians) found++;
            if (sue.akitas != null && sue.akitas == truth.akitas) found++;
            if (sue.vizslas != null && sue.vizslas == truth.vizslas) found++;
            if (sue.goldfish != null && sue.goldfish < truth.goldfish) found++;
            if (sue.trees != null && sue.trees > truth.trees) found++;
            if (sue.cars != null && sue.cars == truth.cars) found++;
            if (sue.perfumes != null && sue.perfumes == truth.perfumes) found++;

            if (found == 3) return i;
        }

        return 999;
    }



    private static AuntSue parseField(AuntSue auntSue, String fieldName, String fieldValue) {
        String trimmedName = fieldName.substring(0, fieldName.length() - 1);
        Integer intValue = Integer.parseInt(fieldValue.endsWith(",") ? fieldValue.substring(0, fieldValue.length() - 1) : fieldValue);
        switch (trimmedName) {
            case "children": auntSue.children = intValue; break;
            case "cats": auntSue.cats = intValue; break;
            case "samoyeds": auntSue.samoyeds = intValue; break;
            case "pomeranians": auntSue.pomeranians = intValue; break;
            case "akitas": auntSue.akitas = intValue; break;
            case "vizslas": auntSue.vizslas = intValue; break;
            case "goldfish": auntSue.goldfish = intValue; break;
            case "trees": auntSue.trees = intValue; break;
            case "cars": auntSue.cars = intValue; break;
            case "perfumes": auntSue.perfumes = intValue; break;
        }

        return auntSue;
    }
}
