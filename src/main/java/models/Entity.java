package models;

import javafx.scene.paint.Color;
import states.entity_states.EntityState;
import ui.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    private static final int RadiusScale = 5;

    Entity(Galaxy galaxy, double x, double y, double velocityX, double velocityY, int radius, Color color) {
        this.galaxy = galaxy;
        this.x = x;
        this.y = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.radius = radius;
        this.color = color;
        this.galaxy.addToGalaxy(this);
    }

    public double x;
    public double y;
    public Color color;

    private final int radius;
    private final Galaxy galaxy;
    public double velocityX;
    public double velocityY;
    private final List<Entity> colliders = new ArrayList<>();
    private EntityState state;

    public void translate(long delta, List<Entity> entities) {
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

        checkForEntityCollisions(entities);
    }

    public boolean isColliding() {
        return !colliders.isEmpty();
    }

    public void checkForEntityCollisions(List<Entity> entities) {
        for (Entity entity : entities) {
            if (!collidedWithCircle(entity))
                continue;
            if (colliders.stream().anyMatch(collider -> entity == collider)) continue;

            colliders.add(entity);
            state.onCollisionEntry();
        }
        for (Entity collider : colliders) {
            if (collidedWithCircle(collider))
                continue;

            colliders.remove(collider);
            state.onCollisionExit();
            return;
        }
    }

    public double distanceToEntity(Entity entity) {
        return Math.sqrt(Math.pow(x - entity.x, 2) + Math.pow(y - entity.y, 2));

    }

    public Galaxy getGalaxy() {
        return galaxy;
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

    public void removeFromGalaxy() {
        galaxy.removeFromGalaxy(this);
    }

    private boolean collidedWithCircle(Entity entity) {
        return distanceToEntity(entity) <= (getRadius() + entity.getRadius());
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
}
