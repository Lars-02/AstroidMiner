package commands;

import main.FlatGalaxySociety ;

public class NextBookmarkCommand extends Command {
    public NextBookmarkCommand(FlatGalaxySociety game) {
        super(game, "Next Bookmark");
    }

    @Override
    public void execute() {
        game.galaxy.redo();
    }
}
