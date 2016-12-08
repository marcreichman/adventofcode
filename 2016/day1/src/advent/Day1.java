package advent;

import java.util.List;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/1/2016
 * Time: 8:58 AM
 */
public class Day1 {
    private enum Direction {
        NORTH, EAST, SOUTH, WEST;
        public Direction turnRight() {
            int newOrdinal = this.ordinal() + 1;
            if (newOrdinal > (values().length - 1)) {
                newOrdinal -= values().length;
            }
            return values()[newOrdinal];
        }
        public Direction turnLeft() {
            int newOrdinal = this.ordinal() - 1;
            if (newOrdinal < 0) {
                newOrdinal += values().length;
            }
            return values()[newOrdinal];
        }
    };

    public static void main(String... args) throws Exception {
        List<Point> visitedPoints = new ArrayList<>();
        int xPos = 0, yPos = 0;
        Direction direction = Direction.NORTH;
        visitedPoints.add(new Point(xPos, yPos));
        Optional<String> input = Files.lines(Paths.get("input.txt")).findFirst();
        if (input.isPresent()) {
            String[] instructions = input.get().split(", ");
            mainLoop:
            for (String instruction : instructions) {


                if (instruction.charAt(0) == 'R') {
                    direction = direction.turnRight();
                } else if (instruction.charAt(0) == 'L') {
                    direction = direction.turnLeft();
                } else {
                    throw new IllegalArgumentException("Unknown instruction: " + instruction);
                }

                int steps = Integer.parseInt(instruction.substring(1));
                for (int i = 0; i < steps; i++) {
                    switch (direction) {
                        case NORTH: yPos += 1; break;
                        case EAST: xPos += 1; break;
                        case SOUTH: yPos -= 1; break;
                        case WEST: xPos -= 1; break;
                    }

                    // day 2 modifications
                    Point thisPoint = new Point(xPos, yPos);
                    if (visitedPoints.contains(thisPoint)) {
                        break mainLoop;
                    }
                    visitedPoints.add(thisPoint);
                }

            }
            int distance = Math.abs(xPos) + Math.abs(yPos);
            System.out.println("Distance: " + distance);
        }
    }
}
