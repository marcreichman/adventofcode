import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/12/2017
 * Time: 3:18 PM
 */
public class Day5Test {
    @Test
    public void testPart1() throws Exception {
        assertEquals(5, Day5.part1(Arrays.asList(0, 3, 0, 1, -3)));
    }

    @Test
    public void testPart2() throws Exception {
        assertEquals(10, Day5.part2(Arrays.asList(0, 3, 0, 1, -3)));
    }

}