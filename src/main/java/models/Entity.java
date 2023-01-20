package models;

import states.behaviourrules.BehaviourRule;
import ui.model.Config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Serializable {
    private final List<Entity> collidingEntities = new ArrayList<>();
    private final List<BehaviourRule> behaviourRules = new ArrayList<>();
    public Vector2d position;
    public Color color;
    public Vector2d velocity;
    public int radius;

    protected Entity(double x, double y, double velocityX, double velocityY, int radius, Color color) {
        this.position = new Vector2d(x, y);
        this.velocity = new Vector2d(velocityX, velocityY);
        this.radius = radius;
        this.color = color;
    }

    public void translate(long delta) {
        position = position.add(velocity.mul((double) delta / 1000));

        if (collidedWithLeft() || collidedWithRight()) {
            velocity.x *= -1;
            position.x = collidedWithLeft() ? radius : Config.SCREEN_WIDTH - radius;
        }

        if (collidedWithTop() || collidedWithBottom()) {
            velocity.y *= -1;
            position.y = collidedWithTop() ? radius : Config.SCREEN_HEIGHT - radius;
        }
    }

    public boolean collidedWith(Entity entity) {
        return collidingEntities.contains(entity);
    }

    public boolean isColliding() {
        return !collidingEntities.isEmpty();
    }

    public void checkForCollisionsOn(Galaxy galaxy, List<Entity> entities) {
        for (var entity : entities) {
            if (entity == this)
                continue;

            if (!isCollidingWith(entity))
                continue;

            if (collidedWith(entity))
                continue;

            collidingEntities.add(entity);
            List.copyOf(behaviourRules).forEach(br -> br.onCollisionEntry(galaxy));
        }

        // Check for exit collisions
        for (Entity collider : List.copyOf(collidingEntities)) {
            if (isCollidingWith(collider) && galaxy.getEntities().stream().anyMatch(entity -> entity == collider))
                continue;

            collidingEntities.remove(collider);
            List.copyOf(behaviourRules).forEach(br -> br.onCollisionExit(galaxy));
        }
    }

    public void addBehaviourRule(BehaviourRule state) {
        if (behaviourRules.contains(state))
            return;

        behaviourRules.add(state);
    }

    public void removeBehaviourRule(BehaviourRule state) {
        behaviourRules.remove(state);
    }

    private boolean collidedWithLeft() {
        return position.x - radius < 0;
    }

    private boolean collidedWithRight() {
        return position.x + radius > Config.SCREEN_WIDTH;
    }

    private boolean collidedWithTop() {
        return position.y - radius < 0;
    }

    private boolean collidedWithBottom() {
        return position.y + radius > Config.SCREEN_HEIGHT;
    }

    public boolean isCollidingWith(Entity otherEntity) {
        return position.dist(otherEntity.position) <= (radius + otherEntity.radius);
    }
}
