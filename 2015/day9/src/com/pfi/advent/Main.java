package com.pfi.advent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static Map<String, Map<String, Integer>> distances = new LinkedHashMap<>();
    static Set<String> cities = new LinkedHashSet<>();

    private static int distance(List<String> names) {
        int distance = 0;
        for(int i = 0;i < names.size() - 1;i++) {
            String n1 = names.get(i);
            String n2 = names.get(i + 1);

            if(distances.containsKey(n1) && distances.get(n1).containsKey(n2)) {
                distance += distances.get(n1).get(n2);
            }
            else {
                distance += distances.get(n2).get(n1);
            }
        }

        return distance;
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
            Map<String, Integer> keyMap = distances.get(key);
            if (keyMap == null) {
                keyMap = new LinkedHashMap<>();
                distances.put(key, keyMap);
            }

            keyMap.put(splits[2], Integer.parseInt(splits[4]));
            cities.add(key);
            cities.add(splits[2]);
        }

        Set<Integer> distances = permutations(cities).stream().map(Main::distance).collect(Collectors.toSet());

        System.out.println(Collections.min(distances));
        System.out.println(Collections.max(distances));


    }
}
