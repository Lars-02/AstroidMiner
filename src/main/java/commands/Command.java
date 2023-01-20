package commands;

import models.Galaxy;

public abstract class Command {
    public final String name;
    public Galaxy galaxy;

    Command(Galaxy galaxy, String name) {
        this.galaxy = galaxy;
        this.name = name;
    }

    public abstract void execute();
}
