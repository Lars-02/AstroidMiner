package states.entity_states;

import javafx.scene.paint.Color;
import models.Entity;
import models.Galaxy;

public class BlinkBehaviourRule extends BehaviourRule {
    public BlinkBehaviourRule(Entity entity) {
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
