package states.collosionchecks;

import models.Galaxy;

public abstract class CollisionChecker {

    protected final Galaxy galaxy;

    public final String name;

    CollisionChecker(String name, Galaxy galaxy) {
        this.name = name;
        this.galaxy = galaxy;
    }

    public abstract void checkCollisions();
}
