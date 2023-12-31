package states.behaviourrules;

import models.Color;
import models.Entity;
import models.Galaxy;

public class BlinkBehaviourRule extends BehaviourRule {
    private final Color initialColor;

    public BlinkBehaviourRule(Entity entity) {
        super(entity);
        initialColor = entity.color;
    }

    @Override
    public void onCollisionEntry(Galaxy galaxy) {
        entity.color = initialColor.interpolate(Color.WHITE, 0.3);
    }

    public void onCollisionExit(Galaxy galaxy) {
        if (!entity.isColliding())
            entity.color = initialColor;
    }
}
