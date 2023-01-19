package commands;

import models.Galaxy;

import static main.FlatGalaxySociety.deltaMultiplier;

public class SlowDownCommand extends Command {
    public SlowDownCommand(Galaxy galaxy) {
        super(galaxy, "Slow down");
    }

    @Override
    public void execute() {
        deltaMultiplier -= 1;
    }
}
