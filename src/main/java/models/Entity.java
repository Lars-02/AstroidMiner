package models;

import javafx.scene.paint.Color;
import states.entity_states.EntityState;
import ui.Renderer;

import java.util.List;

public class Entity {
    private static final int RadiusScale = 5;

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
    private final int radius;

    private double velocityX;
    private double velocityY;
    public final Color color;
    private EntityState state;

    public void tick(long delta, List<Entity> entities) {
        x += velocityX * ((double) delta / 1000);
        y += velocityY * ((double) delta / 1000);

        if (collidedWithLeft() || collidedWithRight()) {
            velocityX *= -1;
            x = collidedWithLeft() ? getRadius() : Renderer.ScreenWidth - getRadius();
        }


        if (collidedWithTop() || collidedWithBottom()) {
            velocityY *= -1;
            y = collidedWithTop() ? getRadius() : Renderer.ScreenHeight - getRadius();
        }

        for (Entity entity : entities) {
            if (collidedWithCircle(entity.x, entity.y, entity.getRadius())) {
                collidedWithCircle(entity.x, entity.y, entity.getRadius());
                onCollision();
            }
        }
    }

    private boolean collidedWithCircle(double circleX, double circleY, double radius) {
        double distance = Math.sqrt(Math.pow(x - circleX, 2) + Math.pow(y - circleY, 2));
        return distance <= (getRadius() + radius);
    }

    private boolean collidedWithLeft() {
        return x - getRadius() < 0;
    }

    private boolean collidedWithRight() {
        return x + getRadius() > Renderer.ScreenWidth;
    }

    private boolean collidedWithTop() {
        return y - getRadius() < 0;
    }

    private boolean collidedWithBottom() {
        return y + getRadius() > Renderer.ScreenHeight;
    }

    public void onCollision() {
        state.onCollision();
    }

    public int getRadius() {
        return radius * RadiusScale;
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
