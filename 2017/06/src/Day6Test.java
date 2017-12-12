import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/12/2017
 * Time: 4:08 PM
 */
public class Day6Test {
    @Test
    public void testPart1() throws Exception {
        assertEquals(5, Day6.part1(Arrays.asList(0, 2, 7, 0)));
    }

    @Test
    public void testPart2() throws Exception {
        assertEquals(4, Day6.part2(Arrays.asList(0, 2, 7, 0)));
    }
}