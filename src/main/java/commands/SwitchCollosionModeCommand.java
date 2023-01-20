package commands;

import models.Galaxy;
import states.collosionchecks.NaiveCollisionChecker;
import states.collosionchecks.QuadCollisionChecker;

public class SwitchCollosionModeCommand extends Command {
    public SwitchCollosionModeCommand(Galaxy galaxy) {
        super(galaxy, "Collision Mode");
    }

    @Override
    public void execute() {
        if (galaxy.collisionChecker instanceof NaiveCollisionChecker) {
            galaxy.collisionChecker = new QuadCollisionChecker(galaxy);
            return;
        }
        galaxy.collisionChecker = new NaiveCollisionChecker(galaxy);
    }
}
