package factories;

import models.Entity;
import models.Galaxy;

public class GalaxyBuilder {
    private final Galaxy galaxy;

    public GalaxyBuilder() {
        this.galaxy = new Galaxy();
    }

    public void addEntity(Entity entity) {
        galaxy.addEntity(entity);
    }

    public Galaxy getResult() {
        return this.galaxy;
    }
}
