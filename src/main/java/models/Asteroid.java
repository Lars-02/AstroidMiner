package models;

import javafx.scene.paint.Color;
import states.entity_states.EntityState;

public class Asteroid extends Entity {

    public Asteroid(double x, double y, double velocityX, double velocityY, int radius, Color color) {
        super(x, y, velocityX, velocityY, radius, color);
    }

}
