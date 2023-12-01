package marcreichman.adventofcode;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AdventDayRunner {
    protected Path getInputFile() {
        return Paths.get("src", "main", "resources", "inputs", getDayString(), "input.txt");
    }

    protected abstract String getDayString();
}
