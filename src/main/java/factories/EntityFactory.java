package factories;

import javafx.scene.paint.Color;
import models.Asteroid;
import models.Planet;

public class EntityFactory {
    private final double x;
    private final double y;
    private final double velocityX;
    private final double velocityY;
    private final int radius;
    private final Color color;

    public EntityFactory(double x, double y, double velocityX, double velocityY, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.radius = radius;
        this.color = color;
    }

    public Asteroid createAsteroid() {
        return new Asteroid(x, y, velocityX, velocityY, radius, color);
    }

    public Planet createPlanet(String name) {
        return new Planet(name, x, y, velocityX, velocityY, radius, color);
    }
}
