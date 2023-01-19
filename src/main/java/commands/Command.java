package commands;

import models.Galaxy;

public abstract class Command {
    public Galaxy galaxy;

    public final String name;

    Command(Galaxy galaxy, String name) {
        this.galaxy = galaxy;
        this.name = name;
    }

    public abstract void execute();
}
