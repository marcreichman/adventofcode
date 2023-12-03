package marcreichman.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day02 extends AdventDayRunner {
    private static final int MAX_RED = 12;
    private static final int MAX_GREEN = 13;
    private static final int MAX_BLUE = 14;

    @Override
    protected String getDayString() {
        return "02";
    }

    public static void main(String[] args) throws IOException {
        runMain(new Day02(), args);
    }

    private List<Game> parseGames(final Path inputFile) throws IOException {
        final List<Game> games = new ArrayList<>();
        try (final Stream<String> lines = Files.lines(inputFile)) {
            lines.forEachOrdered(line -> {
                try {
                    final int id = Integer.parseInt(line.substring(5, line.indexOf(":")));
                    String[] turnStrings = line.substring(line.indexOf(":") + 1).split(";");
                    final List<Turn> turns = new ArrayList<>();
                    for (String turnString : turnStrings) {
                        String turnTrimmed = turnString.strip();
                        if (turnTrimmed.isBlank()) {
                            continue;
                        }

                        int red = 0, green = 0, blue = 0;
                        String[] cubes = turnTrimmed.split(",");
                        for (String cube : cubes) {
                            String cubeTrimmed = cube.strip();
                            if (cubeTrimmed.isEmpty()) {
                                continue;
                            }

                            int count = Integer.parseInt(cubeTrimmed.substring(0, cubeTrimmed.indexOf(" ")));
                            String color = cubeTrimmed.substring(cubeTrimmed.indexOf(" ") + 1);
                            switch (color) {
                                case "red" -> red = count;
                                case "green" -> green = count;
                                case "blue" -> blue = count;
                                default -> throw new IllegalArgumentException("Unknown color " + color);
                            }
                        }

                        turns.add(new Turn(red, green, blue));
                    }

                    games.add(new Game(id, turns));
                } catch (Exception parseException) {
                    System.err.println("Could not parse line " + line);
                }
            });
        }

        return games;
    }

    @Override
    protected void runSolutionPartOne() throws IOException {
        final List<Game> allGames = parseGames(getInputFile());
        final List<Game> possibleGames = allGames.stream().filter(game -> {
                    for (Turn turn : game.turns) {
                        if (turn.red > MAX_RED || turn.green > MAX_GREEN || turn.blue > MAX_BLUE) {
                            return false;
                        }
                    }

                    return true;

                })
                .toList();
        int finalSum = possibleGames.stream().mapToInt(Game::id).sum();
        System.out.println("Part one: " + finalSum);
    }

    @Override
    protected void runSolutionPartTwo() throws IOException {
        final List<Game> allGames = parseGames(getInputFile());
        // we will use a Turn here to represent the final sets since it's a unit of three cubes together
        final List<Turn> minimumGameSets = allGames.stream().map(game -> {
            final int maxRed = game.turns.stream().mapToInt(Turn::red).max().orElse(0);
            final int maxGreen = game.turns.stream().mapToInt(Turn::green).max().orElse(0);
            final int maxBlue = game.turns.stream().mapToInt(Turn::blue).max().orElse(0);

            return new Turn(maxRed, maxGreen, maxBlue);
        }).toList();

        int sumOfPowers = minimumGameSets.stream().mapToInt(t -> t.red * t.green * t.blue).sum();
        System.out.println("Part two: " + sumOfPowers);
    }

    private record Turn(int red, int green, int blue) {
    }

    private record Game(int id, List<Turn> turns) {
    }
}
