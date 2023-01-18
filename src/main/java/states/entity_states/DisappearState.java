package states.entity_states;

import models.Entity;

public class DisappearState extends EntityState {
    public DisappearState(Entity entity) {
        super(entity);
    }

    @Override
    public void onCollisionEntry() {
        entity.removeFromGalaxy();
    }

    @Override
    public void onCollisionExit() {

    }
}
