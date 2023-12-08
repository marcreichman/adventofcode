package marcreichman.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Day08 extends AdventDayRunner {
    @Override
    protected String getDayString() {
        return "08";
    }

    public static void main(String[] args) throws IOException {
        runMain(new Day08(), args);
    }

    private enum Direction {RIGHT, LEFT}

    private record Node(String name, String left, String right, boolean isStart) {
    }

    private record Network(List<Direction> directions, String start, Map<String, Node> nodes) {
    }

    private Network parseNetwork(final Path inputFile) throws IOException {
        List<Direction> directions = new ArrayList<>();
        Map<String, Node> nodes = new LinkedHashMap<>();
        for (String line : Files.readAllLines(inputFile)) {
            if (directions.isEmpty()) {
                line.chars().mapToObj(i -> ((char) i)).forEach(c -> directions.add(switch (c) {
                    case 'R' -> Direction.RIGHT;
                    case 'L' -> Direction.LEFT;
                    default -> throw new IllegalArgumentException("Unknown direction literal " + c);
                }));

            } else if (!line.isBlank()) {
                final String thisName = line.substring(0, 3);
                nodes.put(thisName, new Node(thisName, line.substring(7, 10), line.substring(12, 15), thisName.endsWith("A")));
            }
        }

        return new Network(directions, "AAA", nodes);
    }

    @Override
    protected void runSolutionPartOne() throws IOException {
        final Network network = parseNetwork(getInputFile());
        boolean reachedTarget = false;
        Node current = network.nodes.get(network.start);
        int currDirectionIndex = 0;
        long nStep = 0L;
        while (!reachedTarget) {
            nStep++;

            Direction direction = network.directions.get(currDirectionIndex++);
            current = switch (direction) {
                case LEFT -> network.nodes.get(current.left);
                case RIGHT -> network.nodes.get(current.right);
            };

            reachedTarget = current.name.equals("ZZZ");

            if (currDirectionIndex >= network.directions.size()) {
                currDirectionIndex = 0;
            }
        }

        System.out.println("Part One: " + nStep);
    }

    @Override
    protected void runSolutionPartTwo() throws IOException {
        final Network network = parseNetwork(getInputFile());
        boolean reachedTarget = false;
        final List<Node> currentNodes = new ArrayList<>(network.nodes.values().stream().filter(Node::isStart).toList());
        int currDirectionIndex = 0;
        long nStep = 0L;
        while (!reachedTarget) {
            nStep++;

            if (nStep % 1_000_000 == 0) {
                System.out.println("Step " + nStep);
            }
            Direction direction = network.directions.get(currDirectionIndex++);
            for (int i = 0; i < currentNodes.size(); i++) {
                Node current = currentNodes.get(i);
                current = switch (direction) {
                    case LEFT -> network.nodes.get(current.left);
                    case RIGHT -> network.nodes.get(current.right);
                };
                currentNodes.set(i, current);
            }

            reachedTarget = currentNodes.stream().allMatch(n -> n.name.endsWith("Z"));
            if (currDirectionIndex >= network.directions.size()) {
                currDirectionIndex = 0;
            }

        }

        System.out.println("Part Two: " + nStep);
    }
}
