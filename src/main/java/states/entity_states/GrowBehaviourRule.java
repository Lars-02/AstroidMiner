package states.entity_states;

import models.Entity;
import models.Galaxy;

public class GrowBehaviourRule extends BehaviourRule {
    public GrowBehaviourRule(Entity entity) {
        super(entity);
    }

    @Override
    public void onCollisionEntry(Galaxy galaxy) {
        var radius = entity.getRadius();
        if (radius >= 20) {
            entity.removeBehaviourRule(this);
            entity.addBehaviourRule(new ExplodeBehaviourRule(entity));
            return;
        }
        entity.setRadius(radius + 1);
    }

    @Override
    public void onCollisionExit(Galaxy galaxy) {

    }
}
