import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/12/2017
 * Time: 11:29 AM
 */
public class Day03Test {
    @Test
    public void testPart1() throws Exception {
        assertEquals(0, Day03.part1(1));
        assertEquals(3, Day03.part1(12));
        assertEquals(2, Day03.part1(23));
        assertEquals(31, Day03.part1(1024));

    }

    @Test
    public void testPart2() throws Exception {
        assertEquals(23, Day03.part2(12));
    }
}