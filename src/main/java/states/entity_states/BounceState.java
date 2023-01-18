package states.entity_states;

import models.Entity;

public class BounceState extends EntityState {
    public BounceState(Entity entity) {
        super(entity);
    }

    private int bounces = 0;

    @Override
    public void onCollisionEntry() {
        bounces++;
        entity.velocityX *= -1;
        entity.velocityY *= -1;
    }

    @Override
    public void onCollisionExit() {
        if (bounces >= 5)
            entity.setState(new BlinkState(entity));
    }
}
