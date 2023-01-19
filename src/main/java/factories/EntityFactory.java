package factories;

import enums.OnCollision;
import javafx.scene.paint.Color;
import models.Asteroid;
import models.Entity;
import models.Planet;
import states.entity_states.*;

import java.util.ArrayList;
import java.util.List;

public class EntityFactory {
    private final double x;
    private final double y;
    private final double velocityX;
    private final double velocityY;
    private final int radius;
    private final Color color;
    private final List<OnCollision> onCollisions = new ArrayList<>();

    public EntityFactory(double x, double y, double velocityX, double velocityY, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.radius = radius;
        this.color = color;
    }

    public Asteroid createAsteroid() {
        var asteroid = new Asteroid(x, y, velocityX, velocityY, radius, color);
        setBehaviourRules(asteroid);
        return asteroid;
    }

    public Planet createPlanet(String name) {
        var planet = new Planet(name, x, y, velocityX, velocityY, radius, color);
        setBehaviourRules(planet);
        return planet;
    }

    public void addOnCollision(OnCollision onCollision) {
        onCollisions.add(onCollision);
    }

    private void setBehaviourRules(Entity entity) {
        for (var onCollision : onCollisions) {
            entity.addBehaviourRule(switch (onCollision) {
                case BLINK -> new BlinkBehaviourRule(entity);
                case BOUNCE -> new BounceBehaviourRule(entity);
                case DISAPPEAR -> new DisappearBehaviourRule(entity);
                case EXPLODE -> new ExplodeBehaviourRule(entity);
                case GROW -> new GrowBehaviourRule(entity);
            });
        }
    }
}
