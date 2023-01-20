package states.collosionchecks;

import models.Galaxy;

public abstract class CollisionChecker {

    public final String name;
    protected final Galaxy galaxy;

    CollisionChecker(String name, Galaxy galaxy) {
        this.name = name;
        this.galaxy = galaxy;
    }

    public abstract void checkCollisions();
}
