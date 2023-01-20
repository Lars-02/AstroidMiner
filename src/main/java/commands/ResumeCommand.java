package commands;

import main.FlatGalaxySociety ;

public class ResumeCommand extends Command {
    public ResumeCommand(FlatGalaxySociety game) {
        super(game, "Resume");
    }

    @Override
    public void execute() {
        game.galaxy.isPaused = false;
    }
}
