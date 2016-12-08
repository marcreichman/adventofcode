package com.pfi.advent;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    private static final int GRID_SQUARE_SIZE = 1000;
    public static void main(String[] args) throws Exception {
        int grid[][] = new int[GRID_SQUARE_SIZE][GRID_SQUARE_SIZE];
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("input.txt"))) {
            String line;
            while ( (line = reader.readLine()) != null ) {
                OperationType op = OperationType.TURNON;

                String lineParts[] = line.split(" ");
                // get op
                if (lineParts.length == 4) {
                    op = OperationType.TOGGLE;
                } else if (lineParts[1].equals("off")) {
                    op = OperationType.TURNOFF;
                }

                // get start and finish points
                String start = lineParts[lineParts.length - 3];
                int x1 = Integer.parseInt(start.split(",")[0]);
                int y1 = Integer.parseInt(start.split(",")[1]);
                String finish = lineParts[lineParts.length - 1];
                int x2 = Integer.parseInt(finish.split(",")[0]);
                int y2 = Integer.parseInt(finish.split(",")[1]);

                for (int x = x1; x <= x2; x++)
                    for (int y = y1; y <= y2; y++)
                        switch (op) {
                            case TURNON:
                                grid[x][y] += 1;
                                break;
                            case TURNOFF:
                                grid[x][y] -= 1;
                                if (grid[x][y] < 0) grid[x][y] = 0;
                                break;
                            case TOGGLE:
                                grid[x][y] = grid[x][y] += 2;
                        }
            }

            int nTotal = 0;
            for (int x = 0; x < GRID_SQUARE_SIZE; x++)
                for (int y = 0; y < GRID_SQUARE_SIZE; y++)
                    nTotal += grid[x][y];

            System.out.println("total: " + nTotal);


        }


    }

    private enum OperationType { TURNON, TURNOFF, TOGGLE };
}
