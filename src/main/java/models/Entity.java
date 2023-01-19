package models;

import javafx.scene.paint.Color;
import states.entity_states.BehaviourRule;
import ui.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    Entity(double x, double y, double velocityX, double velocityY, int radius, Color color) {
        this.position = new Vector2d(x, y);
        this.velocity = new Vector2d(velocityX, velocityY);
        this.radius = radius;
        this.color = color;
    }

    public Vector2d position;
    public Color color;

    private int radius;
    public Vector2d velocity;
    private final List<Entity> collidingEntities = new ArrayList<>();
    private final List<BehaviourRule> behaviourRules = new ArrayList<>();

    public void translate(long delta) {
        position = position.add(velocity.mul((double) delta / 1000));

        if (collidedWithLeft() || collidedWithRight()) {
            velocity.x *= -1;
            position.x = collidedWithLeft() ? getRadius() : Renderer.ScreenWidth - getRadius();
        }

        if (collidedWithTop() || collidedWithBottom()) {
            velocity.y *= -1;
            position.y = collidedWithTop() ? getRadius() : Renderer.ScreenHeight - getRadius();
        }
    }

    public boolean isColliding() {
        return !collidingEntities.isEmpty();
    }

    public void onCollision(Galaxy galaxy, Entity entity) {
        collidingEntities.add(entity);
        List.copyOf(behaviourRules).forEach(br -> br.onCollisionEntry(galaxy));
    }

    public void onExitCollision(Galaxy galaxy, Entity entity) {
        collidingEntities.remove(entity);
        List.copyOf(behaviourRules).forEach(br -> br.onCollisionExit(galaxy));
    }

    public int getRadius() {
        return radius;
    }

    public void addBehaviourRule(BehaviourRule state) {
        if (behaviourRules.contains(state))
            return;

        behaviourRules.add(state);
    }

    public void removeBehaviourRule(BehaviourRule state) {
        behaviourRules.remove(state);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    private boolean collidedWithLeft() {
        return position.x - getRadius() < 0;
    }

    private boolean collidedWithRight() {
        return position.x + getRadius() > Renderer.ScreenWidth;
    }

    private boolean collidedWithTop() {
        return position.y - getRadius() < 0;
    }

    private boolean collidedWithBottom() {
        return position.y + getRadius() > Renderer.ScreenHeight;
    }
}
