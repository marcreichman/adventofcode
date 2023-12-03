package marcreichman.adventofcode;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AdventDayRunner {
    protected Path getInputFile() {
        return Paths.get("src", "main", "resources", "inputs", getDayString(), "input.txt");
    }

    protected abstract String getDayString();

    protected abstract void runSolutionPartOne() throws IOException;

    protected abstract void runSolutionPartTwo() throws IOException;

    protected static void runMain(AdventDayRunner instance, String[] args) throws IOException {
        instance.runSolutionPartOne();
        instance.runSolutionPartTwo();
    }
}
