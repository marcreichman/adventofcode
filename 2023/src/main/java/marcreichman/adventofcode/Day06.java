package marcreichman.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day06 extends AdventDayRunner {
    @Override
    protected String getDayString() {
        return "06";
    }

    public static void main(String[] args) throws IOException {
        runMain(new Day06(), args);
    }

    private Function<Race, Long> getRaceWinMethods = race -> {
        long winMethods = 0;
        for (long speed = 0; speed < race.time; speed++) {
            long timeLeft = race.time - speed;
            long distance = timeLeft * speed;
            if (distance > race.record) {
                winMethods++;
            }
        }

        return winMethods;
    };

    @Override
    protected void runSolutionPartOne() throws IOException {
        final List<Race> races = parseSeparateRaces(getInputFile());
        final List<Long> winMethodsPerRace = new ArrayList<>();
        for (Race race : races) {
            long winMethods = getRaceWinMethods.apply(race);

            if (winMethods > 0) {
                winMethodsPerRace.add(winMethods);
            }
        }

        long winMethodsProduct = winMethodsPerRace.stream().mapToLong(Long::longValue).reduce(1, Math::multiplyExact);
        System.out.println("Part One: " + winMethodsProduct);
    }

    @Override
    protected void runSolutionPartTwo() throws IOException {
        final Race combinedRace = parseCombinedRace(getInputFile());
        System.out.println("Part Two: " + getRaceWinMethods.apply(combinedRace));
    }

    private List<Race> parseSeparateRaces(final Path inputFile) throws IOException {
        List<String> raceLines = Files.readAllLines(inputFile);
        Function<String, List<Integer>> getIntegers = line -> Arrays.stream(line.substring(line.indexOf(":") + 1).strip().split("\\s+")).map(s -> Integer.parseInt(s.strip())).toList();

        List<Integer> times = getIntegers.apply(raceLines.get(0));
        List<Integer> records = getIntegers.apply(raceLines.get(1));

        List<Race> races = new ArrayList<>();
        for (int i = 0; i < times.size(); i++) {
            races.add(new Race(times.get(i), records.get(i)));
        }

        return races;
    }

    private Race parseCombinedRace(final Path inputFile) throws IOException {
        List<String> raceLines = Files.readAllLines(inputFile);
        Function<String, Long> getCombinedInteger = line -> Long.parseLong(Arrays.stream(line.substring(line.indexOf(":") + 1).strip().split("\\s+")).collect(Collectors.joining()));

        return new Race(getCombinedInteger.apply(raceLines.get(0)), getCombinedInteger.apply(raceLines.get(1)));
    }

    private record Race(long time, long record) {
    }
}
