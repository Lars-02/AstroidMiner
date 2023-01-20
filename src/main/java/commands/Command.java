package commands;

import main.FlatGalaxySociety ;

public abstract class Command {
    public final String name;
    public FlatGalaxySociety game;

    Command(FlatGalaxySociety game, String name) {
        this.game = game;
        this.name = name;
    }

    public abstract void execute();
}
