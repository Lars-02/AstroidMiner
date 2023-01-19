package states.behaviourrules;

import models.Entity;
import models.Galaxy;

import java.io.Serializable;

public abstract class BehaviourRule implements Serializable {
    protected Entity entity;

    BehaviourRule(Entity entity) {
        this.entity = entity;
    }

    public abstract void onCollisionEntry(Galaxy galaxy);
    public abstract void onCollisionExit(Galaxy galaxy);
}
