import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/1/2017
 * Time: 8:25 AM
 */
public class Day01Test {
    @Test
    public void solvePart1() throws Exception {
        assertEquals(3, Day01.solvePart1("1122"));
        assertEquals(4, Day01.solvePart1("1111"));
        assertEquals(0, Day01.solvePart1("1234"));
        assertEquals(9, Day01.solvePart1("91212129"));
    }

    @Test
    public void solvePart2() throws Exception {
        assertEquals(6,  Day01.solvePart2("1212"));
        assertEquals(0, Day01.solvePart2("1221"));
        assertEquals(4, Day01.solvePart2("123425"));
        assertEquals(12, Day01.solvePart2("123123"));
        assertEquals(4, Day01.solvePart2("12131415"));
    }
}