package commands;

import models.Galaxy;

public class SlowDownCommand extends Command {
    public SlowDownCommand(Galaxy galaxy) {
        super(galaxy, "Slow down");
    }

    @Override
    public void execute() {

    }
}
