package com.pfi.advent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    static String MY_NAME = "Marc";
    static Map<String, Map<String, Integer>> happinessMap = new LinkedHashMap<>();
    static Set<String> people = new LinkedHashSet<>();

    private static int scoreTableList(List<String> names) {
        int score = 0;
        for(int i = 0;i < names.size();i++) {
            String n1 = names.get(i);
            String n2;
            if (i == (names.size() - 1)) {
                n2 = names.get(0);
            } else {
                n2 = names.get(i + 1);
            }

            if (n1.equals(MY_NAME) || n2.equals(MY_NAME)) continue;

            score += happinessMap.get(n1).get(n2);
            score += happinessMap.get(n2).get(n1);
        }

        return score;
    }

    private static List<List<String>> permutations(Collection<String> names) {
        List<List<String>> permutations = new ArrayList<>();

        permutations(new ArrayList<>(), new ArrayList<>(names), permutations);

        return permutations;
    }

    private static void permutations(List<String> head, List<String> tail, List<List<String>> permutations) {
        if(tail.size() == 0) {
            permutations.add(head);
            return;
        }
        for(int i = 0;i < tail.size();i++) {
            List<String> newHead = new ArrayList<>(head.size() + 1);
            List<String> newTail = new ArrayList<>(tail);

            newHead.addAll(head);
            newHead.add(newTail.remove(i));

            permutations(newHead, newTail, permutations);
        }
    }

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("input.txt"));

        for (String line : lines) {
            String splits[] = line.split(" ");
            String key = splits[0];
            Map<String, Integer> keyMap = happinessMap.get(key);
            if (keyMap == null) {
                keyMap = new LinkedHashMap<>();
                happinessMap.put(key, keyMap);
            }
            String target = splits[10].substring(0, splits[10].length() - 1);
            int value = Integer.parseInt(splits[3]);
            if (splits[2].equals("lose")) value *= -1;
            keyMap.put(target, value);
            people.add(key);
            people.add(target);
        }

        people.add(MY_NAME);

        List<List<String>> permutations = permutations(people);
        Set<Integer> scores = permutations.stream().map(Main::scoreTableList).collect(Collectors.toSet());

//        System.out.println(Collections.min(distances));
        System.out.println(Collections.max(scores));

    }
}
