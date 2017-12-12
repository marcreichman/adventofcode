import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/12/2017
 * Time: 11:51 AM
 */
public class Day04Test {
    @Test
    public void testPart1() throws Exception {
        assertTrue(Day04.part1Valid("aa bb cc dd ee"));
        assertFalse(Day04.part1Valid("aa bb cc dd aa"));
        assertTrue(Day04.part1Valid("aa bb cc dd aaa"));
    }

    @Test
    public void testPart2() throws Exception {
        assertTrue(Day04.part2Valid("abcde fghij"));
        assertFalse(Day04.part2Valid("abcde xyz ecdab"));
        assertTrue(Day04.part2Valid("a ab abc abd abf abj"));
        assertTrue(Day04.part2Valid("iiii oiii ooii oooi oooo"));
        assertFalse(Day04.part2Valid("oiii ioii iioi iii"));
    }
}