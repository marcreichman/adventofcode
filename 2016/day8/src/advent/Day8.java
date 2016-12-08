package advent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/8/2016
 * Time: 8:01 AM
 */
public class Day8 {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 6;
    private static int screen[][] = new int[WIDTH][HEIGHT];

    static {
        for (int row = 0; row < HEIGHT; row++) {
            for (int column = 0; column < WIDTH; column++) {
                screen[column][row] = 0;
            }
        }
    }

    private static void drawRect(int width, int height) {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                screen[column][row] = 1;
            }
        }
    }

    private static void rotateRow(int row) {
        int newRow[] = new int[WIDTH];
        newRow[0] = screen[WIDTH - 1][row];
        for (int column = 1; column < WIDTH; column++) newRow[column] = screen[column - 1][row];
        for (int column = 0; column < WIDTH; column++) screen[column ][row] = newRow[column];
    }

    private static void rotateColumn(int column) {
        int newColumn[] = new int[HEIGHT];
        newColumn[0] = screen[column][HEIGHT - 1];
        for (int row = 1; row < HEIGHT; row++) newColumn[row] = screen[column][row - 1];
        for (int row = 0; row < HEIGHT; row++) screen[column][row] = newColumn[row];
    }

    public static void main(String... args) throws Exception {
        Files
                .lines(Paths.get("input.txt"))
                .forEachOrdered(s -> {
                    String splits[] = s.split(" ");
                    switch (splits[0]) {
                        case "rect":
                            String coords[] = splits[1].split("x");
                            drawRect(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
                            break;
                        case "rotate":
                            int location = Integer.parseInt(splits[2].split("=")[1]);
                            int amount = Integer.parseInt(splits[splits.length - 1]);
                            if (splits[1].equals("row")) {
                                for (int a = 0; a < amount; a++) rotateRow(location);
                            } else if (splits[1].equals("column")) {
                                for (int a = 0; a < amount; a++) rotateColumn(location);
                            }
                            break;
                        default:
                            throw new IllegalArgumentException("Unknown instruction: " + splits[0]);
                    }

                    System.out.println("Grid:");
                    for (int row = 0; row < HEIGHT; row++) {
                        for (int column = 0; column < WIDTH; column++) {
                            System.out.print(screen[column][row]);
                            System.out.print("\t");
                            if ((column+1) % 5 == 0) System.out.print(" || ");
                        }
                        System.out.println();
                    }
                    System.out.println();
                    System.out.println();
                });

        Long part1Count = Arrays.stream(screen).flatMapToInt(Arrays::stream).filter(i -> i == 1).count();
        System.out.println("Part 1 count: " + part1Count);
    }
}
