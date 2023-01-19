package states.collosionchecks;

import models.Galaxy;
import models.Entity;

public class NaiveCollisionChecker extends CollisionChecker {

    public NaiveCollisionChecker(Galaxy galaxy) {
        super(galaxy);
    }

    @Override
    public void checkCollisions() {
        // Check for collisions
        for (var entity : galaxy.getEntities()) {
            for (var otherEntity : galaxy.getEntities()) {
                if (otherEntity == entity)
                    continue;

                if (!entity.collidedWithEntity(otherEntity))
                    continue;

                if (galaxy.collisions.get(entity).contains(otherEntity))
                    continue;

                galaxy.collisions.get(entity).add(otherEntity);
                entity.onCollision(galaxy, otherEntity);
            }
        }

        // Check for exit collisions
        galaxy.collisions.forEach((entity, collidingEntities) -> {
            for (var otherEntity : collidingEntities) {
                if (entity.collidedWithEntity(otherEntity))
                    continue;

                entity.onExitCollision(galaxy, otherEntity);
                galaxy.collisions.get(entity).remove(otherEntity);
                return;
            }
        });
    }
}
