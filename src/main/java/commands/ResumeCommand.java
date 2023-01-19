package commands;

import main.FlatGalaxySociety;
import models.Galaxy;

public class ResumeCommand extends Command {
    public ResumeCommand(Galaxy galaxy) {
        super(galaxy, "Resume");
    }

    @Override
    public void execute() {
        FlatGalaxySociety.isPaused = false;
    }
}
