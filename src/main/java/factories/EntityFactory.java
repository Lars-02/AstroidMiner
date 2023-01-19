package factories;

import javafx.scene.paint.Color;
import models.Asteroid;
import models.Entity;
import models.Galaxy;
import models.Planet;

public class EntityFactory {

    private final Galaxy galaxy;
    private final double x;
    private final double y;
    private final double velocityX;
    private final double velocityY;
    private final int radius;
    private final Color color;

    public EntityFactory(Galaxy galaxy, double x, double y, double velocityX, double velocityY, int radius, Color color) {
        this.galaxy = galaxy;
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.radius = radius;
        this.color = color;
    }

    public Asteroid createAsteroid() {
        final var entity = new Asteroid(galaxy, x, y, velocityX, velocityY, radius, color);
        galaxy.addEntity(entity);
        return entity;
    }

    public Planet createPlanet(String name) {
        final var entity = new Planet(galaxy, name, x, y, velocityX, velocityY, radius, color);
        galaxy.addEntity(entity);
        return entity;
    }
}
