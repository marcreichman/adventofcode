package advent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/7/2016
 * Time: 10:01 AM
 */
public class Day4 {
    private static class Room {
        private class StoredFrequency {
            Character aChar;
            Integer frequency;

            public StoredFrequency(Character aChar, Integer frequency) {
                this.aChar = aChar;
                this.frequency = frequency;
            }
        }

        private String originalEncrypted;
        private String origChecksum;
        private Long sector;
        private List<StoredFrequency> storedFrequencies;

        public Room(String input) {
            String[] splits = input.split("-");
            String last = splits[splits.length - 1];
            this.sector = Long.parseLong(last.substring(0, last.indexOf('[')));
            this.origChecksum = last.substring(last.indexOf('[') + 1, last.length() - 1);
            this.originalEncrypted = input.substring(0, input.indexOf(last) - 1);
            List<Character> allChars = this.originalEncrypted.replaceAll("-", "").chars().mapToObj(i -> (char) i).collect(Collectors.toList());
            Map<Character, Integer> frequencies = new LinkedHashMap<>();
            for (Character aChar : allChars) {
                if (!frequencies.containsKey(aChar)) {
                    frequencies.put(aChar, Collections.frequency(allChars, aChar));
                }
            }

            storedFrequencies = frequencies.entrySet().stream().map(e -> new StoredFrequency(e.getKey(), e.getValue())).collect(Collectors.toList());
            Comparator<StoredFrequency> comparator = Comparator.comparing(sf -> sf.frequency);
            comparator = comparator.reversed();
            comparator = comparator.thenComparing(sf -> sf.aChar);

            storedFrequencies.sort(comparator);
        }

        public boolean isReal() {
            // generate checksum, compare to origChecksum
            return storedFrequencies.subList(0, 5).stream().map(sf -> Character.toString(sf.aChar)).collect(Collectors.joining()).equals(origChecksum);
        }

        public Long getSector() {
            return sector;
        }

        public String getUnencrypted() {
            return this
                    .originalEncrypted
                    .replaceAll("-", " ")
                    .chars()
                    .map(i -> (i != 32) ? (i + sector.intValue() - 97) % 26 + 97 : i)
                    .mapToObj(i -> Character.toString(((char) i)))
                    .collect(Collectors.joining());
        }
    }

    public static void main(String... args) throws Exception {
        Long sectorSum = Files
                .lines(Paths.get("input.txt"))
                .map(Room::new)
                .filter(Room::isReal)
                .mapToLong(Room::getSector)
                .sum();
        System.out.println("Step 1 sum: " + sectorSum);

        String northPoleRooms = Files
                .lines(Paths.get("input.txt"))
                .map(Room::new)
                .filter(Room::isReal)
                .filter(r -> r.getUnencrypted().contains("northpole"))
                .map(r -> r.getSector() + " " + r.getUnencrypted())
                .collect(Collectors.joining(","));
        System.out.println("Step 2: " + northPoleRooms);


    }
}
