package advent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/7/2016
 * Time: 2:15 PM
 */
public class Day7 {
    private static class IP {
        String original;
        Map<String, Boolean> sequences = new LinkedHashMap<>();

        public IP(String original) {
            // parse out sequences and brackets
            boolean inBracket = false;
            String curString = "";
            for (int c = 0; c < original.length(); c++) {
                char thisChar = original.charAt(c);
                if (thisChar == '[') {
                    // save off last string
                    sequences.put(curString, false);
                    curString = "";
                    inBracket = true;
                } else {
                    if (inBracket && thisChar == ']') {
                        sequences.put(curString, true);
                        curString = "";
                        inBracket = false;
                    } else {
                        curString += thisChar;
                    }
                }

                if (c == (original.length() - 1)) {
                    sequences.put(curString, inBracket);
                }
            }

            this.original = original;
        }

        private boolean hasABBA(String input) {
            boolean abba = false;
            if (input.length() >= 4) {
                for (int c = 0; c < input.length(); c++) {
                    if ((c + 3) < input.length()) {
                        if (input.charAt(c) != input.charAt(c+1)) {
                            if (input.charAt(c) == input.charAt(c + 3) && input.charAt(c+1) == input.charAt(c+2)) {
                                abba = true;
                                break;
                            }
                        }
                    }
                }
            }

            return abba;
        }

        private List<String> getAllABAs(String input) {
            List<String> allABAs = new ArrayList<>();
            if (input.length() >= 3) {
                for (int c = 0; c < input.length(); c++) {
                    if ((c + 2) < input.length()) {
                        if (input.charAt(c) != input.charAt(c+1) && input.charAt(c) == input.charAt(c + 2)) {
                            allABAs.add(input.substring(c, c + 3));
                        }
                    }
                }
            }

            return allABAs;
        }

        boolean supportsTLS() {
            boolean foundNormalABBA = false;
            for (Map.Entry<String, Boolean> entry : sequences.entrySet()) {
                if (hasABBA(entry.getKey())) {
                    if (entry.getValue()) {
                        // immediately short circuit if we have an abba and we're in a bracket
                        return false;
                    } else {
                        foundNormalABBA = true;
                    }
                }
            }

            return foundNormalABBA;
        }

        private String invertABA(String aba) {
            return new String(new char[]{aba.charAt(1), aba.charAt(0), aba.charAt(1)});
        }

        boolean supportsSSL() {
            List<String> allABAs = sequences.keySet().stream().filter(k -> !sequences.get(k)).map(this::getAllABAs).flatMap(List::stream).collect(Collectors.toList());
            List<String> allBABs = sequences.keySet().stream().filter(k -> sequences.get(k)).map(this::getAllABAs).flatMap(List::stream).collect(Collectors.toList());

            boolean foundPair = false;
            for (String aba : allABAs) {
                if (allBABs.contains(invertABA(aba))) {
                    foundPair = true;
                    break;
                }
            }

            return foundPair;
        }

    }
    public static void main(String... input) throws Exception {
        Long part1Count = Files
                .lines(Paths.get("input.txt"))
                .map(IP::new)
                .filter(IP::supportsTLS)
                .count();
        System.out.println("Part 1 count: " + part1Count);

        Long part2Count = Files
                .lines(Paths.get("input.txt"))
                .map(IP::new)
                .filter(IP::supportsSSL)
                .count();
        System.out.println("Part 2 count: " + part2Count);
    }
}
