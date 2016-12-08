package com.pfi.advent;

public class Main {
    static final int GOAL = 34000000;

    public static void main(String[] args) {

        final int MAX = 1000000;
        int[] houses = new int[MAX];
        int answer = 0;

        boolean part1 = false;

        for (int elf = 1; elf < MAX; elf++) {
            if (part1) {
                for (int visited = elf; visited < MAX; visited += elf) {
                    houses[visited] += elf * 10;
                }
            } else {
                for (int visited = elf; (visited <= elf*50 && visited < MAX); visited += elf) {
                    houses[visited] += elf * 11;
                }
            }
        }

        for (int i = 0; i < MAX; i++) {
            if (houses[i] >= GOAL) {
                answer = i;
                break;
            }
        }

        System.out.println(answer);

//        for (int nHouse = 1, nPresents = 0; ; nHouse++) {
//            nPresents = getPresentsForHouse(nHouse);
//            if (nHouse % 10000 == 0) System.out.println("House " + nHouse + ": " + nPresents);
//            if (nPresents >= GOAL) {
//                System.out.println("House " + nHouse + " exceeded goal first");
//                break;
//            }
//        }

    }

    private static int getPresentsForHouse(int nHouse) {
        int presents = 0;
        for (int nElf = 1; nElf <= nHouse; nElf++) {
            // only if we're a valid elf for this house (mod == 0)
            if (nHouse % nElf == 0) {
                presents += (nElf * 10);
            }
        }

        return presents;
    }
}
