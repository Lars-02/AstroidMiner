package commands;

import models.Galaxy;
import main.FlatGalaxySociety;

public class PauseCommand extends Command {
    public PauseCommand(Galaxy galaxy) {
        super(galaxy, "Pause");
    }

    @Override
    public void execute() {
        FlatGalaxySociety.isPaused = true;
    }
}
