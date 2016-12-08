package advent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/7/2016
 * Time: 1:13 PM
 */
public class Day6 {
    public static void main(String... args) throws Exception {
        List<List<Character>> columns = new ArrayList<>();
        List<String> allLines = Files.readAllLines(Paths.get("input.txt"));
        boolean first = true;
        for (String line : allLines) {
            if (first) {
                for (int c = 0; c < line.length(); c++) {
                    columns.add(new ArrayList<>());
                }
                first = false;
            }
            for (int c = 0; c < line.length(); c++) {
                columns.get(c).add(line.charAt(c));
            }
        }
        String part1 = "", part2 = "";
        for (List<Character> column : columns) {
            List<Character> colMost = new ArrayList<>(column);
            List<Character> colLeast = new ArrayList<>(column);

            colMost.sort((c1, c2) -> new Integer(Collections.frequency(column, c2)).compareTo(Collections.frequency(column, c1)));
            colLeast.sort((c1, c2) -> new Integer(Collections.frequency(column, c1)).compareTo(Collections.frequency(column, c2)));
            part1 += colMost.get(0);
            part2 += colLeast.get(0);
        }

        System.out.println("Part 1: " + part1);
        System.out.println("Part 2: " + part2);
    }
}
