package states.collosionchecks;

import models.Galaxy;

public abstract class CollisionChecker {

    protected final Galaxy galaxy;

    CollisionChecker(Galaxy galaxy) {
        this.galaxy = galaxy;
    }

    public abstract void checkCollisions();
}
