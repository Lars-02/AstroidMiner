package commands;

import main.FlatGalaxySociety ;
import states.collosionchecks.NaiveCollisionChecker;
import states.collosionchecks.QuadCollisionChecker;

public class SwitchCollosionModeCommand extends Command {
    public SwitchCollosionModeCommand(FlatGalaxySociety game) {
        super(game, "Collision Mode");
    }

    @Override
    public void execute() {
        if (game.galaxy.collisionChecker instanceof NaiveCollisionChecker) {
            game.galaxy.collisionChecker = new QuadCollisionChecker(game.galaxy);
            return;
        }
        game.galaxy.collisionChecker = new NaiveCollisionChecker(game.galaxy);
    }
}
