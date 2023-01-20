package commands;

import main.FlatGalaxySociety ;

public class TogglePauseCommand extends Command {
    public TogglePauseCommand(FlatGalaxySociety game) {
        super(game, "Toggle Pause");
    }

    @Override
    public void execute() {
        game.galaxy.isPaused = !game.galaxy.isPaused;
    }
}
