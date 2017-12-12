import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/12/2017
 * Time: 3:13 PM
 */
public class Day5 {
    public static int part1(final List<Integer> instructions) {
        Integer arr[] = instructions.toArray(new Integer[instructions.size()]);
        int pos = 0;
        int steps = 0;
        while (pos >= 0 && pos < arr.length) {
            int oldPos = pos;
            pos += arr[pos];
            arr[oldPos] ++;

            steps++;
        }

        return steps;
    }

    public static int part2(final List<Integer> instructions) {
        Integer arr[] = instructions.toArray(new Integer[instructions.size()]);
        int pos = 0;
        int steps = 0;
        while (pos >= 0 && pos < arr.length) {
            int oldPos = pos;
            int offset = arr[pos];
            pos += offset;

            if (offset >= 3) {
                arr[oldPos] -= 1;
            } else {
                arr[oldPos] += 1;
            }

            steps++;
        }

        return steps;
    }

    public static void main(String args[]) throws Exception {
        List<Integer> input = Files.lines(Paths.get("input.txt")).map(Integer::parseInt).collect(Collectors.toList());
        System.out.println("Part 1: " + part1(input));
        System.out.println("Part 2: " + part2(input));
    }
}
