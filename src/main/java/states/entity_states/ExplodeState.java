package states.entity_states;

import javafx.scene.paint.Color;
import models.Asteroid;
import models.Entity;

import java.util.Random;

public class ExplodeState extends EntityState {
    public ExplodeState(Entity entity) {
        super(entity);
    }

    @Override
    public void onCollisionEntry() {
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            var asteroid = new Asteroid(entity.getGalaxy(), entity.x, entity.y, random.nextInt(20) - 10, random.nextInt(20) - 10, random.nextInt(3) + 1, Color.BLACK);
            asteroid.setState(new BounceState(asteroid));
            entity.getGalaxy().addEntity(asteroid);
        }
        entity.removeFromGalaxy();
    }

    @Override
    public void onCollisionExit() {

    }
}
