package commands;

import models.Galaxy;

public class RevertCommand extends Command {
    public RevertCommand(Galaxy galaxy) {
        super(galaxy, "Revert");
    }

    @Override
    public void execute() {

    }
}
