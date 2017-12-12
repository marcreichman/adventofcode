import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/12/2017
 * Time: 8:40 AM
 */
public class Day2Test {
    @Test
    public void testPart1() throws Exception {
        assertEquals(18, Day2.part1CheckSum(Paths.get("part1-testdata.txt")));
    }

    @Test
    public void testPart2() throws Exception {
        assertEquals(9, Day2.part2CheckSum(Paths.get("part2-testdata.txt")));
    }
}