package states.entity_states;

import models.Entity;
import models.Galaxy;

public class DisappearBehaviourRule extends BehaviourRule {
    public DisappearBehaviourRule(Entity entity) {
        super(entity);
    }

    @Override
    public void onCollisionEntry(Galaxy galaxy) {
        galaxy.removeEntity(entity);
    }

    @Override
    public void onCollisionExit(Galaxy galaxy) {

    }
}
