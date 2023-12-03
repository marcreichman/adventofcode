package marcreichman.adventofcode;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Day03 extends AdventDayRunner {
    @Override
    protected String getDayString() {
        return "03";
    }

    public static void main(String[] args) throws IOException {
        runMain(new Day03(), args);
    }

    private List<List<EnginePoint>> parseEngine(final Path inputFile) throws IOException {
        final List<List<EnginePoint>> engine = new ArrayList<>();
        try (final Stream<String> lines = Files.lines(inputFile)) {
            lines.forEachOrdered(line -> {
                List<EnginePoint> pointLine = new ArrayList<>();
                for (int c = 0; c < line.length(); c++) {
                    char thisChar = line.charAt(c);
                    PointType type;
                    if (Character.isDigit(thisChar)) {
                        type = PointType.DIGIT;
                    } else if (thisChar == '.') {
                        type = PointType.NOTHING;
                    } else {
                        type = PointType.SYMBOL;
                    }

                    pointLine.add(new EnginePoint(thisChar, type));
                }

                engine.add(pointLine);
            });
        }

        return engine;
    }

    @Override
    protected void runSolutionPartOne() throws IOException {
        List<List<EnginePoint>> engine = parseEngine(getInputFile());
        final int dimension = engine.size(); // seems to be a square
        List<Integer> validParts = new ArrayList<>();

        // rough plan:
        // walk through line, looking for consecutive EnginePoints to form numbers
        // once we find a number, we then check the previous, current, and next line for adjacent symbols, factoring in indexes one before and one after to account for diagonals
        // collect the valid numbers in a separate list, to sum up at the end
        for (int nLine = 0; nLine < dimension; nLine++) {
            List<EnginePoint> thisLine = engine.get(nLine);
            List<EnginePoint> prevLine = (nLine == 0 ? null : engine.get(nLine - 1));
            List<EnginePoint> nextLine = (nLine == (dimension - 1) ? null : engine.get(nLine + 1));

            for (int nPoint = 0; nPoint < dimension; nPoint++) {
                EnginePoint thisPoint = thisLine.get(nPoint);
                if (thisPoint.type == PointType.DIGIT) {
                    String thisNumStr = "";
                    int numStart = nPoint;
                    int numEnd = nPoint;
                    for (int numPoint = nPoint; numPoint < dimension; numPoint++) {
                        EnginePoint thisNumPoint = thisLine.get(numPoint);
                        if (thisNumPoint.type == PointType.DIGIT) {
                            thisNumStr += thisNumPoint.value;
                        } else {
                            numEnd = numPoint;
                            nPoint += (numEnd - numStart);
                            break;
                        }
                    }

                    int thisNum = Integer.parseInt(thisNumStr);
                    boolean isValid = false;
                    Predicate<EnginePoint> validNeighbor = ep -> (ep.type == PointType.SYMBOL);
                    // now that we have thisNum, let's look around to validate it
                    // this line
                    if (numStart > 0 && validNeighbor.test(thisLine.get(numStart - 1))) {
                        isValid = true;
                    } else if (numEnd < dimension && validNeighbor.test(thisLine.get(numEnd))) {
                        isValid = true;
                    }

                    // prev line
                    if (!isValid && prevLine != null) {
                        List<EnginePoint> subPrevLine = prevLine.subList(Math.max(0, (numStart - 1)), Math.min(dimension, numEnd + 1));
                        isValid = subPrevLine.stream().anyMatch(validNeighbor);
                    }

                    // next line
                    if (!isValid && nextLine != null) {
                        List<EnginePoint> subNextLine = nextLine.subList(Math.max(0, (numStart - 1)), Math.min(dimension, numEnd + 1));
                        isValid = subNextLine.stream().anyMatch(validNeighbor);
                    }


                    if (isValid) {
                        validParts.add(thisNum);
                    }

                }
            }
        }

        int validSum = validParts.stream().mapToInt(Integer::intValue).sum();

        System.out.println("Part one: " + validSum);

    }

    @Override
    protected void runSolutionPartTwo() throws IOException {

    }

    private enum PointType {DIGIT, SYMBOL, NOTHING}

    private record EnginePoint(Character value, PointType type) {
    }
}
