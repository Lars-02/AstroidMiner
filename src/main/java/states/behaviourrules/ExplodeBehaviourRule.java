package states.behaviourrules;

import models.Color;
import models.Asteroid;
import models.Entity;
import models.Galaxy;

import java.util.Random;

public class ExplodeBehaviourRule extends BehaviourRule {
    public ExplodeBehaviourRule(Entity entity) {
        super(entity);
    }

    @Override
    public void onCollisionEntry(Galaxy galaxy) {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            var asteroid = new Asteroid(
                    entity.position.x,
                    entity.position.y,
                    random.nextInt(-5, 5),
                    random.nextInt(-5, 5),
                    random.nextInt(5) + 1,
                    Color.BLACK
            );

            asteroid.addBehaviourRule(new BounceBehaviourRule(asteroid));
            galaxy.addEntity(asteroid);
        }
        entity.removeBehaviourRule(this);
        galaxy.removeEntity(entity);
    }

    @Override
    public void onCollisionExit(Galaxy galaxy) {

    }
}
