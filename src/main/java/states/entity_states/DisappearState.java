package states.entity_states;

import models.Entity;

public class DisappearState extends EntityState {
    DisappearState(Entity entity) {
        super(entity);
    }

    @Override
    public void onCollision() {

    }
}
