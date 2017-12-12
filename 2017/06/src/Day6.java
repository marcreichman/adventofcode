import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/12/2017
 * Time: 3:59 PM
 */
public class Day6 {
    public static int part1(final List<Integer> input) {
        List<Integer> working = new ArrayList<>(input);
        int iterations = 0;
        Set<List<Integer>> seenSequences = new LinkedHashSet<>();
        while (true) {
            if (!seenSequences.add(working)) {
                break;
            }

            Integer maxVal = Collections.max(working);
            int pos = working.indexOf(maxVal);

            working.set(pos, 0);
            for (int i = 1; i <= maxVal; i++) {
                int index = (pos + i) % working.size();
                working.set(index, working.get(index) + 1);
            }

            iterations++;
        }

        return iterations;
    }

    public static int part2(final List<Integer> input) {
        List<Integer> working = new ArrayList<>(input);
        int iterations = 0;
        Map<List<Integer>, Integer> seenSequences = new LinkedHashMap<>();
        int cycleDiff = 0;
        while (true) {
            Integer previousLocation = seenSequences.put(working, iterations);
            if (previousLocation != null) {
                cycleDiff = iterations - previousLocation;
                break;
            }


            Integer maxVal = Collections.max(working);
            int pos = working.indexOf(maxVal);

            working.set(pos, 0);
            for (int i = 1; i <= maxVal; i++) {
                int index = (pos + i) % working.size();
                working.set(index, working.get(index) + 1);
            }

            iterations++;
        }

        return cycleDiff;
    }

    public static void main(String args[]) throws Exception {
        List<Integer> input = Arrays.stream(Files.readAllLines(Paths.get("input.txt")).get(0).split("\\t|\\s")).map(Integer::parseInt).collect(Collectors.toList());
        System.out.println("Part 1: " + part1(input));
        System.out.println("Part 2: " + part2(input));
    }
}
