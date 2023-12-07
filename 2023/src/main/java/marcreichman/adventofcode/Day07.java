package marcreichman.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day07 extends AdventDayRunner {

    private static final List<Character> STANDARD_CARD_ORDER = List.of('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A');
    private static final List<Character> JOKER_CARD_ORDER = List.of('J', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'Q', 'K', 'A');

    @Override
    protected String getDayString() {
        return "07";
    }

    public static void main(String[] args) throws IOException {
        runMain(new Day07(), args);
    }

    private enum HandType {FIVE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, THREE_OF_A_KIND, TWO_PAIR, ONE_PAIR, HIGH_CARD}

    private HandType getHandType(final String hand, boolean jokersWild) {
        if (jokersWild && hand.contains("J")) {
            // if we have a wild, go through every other existing card, and add replaced versions to a list.
            // then sort the list and return the top type
            Set<Character> otherChars = hand.chars().mapToObj(i -> ((char) i)).filter(c -> c != 'J').collect(Collectors.toSet());
            if (otherChars.isEmpty()) {
                return HandType.FIVE_OF_A_KIND; // this means we had JJJJJ
            }
            List<HandBid> otherHands = new ArrayList<>();
            for (Character otherChar : otherChars) {
                String otherHand = hand.replace('J', otherChar);
                otherHands.add(new HandBid(otherHand, getHandType(otherHand, false), 0)); // bid irrelevant for this part
            }

            otherHands.sort(new HandBidComparator(STANDARD_CARD_ORDER));
            if (otherHands.isEmpty()) {
                throw new IllegalStateException("Joker-processed hand empty for original hand " + hand);
            }
            return otherHands.get(0).type;
        } else {
            final Map<Character, Integer> cardFrequencies = new TreeMap<>(Collections.reverseOrder());
            final List<Character> cards = hand.chars().mapToObj(i -> ((char) i)).toList();

            // figure out frequency of each character to figure out hand type
            cards.forEach(card -> cardFrequencies.put(card, Collections.frequency(cards, card)));

            int numFrequencies = cardFrequencies.size();

            if (numFrequencies == 1) {
                return HandType.FIVE_OF_A_KIND;
            } else if (cardFrequencies.containsValue(4)) {
                return HandType.FOUR_OF_A_KIND;
            } else if (cardFrequencies.containsValue(3)) {
                // can be full house or three of a kind depending on remaining frequency
                return cardFrequencies.containsValue(2) ? HandType.FULL_HOUSE : HandType.THREE_OF_A_KIND;
            } else if (cardFrequencies.containsValue(2)) {
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
    }

    private record HandBid(String hand, HandType type, long bid) {
    }

    private List<HandBid> parseHandBids(final Path inputFile, boolean jokersWild) throws IOException {
        try (Stream<String> lines = Files.lines(inputFile)) {
            return lines.map(line -> new HandBid(line.substring(0, 5), getHandType(line.substring(0, 5), jokersWild), Long.parseLong(line.substring(6)))).toList();
        }
    }

    @Override
    protected void runSolutionPartOne() throws IOException {
        final List<HandBid> handBids = new ArrayList<>(parseHandBids(getInputFile(), false));
        System.out.println("Part One: " + getTotalWinnings(handBids, STANDARD_CARD_ORDER));
    }

    @Override
    protected void runSolutionPartTwo() throws IOException {
        final List<HandBid> handBids = new ArrayList<>(parseHandBids(getInputFile(), true));
        System.out.println("Part Two: " + getTotalWinnings(handBids, JOKER_CARD_ORDER));
    }

    private long getTotalWinnings(List<HandBid> handBids, List<Character> cardStrengths) {
        handBids.sort(new HandBidComparator(cardStrengths));
        Collections.reverse(handBids);
        AtomicInteger currRank = new AtomicInteger();
        return handBids.stream().mapToLong(hb -> currRank.incrementAndGet() * hb.bid).sum();
    }

    private static class HandBidComparator implements Comparator<HandBid> {
        private final List<Character> cardStrengths;

        public HandBidComparator(List<Character> cardStrengths) {
            this.cardStrengths = cardStrengths;
        }

        @Override
        public int compare(HandBid o1, HandBid o2) {
            if (o1.type.ordinal() != o2.type.ordinal()) {
                return Integer.compare(o1.type.ordinal(), o2.type.ordinal());
            } else {
                // step through cards looking for first mismatch, and then compare card strength
                for (int c = 0; c < o1.hand.length(); c++) {
                    Character c1 = o1.hand.charAt(c);
                    Character c2 = o2.hand.charAt(c);
                    if (!c1.equals(c2)) {
                        return Integer.compare(cardStrengths.indexOf(c2), cardStrengths.indexOf(c1));
                    }

                }
            }

            return 0;
        }
    }
}
