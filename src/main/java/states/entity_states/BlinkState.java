package states.entity_states;

import javafx.scene.paint.Color;
import models.Entity;
import models.Galaxy;

public class BlinkState extends EntityState {
    public BlinkState(Entity entity) {
        super(entity);
        initialColor = entity.color;
    }

    private final Color initialColor;

    @Override
    public void onCollisionEntry(Galaxy galaxy) {
        entity.color = initialColor.interpolate(Color.WHITE, 0.3);
    }

    public void onCollisionExit(Galaxy galaxy) {
        if (!entity.isColliding())
            entity.color = initialColor;
    }
}
