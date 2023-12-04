package marcreichman.adventofcode;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AdventDayRunner {
    protected Path getInputFile() {
        return getResourceFile("input.txt");
    }

    protected Path getTestInputFile() {
        return getResourceFile("testinput.txt");
    }

    private Path getResourceFile(String filename) {
        return Paths.get("src", "main", "resources", "inputs", getDayString(), filename);
    }

    protected abstract String getDayString();

    protected abstract void runSolutionPartOne() throws IOException;

    protected abstract void runSolutionPartTwo() throws IOException;

    protected static void runMain(AdventDayRunner instance, String[] args) throws IOException {
        instance.runSolutionPartOne();
        instance.runSolutionPartTwo();
    }
}
