package models;

import javafx.scene.paint.Color;
import states.entity_states.EntityState;

public class Entity {

    Entity(double x, double y, double velocityX, double velocityY, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.radius = radius;
        this.color = color;
    }

    public double x;
    public double y;

    private final double velocityX;
    private final double velocityY;
    private final int radius;
    private final Color color;
    private EntityState state;

    public void onCollision() {
        state.onCollision();
    }

    public int getRadius() {
        return radius;
    }

    public Color getColor() {
        return color;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setState(EntityState state) {
        this.state = state;
    }
}
