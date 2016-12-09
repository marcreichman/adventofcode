package advent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/9/2016
 * Time: 8:03 AM
 */
public class Day9 {
    public static String decodeString(String input) {
        StringBuilder output = new StringBuilder();
        int pos = 0;
        while (pos < input.length()) {
            Character thisChar = input.charAt(pos);
            if (thisChar.equals('(')) {
                int markerEndPoint = input.indexOf(')', pos);
                String marker = input.substring(pos + 1, markerEndPoint);
                String markerSplit[] = marker.split("x");
                int numChars = Integer.parseInt(markerSplit[0]);
                int numOutputs = Integer.parseInt(markerSplit[1]);
                int dataStartPoint = markerEndPoint + 1;
                int dataEndPoint = dataStartPoint + numChars;
                String data = input.substring(dataStartPoint, dataEndPoint);
                for (int i = 0; i < numOutputs; i++) {
                    output.append(data);
                }

                pos = dataEndPoint;
            } else {
                output.append(thisChar);
                pos++;
            }
        }

        return output.toString();
    }

    public static long getRecursiveDecodedLength(String input) {
        int pos = 0;
        long dLength = 0;
        while (pos < input.length()) {
            Character thisChar = input.charAt(pos);
            if (thisChar.equals('(')) {
                int markerEndPoint = input.indexOf(')', pos);
                String marker = input.substring(pos + 1, markerEndPoint);
                String markerSplit[] = marker.split("x");
                int numChars = Integer.parseInt(markerSplit[0]);
                int numOutputs = Integer.parseInt(markerSplit[1]);
                int dataStartPoint = markerEndPoint + 1;
                int dataEndPoint = dataStartPoint + numChars;
                String data = input.substring(dataStartPoint, dataEndPoint);
                for (int i = 0; i < numOutputs; i++) {
                    dLength += getRecursiveDecodedLength(data);
                }

                pos = dataEndPoint;
            } else {
                dLength ++;
                pos ++;
            }
        }

        return dLength;
    }

    public static void main(String... args) throws Exception {
        String input = Files.lines(Paths.get("input.txt")).findFirst().get();
        Arrays
                .asList("ADVENT", "A(1x5)BC", "(3x3)XYZ", "A(2x2)BCD(2x2)EFG", "(6x1)(1x3)A", "X(8x2)(3x3)ABCY")
                .forEach(s -> {
                    String decoded = decodeString(s);
                    System.out.println(decoded + ": " + decoded.length());
                });

        String output = decodeString(input);
        System.out.println("Part 1 size: " + output.length());

        Arrays
                .asList("(3x3)XYZ", "X(8x2)(3x3)ABCY", "(27x12)(20x12)(13x14)(7x10)(1x12)A", "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN")
                .forEach(s -> System.out.println(s + ": " + getRecursiveDecodedLength(s)));

        System.out.println("Part 2 size: " + getRecursiveDecodedLength(input));
    }


}
