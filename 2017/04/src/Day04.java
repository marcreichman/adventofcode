import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/12/2017
 * Time: 11:49 AM
 */
public class Day04 {
    public static boolean isAnagram(String firstWord, String secondWord) {
        char[] word1 = firstWord.replaceAll("[\\s]", "").toCharArray();
        char[] word2 = secondWord.replaceAll("[\\s]", "").toCharArray();
        Arrays.sort(word1);
        Arrays.sort(word2);
        return Arrays.equals(word1, word2);
    }

    public static boolean part1Valid(final String input) {
        Set<String> uniqueTokens = new HashSet<>();
        for (String token : input.split(" ")) {
            if (token.trim().isEmpty()) continue;

            if (!uniqueTokens.add(token.trim())) return false;
        }

        return true;
    }

    public static boolean part2Valid(final String input) {
        List<String> lineWords = Arrays.stream(input.split(" ")).map(String::trim).collect(Collectors.toList());
        for (String word : lineWords) {
            boolean anyAnagrams = lineWords.stream().anyMatch(aWord -> word != aWord && isAnagram(word, aWord));
            if (anyAnagrams) return false;
        }

        return true;
    }

    public static void main(String args[]) throws Exception {
        System.out.println("Part 1 count: " + Files.lines(Paths.get("input.txt")).filter(Day04::part1Valid).count());
        System.out.println("Part 2 count: " + Files.lines(Paths.get("input.txt")).filter(Day04::part2Valid).count());
    }
}
