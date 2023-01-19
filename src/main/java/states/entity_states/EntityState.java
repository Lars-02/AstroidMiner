package states.entity_states;

import models.Entity;
import models.Galaxy;

public abstract class EntityState {
    Entity entity;

    EntityState(Entity entity) {
        this.entity = entity;
    }

    public abstract void onCollisionEntry(Galaxy galaxy);
    public abstract void onCollisionExit(Galaxy galaxy);
}
