import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/1/2017
 * Time: 8:10 AM
 */
public class Day01 {
    public static int solvePart1(final String s) {
        int sumTotal = 0;
        for (int i = 0; i < s.length(); i++) {
            int thisChar = Character.getNumericValue(s.charAt(i));
            int nextChar = Character.getNumericValue(s.charAt((i == (s.length() - 1)) ? 0 : i+1));

            if (thisChar == nextChar) sumTotal += thisChar;
        }

        return sumTotal;
    }

    public static int solvePart2(final String s) {
        int sumTotal = 0;
        for (int i = 0; i < s.length(); i++) {
            int thisChar = Character.getNumericValue(s.charAt(i));
            int nextCharIndex = i + (s.length() / 2);
            if (nextCharIndex >= s.length()) nextCharIndex -= s.length();
            int nextChar = Character.getNumericValue(s.charAt(nextCharIndex));

            if (thisChar == nextChar) sumTotal += thisChar;
        }

        return sumTotal;
    }
    public static void main(String[] args) throws Exception {
        int part1 = Files.lines(Paths.get("input.txt")).findFirst().map(Day01::solvePart1).orElseThrow(RuntimeException::new);
        int part2 = Files.lines(Paths.get("input.txt")).findFirst().map(Day01::solvePart2).orElseThrow(RuntimeException::new);
        System.out.println("Part 1 Answer: " + part1);
        System.out.println("Part 2 Answer: " + part2);
    }
}
