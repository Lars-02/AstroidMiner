package commands;

import models.Galaxy;

import static main.FlatGalaxySociety.isPaused;

public class TogglePauseCommand extends Command {
    public TogglePauseCommand(Galaxy galaxy) {
        super(galaxy, "Toggle Pause");
    }

    @Override
    public void execute() {
        isPaused = !isPaused;
    }
}
