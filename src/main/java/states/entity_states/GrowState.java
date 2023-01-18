package states.entity_states;

import models.Entity;

public class GrowState extends EntityState {
    public GrowState(Entity entity) {
        super(entity);
    }

    @Override
    public void onCollisionEntry() {
        var radius = entity.getRadius();
        if (radius >= 20) {
            entity.setState(new ExplodeState(entity));
            return;
        }
        entity.setRadius(radius + 1);
    }

    @Override
    public void onCollisionExit() {

    }
}
