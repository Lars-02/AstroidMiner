package states.entity_states;

import javafx.scene.paint.Color;
import models.Asteroid;
import models.Entity;
import models.Galaxy;

import java.util.Random;

public class ExplodeState extends EntityState {
    public ExplodeState(Entity entity) {
        super(entity);
    }

    @Override
    public void onCollisionEntry(Galaxy galaxy) {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            var asteroid = new Asteroid(
                    entity.position.x,
                    entity.position.y,
                    random.nextInt(20) - 10,
                    random.nextInt(20) - 10,
                    random.nextInt(5) + 1,
                    Color.BLACK
            );

            asteroid.setState(new BounceState(asteroid));
            galaxy.addEntity(asteroid);
        }
        galaxy.removeEntity(entity);
    }

    @Override
    public void onCollisionExit(Galaxy galaxy) {

    }
}
