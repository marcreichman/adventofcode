package marcreichman.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Day05 extends AdventDayRunner {
    @Override
    protected String getDayString() {
        return "05";
    }

    public static void main(String[] args) throws IOException {
        runMain(new Day05(), args);
    }

    private record SourceDestMapEntry(Long destStart, Long sourceStart, Long rangeLength) {
    }

    private record Almanac(List<Long> seeds, List<SourceDestMapEntry> seed, List<SourceDestMapEntry> soil,
                           List<SourceDestMapEntry> fertilizer, List<SourceDestMapEntry> water,
                           List<SourceDestMapEntry> light, List<SourceDestMapEntry> temperature,
                           List<SourceDestMapEntry> humidity) {
        List<List<SourceDestMapEntry>> getAllMaps() {
            return List.of(seed, soil, fertilizer, water, light, temperature, humidity);
        }
    }

    private Almanac parseAlmanac(final Path inputFile) throws IOException {
        final List<Long> seeds = new ArrayList<>();
        final List<List<SourceDestMapEntry>> maps = new ArrayList<>();
        final List<String> fullInput = Files.readAllLines(inputFile);
        List<SourceDestMapEntry> currMap = null;
        for (String line : fullInput) {
            // seeds are first
            if (seeds.isEmpty()) {
                seeds.addAll(Arrays.stream(line.substring(line.indexOf(":") + 1).strip().split("\\s+")).map(s -> Long.parseLong(s.strip())).toList());
            } else if (line.isBlank()) {
                if (currMap != null) {
                    maps.add(currMap);
                }

                currMap = new ArrayList<>();
            } else if (!line.contains(":")) {
                long[] splits = Arrays.stream(line.split("\\s")).mapToLong(Long::parseLong).toArray();
                currMap.add(new SourceDestMapEntry(splits[0], splits[1], splits[2]));
            }

        }

        // add the last one with no blank line
        maps.add(currMap);

        return new Almanac(seeds, maps.get(0), maps.get(1), maps.get(2), maps.get(3), maps.get(4), maps.get(5), maps.get(6));
    }

    private long runSolution(final boolean seedsAreRanges) throws IOException {
        final Almanac almanac = parseAlmanac(getInputFile());
        final Function<Long, Long> getLocationForSeed = getLocationForSpeed(almanac);

        List<Long> seeds = almanac.seeds;
        if (seedsAreRanges) {
            ExecutorService executorService = Executors.newFixedThreadPool(10);
            List<Long> minLocations = new CopyOnWriteArrayList<>();
            for (int s = 0, seedsSize = seeds.size(); s < seedsSize; s += 2) {
                final long rangeStart = seeds.get(s);
                final long rangeEnd = seeds.get(s) + seeds.get(s + 1);
                executorService.submit(() -> {
                    long minLocation = Long.MAX_VALUE;
                    for (long l = rangeStart; l < rangeEnd; l++) {
                        minLocation = Math.min(minLocation, getLocationForSeed.apply(l));
                    }

                    minLocations.add(minLocation);
                });

            }

            try {
                executorService.shutdown();
                executorService.awaitTermination(15, TimeUnit.MINUTES);
            } catch (InterruptedException intEx) {
                Thread.currentThread().interrupt();
                return 0L;
            }

            return minLocations.stream().mapToLong(Long::longValue).min().orElseThrow();
        } else {
            final Map<Long, Long> locations = new HashMap<>();
            seeds.forEach(seed -> locations.computeIfAbsent(seed, getLocationForSeed));
            return locations.values().stream().mapToLong(Long::longValue).min().orElseThrow();
        }
    }

    private static Function<Long, Long> getLocationForSpeed(Almanac almanac) {
        BiFunction<List<SourceDestMapEntry>, Long, Long> getDestForSource = (map, source) -> {
            long destination = source; // default condition if not found in map
            for (SourceDestMapEntry entry : map) {
                if (source >= entry.sourceStart && source < (entry.sourceStart + entry.rangeLength)) {
                    // we found the range, get an offset so we can apply it to the dest range
                    long offset = source - entry.sourceStart;
                    destination = entry.destStart + offset;
                    break;
                }
            }

            return destination;
        };

        Function<Long, Long> getLocationForSeed = seed -> {
//            System.out.println("Evaluating seed " + seed);
            // find which range the seed is in, hopping map to map to get the final location number
            Long source = seed;
            Long destination = null;
            for (List<SourceDestMapEntry> map : almanac.getAllMaps()) {
                destination = getDestForSource.apply(map, source);
                source = destination;
            }

            return destination;
        };
        return getLocationForSeed;
    }

    @Override
    protected void runSolutionPartOne() throws IOException {
        System.out.println("Part One: " + runSolution(false));
    }


    @Override
    protected void runSolutionPartTwo() throws IOException {
        System.out.println("Part Two: " + runSolution(true));
    }
}
