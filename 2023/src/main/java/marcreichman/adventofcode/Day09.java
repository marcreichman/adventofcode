package marcreichman.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("DuplicatedCode")
public class Day09 extends AdventDayRunner {
    @Override
    protected String getDayString() {
        return "09";
    }

    public static void main(String[] args) throws IOException {
        runMain(new Day09(), args);
    }

    private record Sequence(List<Integer> steps) {
    }

    private record History(List<Sequence> sequences) {
    }

    private List<History> parseInput(final Path inputFile) throws IOException {
        try (Stream<String> lines = Files.lines(inputFile)) {
            return lines.map(line -> new History(List.of(new Sequence(new ArrayList<>(Arrays.stream(line.split("\\s+")).map(s -> Integer.parseInt(s.strip())).toList()))))).toList();
        }
    }

    private long extrapolateNextValue(final History history) {
        List<Sequence> sequences = new ArrayList<>(history.sequences); // make copy to modify
        Sequence currSequence = sequences.get(0);
        // iterate downward to all zeroes
        boolean finishedDown = false;
        while (!finishedDown) {
            List<Integer> nextSequence = new ArrayList<>();
            for (int i = 0; i < currSequence.steps.size() - 1; i++) {
                int diff = currSequence.steps.get(i + 1) - currSequence.steps.get(i);
                nextSequence.add(diff);
            }


            currSequence = new Sequence(nextSequence);
            sequences.add(currSequence);

            if (currSequence.steps.stream().allMatch(i -> i == 0)) {
                finishedDown = true;
                currSequence.steps.add(0); // add one more to work back up
            }
        }
        // work back up to extrapolate the last value of the original history
        for (int s = sequences.size() - 1; s >= 1; s--) {
            currSequence = sequences.get(s);
            Sequence prevSequence = sequences.get(s - 1);
            prevSequence.steps.add(currSequence.steps.get(currSequence.steps.size() - 1) + prevSequence.steps.get(prevSequence.steps.size() - 1));
        }

        return sequences.get(0).steps.get(sequences.get(0).steps.size() - 1);

    }

    private long extrapolatePrevValue(final History history) {
        List<Sequence> sequences = new ArrayList<>(history.sequences); // make copy to modify
        Sequence currSequence = sequences.get(0);
        // iterate downward to all zeroes
        boolean finishedDown = false;
        while (!finishedDown) {
            List<Integer> nextSequence = new ArrayList<>();
            for (int i = 0; i < currSequence.steps.size() - 1; i++) {
                int diff = currSequence.steps.get(i + 1) - currSequence.steps.get(i);
                nextSequence.add(diff);
            }


            currSequence = new Sequence(nextSequence);
            sequences.add(currSequence);

            if (currSequence.steps.stream().allMatch(i -> i == 0)) {
                finishedDown = true;
                currSequence.steps.add(0, 0); // add one more to the start to work back up
            }
        }

        // work back up to extrapolate the first value of the original history
        for (int s = sequences.size() - 1; s >= 1; s--) {
            currSequence = sequences.get(s);
            Sequence prevSequence = sequences.get(s - 1);
            prevSequence.steps.add(0, prevSequence.steps.get(0) - currSequence.steps.get(0));
        }

        return sequences.get(0).steps.get(0);
    }

    @Override
    protected void runSolutionPartOne() throws IOException {
        final List<History> histories = parseInput(getInputFile());
        System.out.println("Part One: " + histories.stream().mapToLong(this::extrapolateNextValue).sum());
    }

    @Override
    protected void runSolutionPartTwo() throws IOException {
        final List<History> histories = parseInput(getInputFile());
        System.out.println("Part Two: " + histories.stream().mapToLong(this::extrapolatePrevValue).sum());
    }
}
