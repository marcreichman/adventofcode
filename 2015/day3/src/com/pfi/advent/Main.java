import java.awt.*;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
	    Set<Point> visitedPoints = new HashSet<>();
        int cX = 0, cY = 0;
        try (Reader reader = Files.newBufferedReader(Paths.get(args[0]))) {
            char c;
            int nChar = 0;
            readLoop:
            while ((c = (char) reader.read()) != -1) {
                switch (c) {
                    case '^':
                        cY--;
                        break;
                    case 'v':
                        cY++;
                        break;
                    case '>':
                        cX++;
                        break;
                    case '<':
                        cX--;
                        break;
                    default:
                        break readLoop;
                }


                visitedPoints.add(new Point(cX, cY));

                System.out.println("read char " + nChar);
                nChar++;

            }
        }

        System.out.println(visitedPoints.size() + 1);

    }

}
