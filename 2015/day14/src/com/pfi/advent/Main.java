package com.pfi.advent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    static Map<String, ReindeerCapability> capabilities = new LinkedHashMap<>();
    static Map<String, ReindeerState> states = new LinkedHashMap<>();
    static final int RACE_TIME = 2503;

    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("input.txt"));
        for (String line : lines) {
            String splits[] = line.split(" ");
            ReindeerCapability capability = new ReindeerCapability();
            capability.name = splits[0];
            capability.rate = Integer.parseInt(splits[3]);
            capability.max = Integer.parseInt(splits[6]);
            capability.rest = Integer.parseInt(splits[13]);
            capabilities.put(capability.name, capability);
        }

        // setup initial states
        for (String thisDeer : capabilities.keySet()) {
            ReindeerState state = new ReindeerState();
            state.position = 0;
            // use the rest pointer negative to count the number of days until rest is needed
            // and then go positive when rest is needed to count back down
            state.restPointer = capabilities.get(thisDeer).max * -1;

            states.put(thisDeer, state);
        }

        for (int i = 0; i < RACE_TIME; i++) {

            for (String thisDeer : states.keySet()) {
                ReindeerState state = states.get(thisDeer);
                ReindeerCapability capability = capabilities.get(thisDeer);

                // are we resting?
                if (state.restPointer > 0) {
                    state.restPointer--;
                    if (state.restPointer == 0) {
                        state.restPointer = capability.max * -1;
                    }
                } else {
                    // move one turn and update rest stats
                    state.position += capability.rate;
                    state.restPointer++;
                    if (state.restPointer == 0) {
                        state.restPointer = capability.rest;
                    }
                }

            }

            int max = states.values().stream().mapToInt(x -> x.position).max().getAsInt();
            states.values().stream().filter(x -> x.position==max).forEach(x -> x.points++);
        }

        System.out.println("Position winner: " + states.values().stream().mapToInt(x -> x.position).max().getAsInt());
        System.out.println("Points winner: " + states.values().stream().mapToInt(x -> x.points).max().getAsInt());
//        System.out.println("Current winner is at position " + states.values().stream().mapToInt(x -> x.position).max().getAsInt() + " with " + states.get(currWinner).points + " points");

    }


}

class ReindeerCapability {
    String name;
    int rate;
    int max;
    int rest;
}

class ReindeerState {
    int position = 0;
    int restPointer = 0;
    int points = 0;
}
