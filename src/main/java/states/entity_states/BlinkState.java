package states.entity_states;

import models.Entity;

public class BlinkState extends EntityState {
    public BlinkState(Entity entity) {
        super(entity);
    }

    @Override
    public void onCollision() {
        System.out.println("blink");
    }
}
