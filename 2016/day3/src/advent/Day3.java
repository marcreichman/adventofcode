package advent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: mreichman
 * Date: 12/7/2016
 * Time: 9:09 AM
 */
public class Day3 {
    private static class Triangle {
        private int s1, s2, s3;

        public Triangle(int s1, int s2, int s3) {
            this.s1 = s1;
            this.s2 = s2;
            this.s3 = s3;
        }

        public boolean isValid() {
            return (
                    ((s1 + s2) > s3) &&
                            ((s1 + s3) > s2) &&
                            ((s2 + s3) > s1)
            );
        }
    }

    public static void main(String... args) throws Exception {
        Long step1Count = Files
                .lines(Paths.get("input.txt"))
                .map(s -> {
                    int[] splits = Arrays.stream(s.replaceAll(" ", ",").split(",")).filter(sp -> !sp.isEmpty()).mapToInt(Integer::parseInt).toArray();
                    return new Triangle(splits[0], splits[1], splits[2]);
                })
                .filter(Triangle::isValid)
                .count();
        System.out.println("Step 1 Count: " + step1Count);

        List<List<Integer>> lineInts = Files
                .lines(Paths.get("input.txt"))
                .map(s ->
                        Arrays.stream(s.replaceAll(" ", ",").split(",")).filter(sp -> !sp.isEmpty()).map(Integer::parseInt).map(Integer::new).collect(Collectors.toList())
                )
                .collect(Collectors.toList());

        List<Triangle> step2Triangles = new ArrayList<>();
        for (int l = 0; l < lineInts.size(); l += 3) {
            List<Integer> l1 = lineInts.get(l), l2 = lineInts.get(l+1), l3 = lineInts.get(l+2);
            step2Triangles.add(new Triangle(l1.get(0), l2.get(0), l3.get(0)));
            step2Triangles.add(new Triangle(l1.get(1), l2.get(1), l3.get(1)));
            step2Triangles.add(new Triangle(l1.get(2), l2.get(2), l3.get(2)));
        }
        Long step2Count = step2Triangles.stream().filter(Triangle::isValid).count();
        System.out.println("Step 2 Count: " + step2Count);
    }
}
