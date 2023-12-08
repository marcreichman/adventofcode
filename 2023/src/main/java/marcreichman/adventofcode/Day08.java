package marcreichman.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

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

    private long getStepsToFinish(final Network network, final String start, final Predicate<Node> targetCheck) {
        boolean reachedTarget = false;
        Node current = network.nodes.get(start);
        int currDirectionIndex = 0;
        long nStep = 0L;
        while (!reachedTarget) {
            nStep++;

            Direction direction = network.directions.get(currDirectionIndex++);
            current = switch (direction) {
                case LEFT -> network.nodes.get(current.left);
                case RIGHT -> network.nodes.get(current.right);
            };

            reachedTarget = targetCheck.test(current);

            if (currDirectionIndex >= network.directions.size()) {
                currDirectionIndex = 0;
            }
        }

        return nStep;
    }

    // these two functions are borrowed from  https://www.tutorialsfreak.com/java-tutorial/examples/lcm-array
    // Function to find the GCD of two numbers
    public static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    // Function to find the LCM of an array of numbers
    public static long findLCM(Long[] arr) {
        long lcm = arr[0];
        for (int i = 1; i < arr.length; i++) {
            long currentNumber = arr[i];
            lcm = (lcm * currentNumber) / gcd(lcm, currentNumber);
        }
        return lcm;
    }

    @Override
    protected void runSolutionPartOne() throws IOException {
        final Network network = parseNetwork(getInputFile());
        System.out.println("Part One: " + getStepsToFinish(network, network.start, node -> node.name.equals("ZZZ")));
    }

    @Override
    protected void runSolutionPartTwo() throws IOException {
        final Network network = parseNetwork(getInputFile());
        final List<Node> currentNodes = new ArrayList<>(network.nodes.values().stream().filter(Node::isStart).toList());
        final List<Long> nodeSteps = new ArrayList<>();
        currentNodes.forEach(node -> nodeSteps.add(getStepsToFinish(network, node.name, target -> target.name.endsWith("Z"))));
        System.out.println("Part Two: " + findLCM(nodeSteps.toArray(new Long[0])));
    }
}
