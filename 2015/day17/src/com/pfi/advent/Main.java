package com.pfi.advent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    static final Integer TARGET_SIZE = 150;
    static List<Integer> containers = new ArrayList<>();

    private static int solve(int liters, int used, List<Integer> unused, List<Integer> sLength) {
        if (liters == 0) {
            sLength.add(used);
            return 1;
        } else if (liters < 0 || unused.isEmpty())
            return 0;
        else {
            List<Integer> tail = unused.subList(1, unused.size());
            return solve(liters - unused.get(0), used + 1, tail, sLength) + solve(liters, used, tail, sLength);
        }
    }

    public static void main(String[] args) throws Exception {
        containers.addAll(
                Files
                        .lines(Paths.get("input.txt"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList())

        );

        List<Integer> solutions = new ArrayList<>();
        int partOne = solve(TARGET_SIZE, 0, containers, solutions);
        System.out.println(partOne);

        int p2Min = solutions.stream().min(Integer::compareTo).get();
        long partTwo = solutions.stream().filter(x -> x.equals(p2Min)).count();
        System.out.println(partTwo);


    }

    static int addList(Collection<Integer> values) { return values.stream().mapToInt(Integer::intValue).sum(); }
}
