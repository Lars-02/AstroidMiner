package commands;

import models.Galaxy;

public class NextBookmarkCommand extends Command {
    public NextBookmarkCommand(Galaxy galaxy) {
        super(galaxy, "Next Bookmark");
    }

    @Override
    public void execute() {
        galaxy.redo();
    }
}
