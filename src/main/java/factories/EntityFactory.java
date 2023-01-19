package factories;

import enums.OnCollision;
import javafx.scene.paint.Color;
import models.Asteroid;
import models.Entity;
import models.Planet;
import states.entity_states.*;

public class EntityFactory {
    private final double x;
    private final double y;
    private final double velocityX;
    private final double velocityY;
    private final int radius;
    private final Color color;
    private final OnCollision onCollision;

    public EntityFactory(double x, double y, double velocityX, double velocityY, int radius, Color color, OnCollision onCollision) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.radius = radius;
        this.color = color;
        this.onCollision = onCollision;
    }

    public Asteroid createAsteroid() {
        var asteroid = new Asteroid(x, y, velocityX, velocityY, radius, color);
        setOnCollision(asteroid, onCollision);
        return asteroid;
    }

    public Planet createPlanet(String name) {
        var planet = new Planet(name, x, y, velocityX, velocityY, radius, color);
        setOnCollision(planet, onCollision);
        return planet;
    }

    private void setOnCollision(Entity entity, OnCollision onCollision) {
        entity.setState(switch (onCollision) {
            case BLINK -> new BlinkState(entity);
            case BOUNCE -> new BounceState(entity);
            case DISAPPEAR -> new DisappearState(entity);
            case EXPLODE -> new ExplodeState(entity);
            case GROW -> new GrowState(entity);
        });
    }
}
