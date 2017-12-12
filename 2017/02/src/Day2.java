import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/12/2017
 * Time: 8:39 AM
 */
public class Day2 {
    public static int part1CheckSum(final Path inputFile) throws IOException {
        AtomicInteger checkSum = new AtomicInteger();
        Files.lines(inputFile).forEach(line -> {
            final List<Integer> lineInts = Arrays
                    .stream(line.trim().split("\\t|\\s"))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            checkSum.addAndGet(Collections.max(lineInts) - Collections.min(lineInts));
        });

        return checkSum.get();
    }

    public static int part2CheckSum(final Path inputFile) throws IOException {
        AtomicInteger checkSum = new AtomicInteger();
        Files.lines(inputFile).forEach(line -> {
            final List<Integer> lineInts = Arrays
                    .stream(line.trim().split("\\t|\\s"))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            mainLoop:
            for (int i = 0; i < lineInts.size(); i++) {
                for (int j = 0; j < lineInts.size(); j++) {
                    if (i != j && lineInts.get(i) % lineInts.get(j) == 0) {
                        checkSum.addAndGet(lineInts.get(i) / lineInts.get(j));
                        break mainLoop;
                    }
                }
            }
        });

        return checkSum.get();
    }

    public static void main(String args[]) throws Exception {
        System.out.println("Part 1: " + Day2.part1CheckSum(Paths.get("input.txt")));
        System.out.println("Part 2: " + Day2.part2CheckSum(Paths.get("input.txt")));
    }
}
