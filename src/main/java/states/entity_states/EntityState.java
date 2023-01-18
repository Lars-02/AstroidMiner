package states.entity_states;

import models.Entity;

public abstract class EntityState {
    Entity entity;

    EntityState(Entity entity) {
        this.entity = entity;
    }

    public abstract void onCollisionEntry();
    public abstract void onCollisionExit();
}
