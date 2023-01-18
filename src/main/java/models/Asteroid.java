package models;

import javafx.scene.paint.Color;

public class Asteroid extends Entity {

    public Asteroid(Galaxy galaxy, double x, double y, double velocityX, double velocityY, int radius, Color color) {
        super(galaxy, x, y, velocityX, velocityY, radius, color);
    }
}
