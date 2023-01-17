package commands;

import models.Galaxy;

public abstract class Command {
    public Galaxy galaxy;

    Command(Galaxy galaxy) {
        this.galaxy = galaxy;
    }

    public abstract void execute();
}
