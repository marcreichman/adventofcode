package marcreichman.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Day07 extends AdventDayRunner {
    @Override
    protected String getDayString() {
        return "07";
    }

    public static void main(String[] args) throws IOException {
        runMain(new Day07(), args);
    }

    private final Map<Character, Integer> cardStrengths = Map.ofEntries(
            Map.entry('2', 2),
            Map.entry('3', 3),
            Map.entry('4', 4),
            Map.entry('5', 5),
            Map.entry('6', 6),
            Map.entry('7', 7),
            Map.entry('8', 8),
            Map.entry('9', 9),
            Map.entry('T', 10),
            Map.entry('J', 11),
            Map.entry('Q', 12),
            Map.entry('K', 13),
            Map.entry('A', 14)
    );

    private enum HandType {FIVE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, THREE_OF_A_KIND, TWO_PAIR, ONE_PAIR, HIGH_CARD}

    private HandType getHandType(final String hand) {
        final Map<Character, Integer> cardFrequencies = new TreeMap<>(Collections.reverseOrder());
        final List<Character> cards = hand.chars().mapToObj(i -> ((char) i)).toList();

        // figure out frequency of each character to figure out hand type
        cards.forEach(card -> cardFrequencies.put(card, Collections.frequency(cards, card)));
        int numFrequencies = cardFrequencies.size();

        if (numFrequencies == 1) {
            return HandType.FIVE_OF_A_KIND;
        } else if (cardFrequencies.values().contains(4)) {
            return HandType.FOUR_OF_A_KIND;
        } else if (cardFrequencies.values().contains(3)) {
            // can be full house or three of a kind depending on remaining frequency
            return cardFrequencies.values().contains(2) ? HandType.FULL_HOUSE : HandType.THREE_OF_A_KIND;
        } else if (cardFrequencies.values().contains(2)) {
            // can be two pair or one pair depending on the count of frequency 2
            if (cardFrequencies.values().stream().filter(f -> f == 2).count() == 2) {
                return HandType.TWO_PAIR;
            } else {
                return HandType.ONE_PAIR;
            }
        } else {
            return HandType.HIGH_CARD;
        }

    }

    private record HandBid(String hand, HandType type, long bid) {
    }

    private List<HandBid> parseHandBids(final Path inputFile) throws IOException {
        try (Stream<String> lines = Files.lines(inputFile)) {
            return lines.map(line -> new HandBid(line.substring(0, 5), getHandType(line.substring(0, 5)), Long.parseLong(line.substring(6)))).toList();
        }
    }

    private Comparator<HandBid> handBidComparator = (o1, o2) -> {
        if (o1.type.ordinal() != o2.type.ordinal()) {
            return Integer.compare(o1.type.ordinal(), o2.type.ordinal());
        } else {
            // step through cards looking for first mismatch, and then compare card strength
            for (int c = 0; c < o1.hand.length(); c++) {
                Character c1 = o1.hand.charAt(c);
                Character c2 = o2.hand.charAt(c);
                if (!c1.equals(c2)) {
                    return Integer.compare(cardStrengths.get(c2), cardStrengths.get(c1));
                }

            }
        }
        return 0;
    };

    @Override
    protected void runSolutionPartOne() throws IOException {
        final List<HandBid> handBids = new ArrayList<>(parseHandBids(getInputFile()));
        handBids.sort(handBidComparator);
        Collections.reverse(handBids);
        AtomicInteger currRank = new AtomicInteger();
        long totalWinnings = handBids.stream().mapToLong(hb -> currRank.incrementAndGet() * hb.bid).sum();
        System.out.println("Part One: " + totalWinnings);
    }

    @Override
    protected void runSolutionPartTwo() throws IOException {

    }
}
