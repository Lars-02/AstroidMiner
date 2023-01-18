package models;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import states.entity_states.EntityState;

import java.util.List;

public class Entity {
    private static final int RadiusScale = 5;

    Entity(double x, double y, double velocityX, double velocityY, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.radius = radius;
        this.shape = new Circle(getRadius(), color);
        this.shape.relocate(x, y);
    }

    public double x;
    public double y;

    private final double velocityX;
    private final double velocityY;
    private final int radius;
    private EntityState state;
    private final Circle shape;

    public void tick(long delta, List<Entity> entities) {
        x += velocityX * ((double) delta / 1000);
        y += velocityY * ((double) delta / 1000);
        shape.setTranslateX(x);
        shape.setTranslateY(y);

        for (Entity entity : entities) {
            if (insideCircle(entity.x, entity.y, entity.getRadius()))
                onCollision();
        }
    }

    private boolean insideCircle(double circleX, double circleY, double radius) {
        double diameter = Math.sqrt((x - circleX) * (x - circleX) + (y - circleY) * (y - circleY));
        return diameter <= radius - getRadius();
    }

    public void onCollision() {
        state.onCollision();
    }

    public int getRadius() {
        return radius * RadiusScale;
    }

    public Circle getShape() {
        return shape;
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
