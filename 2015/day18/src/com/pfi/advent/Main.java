package com.pfi.advent;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static final Integer GRIDSIZE = 100;
    static int[][] grid = new int[GRIDSIZE][GRIDSIZE];

    public static void main(String[] args) throws Exception {
        processInput();
        int[][] gridBackup = copy2dArray(grid);

        // step 1
        for (int step = 0; step < 100; step++) {
            updateGrid(false);
        }

        System.out.println("On: " + Arrays.stream(grid).flatMapToInt(Arrays::stream).filter(x -> x == 1).count());

        // step 2
        grid = gridBackup;
        for (int step = 0; step < 100; step++) {
            updateGrid(true);
        }

        System.out.println("On: " + Arrays.stream(grid).flatMapToInt(Arrays::stream).filter(x -> x == 1).count());
    }

    private static int[][] copy2dArray(int[][] data) {
        return Arrays.stream(data)
                .map((int[] row) -> row.clone())
                .toArray((int length) -> new int[length][]);
    }

    private static void updateGrid(boolean protectCorners) {
        int[][] gridCopy = copy2dArray(grid);

        for (int x = 0; x < GRIDSIZE; x++) {
            for (int y = 0; y < GRIDSIZE; y++) {
                int numNeighbors = getNeighbors(x, y);

                if (grid[x][y] == 1) {
                    if (numNeighbors != 2 && numNeighbors != 3) {
                        boolean corner = (x == 0 && y == 0) ||
                                (x == 0 && y == (GRIDSIZE - 1)) ||
                                (x == (GRIDSIZE - 1) && y == 0) ||
                                (x == (GRIDSIZE - 1) && y == (GRIDSIZE - 1));
                        if (!protectCorners || !corner) {
                            gridCopy[x][y] = 0;
                        }
                    }
                } else {
                    if (numNeighbors == 3) {
                        gridCopy[x][y] = 1;
                    }
                }
            }
        }

        grid = gridCopy;
    }

    private static int getNeighbors(int sourceX, int sourceY) {
        List<Point> neighborPoints = new ArrayList<>();
        neighborPoints.add(new Point(sourceX - 1, sourceY));     // left
        neighborPoints.add(new Point(sourceX - 1, sourceY - 1)); // top-left
        neighborPoints.add(new Point(sourceX, sourceY - 1));     // top
        neighborPoints.add(new Point(sourceX + 1, sourceY - 1)); // top-right
        neighborPoints.add(new Point(sourceX + 1, sourceY));     // right
        neighborPoints.add(new Point(sourceX + 1, sourceY + 1)); // bottom-right
        neighborPoints.add(new Point(sourceX, sourceY + 1)); // bottom
        neighborPoints.add(new Point(sourceX - 1, sourceY + 1)); // bottom-left

        int numInGrid = 0;
        for (Point testPoint : neighborPoints) {
            if (testPoint.getX() >= 0 && testPoint.getX() < GRIDSIZE &&
                    testPoint.getY() >= 0 && testPoint.getY() < GRIDSIZE &&
                    grid[(int) testPoint.getX()][(int) testPoint.getY()] == 1) {
                numInGrid++;
            }
        }
        return numInGrid;
    }

    private static void processInput() throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("input.txt"));
        for (int l = 0; l < lines.size(); l++) {
            String line = lines.get(l);
            for (int c = 0; c < line.length(); c++) {
                grid[c][l] = (line.charAt(c) == '#') ? 1 : 0;
            }
        }
    }
}
