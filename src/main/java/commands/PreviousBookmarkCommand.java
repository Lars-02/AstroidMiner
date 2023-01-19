package commands;

import models.Galaxy;

public class PreviousBookmarkCommand extends Command {
    public PreviousBookmarkCommand(Galaxy galaxy) {
        super(galaxy, "Previous Bookmark");
    }

    @Override
    public void execute() {
        galaxy.undo();
    }
}
