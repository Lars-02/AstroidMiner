package states.entity_states;

import models.Entity;
import models.Galaxy;

public class GrowState extends EntityState {
    public GrowState(Entity entity) {
        super(entity);
    }

    @Override
    public void onCollisionEntry(Galaxy galaxy) {
        var radius = entity.getRadius();
        if (radius >= 20) {
            entity.setState(new ExplodeState(entity));
            return;
        }
        entity.setRadius(radius + 1);
    }

    @Override
    public void onCollisionExit(Galaxy galaxy) {

    }
}
