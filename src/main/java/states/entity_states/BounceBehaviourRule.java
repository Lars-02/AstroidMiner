package states.entity_states;

import models.Entity;
import models.Galaxy;

public class BounceBehaviourRule extends BehaviourRule {
    public BounceBehaviourRule(Entity entity) {
        super(entity);
    }

    private int bounces = 0;

    @Override
    public void onCollisionEntry(Galaxy galaxy) {
        bounces++;
        entity.velocity = entity.velocity.mul(-1);
    }

    @Override
    public void onCollisionExit(Galaxy galaxy) {
        if (bounces < 5)
            return;

        entity.removeBehaviourRule(this);
        entity.addBehaviourRule(new BlinkBehaviourRule(entity));
    }
}
