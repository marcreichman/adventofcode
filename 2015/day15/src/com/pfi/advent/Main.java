package com.pfi.advent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    List<Ingredient> ingredients = new ArrayList<>();
    private List<Long> cal500Scores = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        new Main().run();


    }

    public void run() throws Exception {
        // parse
        List<String> lines = Files.readAllLines(Paths.get("input.txt"));
        for (String line : lines) {
            String splits[] = line.split(" ");
            Ingredient ingredient = new Ingredient();
            ingredient.name = splits[0].substring(0, splits[0].length() - 1);
            ingredient.capacity = Integer.parseInt(splits[2].substring(0, splits[2].length() - 1));
            ingredient.durability = Integer.parseInt(splits[4].substring(0, splits[4].length() - 1));
            ingredient.flavor = Integer.parseInt(splits[6].substring(0, splits[6].length() - 1));
            ingredient.texture = Integer.parseInt(splits[8].substring(0, splits[8].length() - 1));
            ingredient.calories = Integer.parseInt(splits[10]);

            ingredients.add(ingredient);
        }

        System.out.println("Read " + ingredients.size() + " ingredients");
        long max = Long.MIN_VALUE;
        for (List<Integer> weightList : getWeightsList(ingredients.size(), 100)) {
            long score = getScore(weightList);
            if (score > max) {
                max = score;
            }
        }

        System.out.println("Score: " + max);

        System.out.println("Cal 500 Score: " + Collections.max(cal500Scores));
    }

    // todo: see if we need to go biginteger here
    private long getScore(List<Integer> weights) {
        if (weights.size() != ingredients.size()) throw new IllegalArgumentException("Weight and ingredients list must be the same size");
        
        int capacity = Math.max(0, (weights.get(0) * ingredients.get(0).capacity + weights.get(1) * ingredients.get(1).capacity + weights.get(2) * ingredients.get(2).capacity + weights.get(3) * ingredients.get(3).capacity));
        int durability = Math.max(0, (weights.get(0) * ingredients.get(0).durability + weights.get(1) * ingredients.get(1).durability + weights.get(2) * ingredients.get(2).durability + weights.get(3) * ingredients.get(3).durability));
        int flavor = Math.max(0, (weights.get(0) * ingredients.get(0).flavor + weights.get(1) * ingredients.get(1).flavor + weights.get(2) * ingredients.get(2).flavor + weights.get(3) * ingredients.get(3).flavor));
        int texture = Math.max(0, (weights.get(0) * ingredients.get(0).texture + weights.get(1) * ingredients.get(1).texture + weights.get(2) * ingredients.get(2).texture + weights.get(3) * ingredients.get(3).texture));

        long mainScore = capacity * durability * flavor * texture;
        int calories = Math.max(0, (weights.get(0) * ingredients.get(0).calories + weights.get(1) * ingredients.get(1).calories + weights.get(2) * ingredients.get(2).calories + weights.get(3) * ingredients.get(3).calories));
        if (calories == 500) {
            cal500Scores.add(mainScore);
        }
        return mainScore;
    }

    private List<List<Integer>> getWeightsList(int size, int cap) {
        // todo: work in size
        List<List<Integer>> weights = new ArrayList<>();
        for (int i = 0; i < cap; i++)
            for (int j = 0; j < cap; j++)
                for (int k = 0; k < cap; k++)
                    for (int l = 0; l < cap; l++) {
                        if ((i + j + k + l) == cap) {
                            weights.add(Arrays.asList(i, j, k, l));
                        }
                    }

        return weights;
    }

    private class Ingredient {
        String name;
        int capacity;
        int durability;
        int flavor;
        int texture;
        int calories;
    }
}
