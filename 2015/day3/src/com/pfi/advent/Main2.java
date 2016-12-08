import java.awt.*;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Main2 {
    public static void main(String[] args) throws Exception {
	    Set<Point> oddPoints = new HashSet<>();
        oddPoints.add(new Point(0,0));

        Set<Point> evenPoints = new HashSet<>();
        evenPoints.add(new Point(0,0));

        int oddCX = 0, oddCY = 0, evenCX = 0, evenCY = 0;
        try (Reader reader = Files.newBufferedReader(Paths.get(args[0]))) {
            char c;
            int nChar = 1;
            readLoop:
            while ((c = (char) reader.read()) != -1) {
                boolean odd = (nChar % 2 != 0);
                switch (c) {
                    case '^':
                        if (odd) oddCY++;
                        else evenCY++;
                        break;
                    case 'v':
                        if (odd) oddCY--;
                        else evenCY--;
                        break;
                    case '>':
                        if (odd) oddCX++;
                        else evenCX++;
                        break;
                    case '<':
                        if (odd) oddCX--;
                        else evenCX--;
                        break;
                    default:
                        break readLoop;
                }

                if (odd) {
                    oddPoints.add(new Point(oddCX, oddCY));
                } else {
                    evenPoints.add(new Point(evenCX, evenCY));
                }

                nChar++;

            }
        }

        Set<Point> combinedSet = new HashSet<>(oddPoints);
        combinedSet.addAll(evenPoints);
        System.out.println(combinedSet.size());

    }

}
