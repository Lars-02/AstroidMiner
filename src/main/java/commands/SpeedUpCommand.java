package commands;

import models.Galaxy;

import static main.FlatGalaxySociety.deltaMultiplier;

public class SpeedUpCommand extends Command {
    public SpeedUpCommand(Galaxy galaxy) {
        super(galaxy, "Speed up");
    }

    @Override
    public void execute() {
        deltaMultiplier += 1;
    }
}
