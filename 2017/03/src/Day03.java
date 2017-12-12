import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/12/2017
 * Time: 10:52 AM
 */
public class Day03 {
    private static enum Direction {
        RIGHT, UP, LEFT, DOWN;

        public Direction next() {
            return Direction.values()[(this.ordinal() + 1) % 4];
        }

        public Point nextPoint(int x, int y) {
            switch (this) {
                case RIGHT:
                    return new Point(x + 1, y);
                case UP:
                    return new Point(x, y + 1);
                case LEFT:
                    return new Point(x - 1, y);
                case DOWN:
                    return new Point(x, y - 1);
                default: throw new RuntimeException();
            }
        }
    }

    public static void main(String args[]) throws Exception {
        System.out.println("Part 1 for input 277678: " + Day03.part1(277678));
        System.out.println("Part 2 for input 277678: " + Day03.part2(277678));
    }

    public static int part1(final int input) {
        Map<Integer, Point> points = new LinkedHashMap<>();
        int num = 1;
        int x = 0, y = 0;
        points.put(num, new Point(x, y));
        int numSteps = 1;
        Direction direction = Direction.RIGHT;

        while (num <= input) {
            for (int step = 0; step < numSteps; step++) {
                Point nextPoint = direction.nextPoint(x, y);
                x = nextPoint.x;
                y = nextPoint.y;
                points.put(++num, nextPoint);
            }
            direction = direction.next();

            for (int step = 0; step < numSteps; step++) {
                Point nextPoint = direction.nextPoint(x, y);
                x = nextPoint.x;
                y = nextPoint.y;
                points.put(++num, nextPoint);
            }
            direction = direction.next();

            numSteps ++;
        }

        Point finalPoint = points.get(input);
        return Math.abs(finalPoint.x) + Math.abs(finalPoint.y);
    }

    private static int getNumForLocation(final Point location, final Map<Point, Integer> pointsMap) {
        int sum = 0;
        for (int x = (location.x - 1); x <= (location.x + 1); x++) {
            for (int y = (location.y - 1); y <= (location.y + 1); y++) {
                Point thisLoc = new Point(x, y);
                if (!thisLoc.equals(location) && pointsMap.containsKey(thisLoc)) {
                    sum += pointsMap.get(thisLoc);
                }
            }
        }

        return sum;
    }

    public static int part2(final int input) {
        final Map<Point, Integer> points = new LinkedHashMap<>();
        int lastNum = 1;
        int x = 0, y = 0;
        points.put(new Point(x, y), lastNum);
        int numSteps = 1;
        Direction direction = Direction.RIGHT;

        while (true) {
            for (int step = 0; step < numSteps; step++) {
                Point nextPoint = direction.nextPoint(x, y);
                x = nextPoint.x;
                y = nextPoint.y;

                int newNum = getNumForLocation(nextPoint, points);
                points.put(nextPoint, newNum);
                lastNum = newNum;
                if (lastNum > input) return lastNum;
            }
            direction = direction.next();

            for (int step = 0; step < numSteps; step++) {
                Point nextPoint = direction.nextPoint(x, y);
                x = nextPoint.x;
                y = nextPoint.y;

                int newNum = getNumForLocation(nextPoint, points);
                points.put(nextPoint, newNum);
                lastNum = newNum;
                if (lastNum > input) return lastNum;
            }
            direction = direction.next();

            numSteps ++;
        }
    }
}
