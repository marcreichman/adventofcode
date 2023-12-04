package marcreichman.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Day04 extends AdventDayRunner {
    @Override
    protected String getDayString() {
        return "04";
    }

    public static void main(String[] args) throws IOException {
        runMain(new Day04(), args);
    }

    private Map<Long, Card> parseCards(final Path inputFile) throws IOException {
        try (final Stream<String> lines = Files.lines(inputFile)) {
            AtomicLong numCard = new AtomicLong(0);
            return lines.map(line -> {
                String winningSide = line.substring(line.indexOf(":") + 1, line.indexOf("|"));
                Set<Integer> winningNumbers = Arrays.stream(winningSide.strip().split("\\s+")).map(s -> Integer.parseInt(s.strip())).collect(Collectors.toSet());

                String inputSide = line.substring(line.indexOf("|") + 1);
                List<Integer> inputNumbers = Arrays.stream(inputSide.strip().split("\\s+")).map(s -> Integer.parseInt(s.strip())).toList();

                return new Card(numCard.incrementAndGet(), winningNumbers, inputNumbers);
            }).collect(Collectors.toMap(Card::id, Function.identity(), (v1, v2) -> v1, LinkedHashMap::new));
        }
    }

    @Override
    protected void runSolutionPartOne() throws IOException {
        final Collection<Card> cards = parseCards(getInputFile()).values();
        long totalSum = cards.stream().mapToLong(card -> {
            long thisSum = 0;
            for (Integer inputNumber : card.inputNumbers) {
                if (card.winningNumbers.contains(inputNumber)) {
                    thisSum += thisSum == 0 ? 1 : thisSum;
                }
            }

            return thisSum;
        }).sum();
        System.out.println("Part One: " + totalSum);
    }

    @Override
    protected void runSolutionPartTwo() throws IOException {
        final Map<Long, Card> cardMap = parseCards(getInputFile());
        Queue<Card> cardScoringQueue = new LinkedList<>(cardMap.values());
        Map<Long, AtomicLong> cardCounts = new HashMap<>();
        do {
            Card card = cardScoringQueue.poll();
            if (card != null) {
                cardCounts.computeIfAbsent(card.id, k -> new AtomicLong()).incrementAndGet();

                long numMatches = card.inputNumbers.stream().filter(card.winningNumbers::contains).count();
                cardScoringQueue.addAll(LongStream.rangeClosed(card.id + 1L, card.id + numMatches).mapToObj(cardMap::get).toList());

            }
        } while (!cardScoringQueue.isEmpty());

        System.out.println("Part Two: " + cardCounts.values().stream().mapToLong(AtomicLong::get).sum());

    }


    private record Card(Long id, Set<Integer> winningNumbers, List<Integer> inputNumbers) {
    }
}
