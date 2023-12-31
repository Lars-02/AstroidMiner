package states.collosionchecks;

import models.Entity;
import models.Galaxy;

public class NaiveCollisionChecker extends CollisionChecker {

    public NaiveCollisionChecker(Galaxy galaxy) {
        super("Naive", galaxy);
    }

    @Override
    public void checkCollisions() {
        // Check for collisions
        for (Entity entity : galaxy.getEntities()) {
            entity.checkForCollisionsOn(galaxy, galaxy.getEntities());
        }
    }
}
