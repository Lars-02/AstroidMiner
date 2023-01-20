package commands;

import main.FlatGalaxySociety ;

public class PauseCommand extends Command {
    public PauseCommand(FlatGalaxySociety game) {
        super(game, "Pause");
    }

    @Override
    public void execute() {
        game.galaxy.isPaused = true;
    }
}
