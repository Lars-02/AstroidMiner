package states.entity_states;

import models.Entity;
import models.Galaxy;

public class BounceState extends EntityState {
    public BounceState(Entity entity) {
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
        if (bounces >= 5)
            entity.setState(new BlinkState(entity));
    }
}
