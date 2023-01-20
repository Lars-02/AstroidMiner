package commands;

import main.FlatGalaxySociety ;

import static main.FlatGalaxySociety.deltaMultiplier;

public class SpeedUpCommand extends Command {
    public SpeedUpCommand(FlatGalaxySociety game) {
        super(game, "Speed up");
    }

    @Override
    public void execute() {
        if (deltaMultiplier < 1000)
            deltaMultiplier += 1;
    }
}
