package states.behaviourrules;

import models.Entity;
import models.Galaxy;

public abstract class BehaviourRule {
    protected Entity entity;

    BehaviourRule(Entity entity) {
        this.entity = entity;
    }

    public abstract void onCollisionEntry(Galaxy galaxy);
    public abstract void onCollisionExit(Galaxy galaxy);
}
