package commands;

import models.Galaxy;

public class PauseCommand extends Command {
    public PauseCommand(Galaxy galaxy) {
        super(galaxy, "Pause");
    }

    @Override
    public void execute() {

    }
}
