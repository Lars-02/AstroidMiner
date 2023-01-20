package commands;

import models.Galaxy;

import static main.FlatGalaxySociety.isPaused;

public class PauseCommand extends Command {
    public PauseCommand(Galaxy galaxy) {
        super(galaxy, "Pause");
    }

    @Override
    public void execute() {
        isPaused = true;
    }
}
