package quadtree;

import models.Entity;
import models.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Quad {

    public static int TreeDepth = 6;
    public static int NodeCapacity = 4;

    public Quad(List<Entity> entities, Vector2d topLeftBoundary, Vector2d bottomRightBoundary) {
        this.entities = entities;
        this.topLeftBoundary = topLeftBoundary;
        this.bottomRightBoundary = bottomRightBoundary;
        subdivide();
    }

    private Quad(Vector2d topLeftBoundary, Vector2d bottomRightBoundary, int depth) {
        this.depth = depth;
        this.topLeftBoundary = topLeftBoundary;
        this.bottomRightBoundary = bottomRightBoundary;
    }

    public void addEntity(Entity entity) {
        // Check if the entity is within the boundaries of this quad
        if (!(entity.position.x + entity.radius >= topLeftBoundary.x) ||
                !(entity.position.x - entity.radius <= bottomRightBoundary.x) ||
                !(entity.position.y + entity.radius >= topLeftBoundary.y) ||
                !(entity.position.y - entity.radius <= bottomRightBoundary.y)) {
            return;
        }
        // If this quad has no subdivisions, add the entity to the entities list
        if (topLeft == null) {
            entities.add(entity);
            // If the entities list has reached the capacity, subdivide the quad
            if (entities.size() > NodeCapacity) {
                subdivide();
            }
            return;
        }
        // If this quad has subdivisions, add the entity to the appropriate quad
        topLeft.addEntity(entity);
        topRight.addEntity(entity);
        bottomLeft.addEntity(entity);
        bottomRight.addEntity(entity);

    }

    private List<Entity> entities = new ArrayList<>();
    public final Vector2d topLeftBoundary;
    public final Vector2d bottomRightBoundary;

    private Quad topLeft;
    private Quad topRight;
    private Quad bottomLeft;
    private Quad bottomRight;
    private int depth = 0;

    private void subdivide() {
        // Check if the current depth is equal to the maximum depth
        if (depth >= TreeDepth || entities.size() <= NodeCapacity) {
            return;
        }
        // Calculate the center point of the quad
        double centerX = (topLeftBoundary.x + bottomRightBoundary.x) / 2;
        double centerY = (topLeftBoundary.y + bottomRightBoundary.y) / 2;

        // Create the four subdivisions
        topLeft = new Quad(topLeftBoundary, new Vector2d(centerX, centerY), depth + 1);
        topRight = new Quad(new Vector2d(centerX, topLeftBoundary.y), new Vector2d(bottomRightBoundary.x, centerY), depth + 1);
        bottomLeft = new Quad(new Vector2d(topLeftBoundary.x, centerY), new Vector2d(centerX, bottomRightBoundary.y), depth + 1);
        bottomRight = new Quad(new Vector2d(centerX, centerY), bottomRightBoundary, depth + 1);

        // Move entities from the parent quad to the appropriate subdivisions
        for (Entity entity : entities) {
            topLeft.addEntity(entity);
            topRight.addEntity(entity);
            bottomLeft.addEntity(entity);
            bottomRight.addEntity(entity);
        }
        entities = new ArrayList<>();
    }

    public int getDepth() {
        return depth;
    }

    public Quad getTopLeft() {
        return topLeft;
    }

    public Quad getTopRight() {
        return topRight;
    }

    public Quad getBottomLeft() {
        return bottomLeft;
    }

    public Quad getBottomRight() {
        return bottomRight;
    }
}
