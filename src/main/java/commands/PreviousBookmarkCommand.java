package commands;

import main.FlatGalaxySociety ;

public class PreviousBookmarkCommand extends Command {
    public PreviousBookmarkCommand(FlatGalaxySociety game) {
        super(game, "Previous Bookmark");
    }

    @Override
    public void execute() {
        game.galaxy.undo();
    }
}
