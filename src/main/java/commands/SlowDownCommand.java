package commands;

import main.FlatGalaxySociety ;

import static main.FlatGalaxySociety.deltaMultiplier;

public class SlowDownCommand extends Command {
    public SlowDownCommand(FlatGalaxySociety game) {
        super(game, "Slow down");
    }

    @Override
    public void execute() {
        if (deltaMultiplier > 1)
            deltaMultiplier -= 1;
    }
}
