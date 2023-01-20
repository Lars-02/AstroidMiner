package states.collosionchecks;

import models.Entity;
import models.Galaxy;

import java.util.List;

public class NaiveCollisionChecker extends CollisionChecker {

    public NaiveCollisionChecker(Galaxy galaxy) {
        super(galaxy);
    }

    @Override
    public void checkCollisions() {
        // Check for collisions
        for (Entity entity : galaxy.getEntities()) {
            entity.checkForCollisionsOn(galaxy, galaxy.getEntities());
        }
    }
}
