package advent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/2/2016
 * Time: 8:27 AM
 */
public class Day2 {
    private enum PositionSimple {
        ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE;

        private PositionSimple up, right, down, left;

        static {
            setPosition(ONE, ONE, TWO, FOUR, ONE);
            setPosition(TWO, TWO, THREE, FIVE, ONE);
            setPosition(THREE, THREE, THREE, SIX, TWO);
            setPosition(FOUR, ONE, FIVE, SEVEN, FOUR);
            setPosition(FIVE, TWO, SIX, EIGHT, FOUR);
            setPosition(SIX, THREE, SIX, NINE, FIVE);
            setPosition(SEVEN, FOUR, EIGHT, SEVEN, SEVEN);
            setPosition(EIGHT, FIVE, NINE, EIGHT, SEVEN);
            setPosition(NINE, SIX, NINE, NINE, EIGHT);
        }

        static void setPosition(PositionSimple target, PositionSimple up, PositionSimple right, PositionSimple down, PositionSimple left) {
            target.up = up;
            target.right = right;
            target.down = down;
            target.left = left;
        }

        public PositionSimple fromInstruction(char instruction) {
            switch (instruction) {
                case 'U': return up;
                case 'R': return right;
                case 'D': return down;
                case 'L': return left;
                default: throw new IllegalArgumentException("Unknown instruction: " + instruction);
            }

        }
    }

    private enum PositionDifficult {
        ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), A("A"), B("B"), C("C"), D("D");

        private PositionDifficult up, right, down, left;
        private String code;

        PositionDifficult(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        static {
            setPosition(ONE, ONE, ONE, THREE, ONE);
            setPosition(TWO, TWO, THREE, SIX, TWO);
            setPosition(THREE, ONE, FOUR, SEVEN, TWO);
            setPosition(FOUR, FOUR, FOUR, EIGHT, THREE);
            setPosition(FIVE, FIVE, SIX, FIVE, FIVE);
            setPosition(SIX, TWO, SEVEN, A, FIVE);
            setPosition(SEVEN, THREE, EIGHT, B, SIX);
            setPosition(EIGHT, FOUR, NINE, C, SEVEN);
            setPosition(NINE, NINE, NINE, NINE, EIGHT);
            setPosition(A, SIX, B, A, A);
            setPosition(B, SEVEN, C, D, A);
            setPosition(C, EIGHT, C, C, B);
            setPosition(D, B, D, D, D);

        }

        static void setPosition(PositionDifficult target, PositionDifficult up, PositionDifficult right, PositionDifficult down, PositionDifficult left) {
            target.up = up;
            target.right = right;
            target.down = down;
            target.left = left;
        }

        public PositionDifficult fromInstruction(char instruction) {
            switch (instruction) {
                case 'U': return up;
                case 'R': return right;
                case 'D': return down;
                case 'L': return left;
                default: throw new IllegalArgumentException("Unknown instruction: " + instruction);
            }

        }
    }
    public static void main(String... args) throws Exception {
        // step 1
        AtomicReference<PositionSimple> step1CurrPosition = new AtomicReference<>(PositionSimple.FIVE);
        String result = Files
                .lines(Paths.get("input.txt"))
                .map(line -> {
                    line.chars().forEach(c -> {
                        step1CurrPosition.set(step1CurrPosition.get().fromInstruction(((char) c)));
                    });
                    return step1CurrPosition.get().ordinal() + 1;
                })
                .map(Object::toString).collect(Collectors.joining(","));
        System.out.println("Step 1 code: " + result);
        
        // step 2
        AtomicReference<PositionDifficult> step2CurrPosition = new AtomicReference<>(PositionDifficult.FIVE);
        result = Files
                .lines(Paths.get("input.txt"))
                .map(line -> {
                    line.chars().forEach(c -> {
                        step2CurrPosition.set(step2CurrPosition.get().fromInstruction(((char) c)));
                    });
                    return step2CurrPosition.get().getCode();
                })
                .collect(Collectors.joining(","));
        System.out.println("Step 2 code: " + result);
    }
}
