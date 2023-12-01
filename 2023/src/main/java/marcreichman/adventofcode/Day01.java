package marcreichman.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Day01 extends AdventDayRunner {

    @Override
    protected String getDayString() {
        return "01";
    }

    void runSolutionPartOne() throws IOException {
        runSolutionForStringStream(Files.lines(getInputFile()));
    }

    void runSolutionPartTwo() throws IOException {
        // this solution is annoying, but there are overlapping words which means a plain replacement won't do it
        runSolutionForStringStream(Files.lines(getInputFile()).map(s -> {
            s = s.replaceAll("one", "o1ne");
            s = s.replaceAll("two", "t2wo");
            s = s.replaceAll("three", "t3hree");
            s = s.replaceAll("four", "f4our");
            s = s.replaceAll("five", "f5ive");
            s = s.replaceAll("six", "s6ix");
            s = s.replaceAll("seven", "s7even");
            s = s.replaceAll("eight", "e8ight");
            s = s.replaceAll("nine", "n9ine");
            return s;
        }));
    }

    private void runSolutionForStringStream(final Stream<String> input) {
        input.map(s -> {
            StringBuilder numberString = new StringBuilder();
            for (char c : s.toCharArray()) {
                if (c >= '1' && c <= '9') {
                    numberString.append(c);
                }
            }

            return numberString.toString().strip();
        }).filter(Predicate.not(String::isBlank)).map(s -> Integer.parseInt(s.substring(0, 1) + s.substring(s.length() - 1))).reduce(Math::addExact).ifPresent(System.out::println);
    }

    public static void main(String[] args) throws IOException {
        Day01 day01 = new Day01();
        day01.runSolutionPartOne();
        day01.runSolutionPartTwo();
    }
}