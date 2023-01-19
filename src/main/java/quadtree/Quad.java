package quadtree;

import models.Entity;
import models.Vector2d;

import java.util.List;

public class Quad {

    public static int TreeDepth = 4;
    public static int NodeCapacity = 4;

    public Quad(List<Entity> entities, Vector2d topLeftBoundary, Vector2d bottomRightBoundary) {
        this.entities = entities;
        this.topLeftBoundary = topLeftBoundary;
        this.bottomRightBoundary = bottomRightBoundary;
        subdivide();
    }

    private Quad(Vector2d topLeftBoundary, Vector2d bottomRightBoundary) {
        this.topLeftBoundary = topLeftBoundary;
        this.bottomRightBoundary = bottomRightBoundary;
    }

    public boolean addEntity(Entity entity) {
        // Check if the entity is within the boundaries of this quad
        if (!(entity.position.x >= topLeftBoundary.x) || !(entity.position.x <= bottomRightBoundary.x) ||
                !(entity.position.y >= topLeftBoundary.y) || !(entity.position.y <= bottomRightBoundary.y)) {
            return false;
        }
        // If this quad has no subdivisions, add the entity to the entities list
        if (topLeft == null) {
            entities.add(entity);
            // If the entities list has reached the capacity, subdivide the quad
            if (entities.size() > NodeCapacity) {
                subdivide();
            }
            return true;
        }
        // If this quad has subdivisions, add the entity to the appropriate quad
        topLeft.addEntity(entity);
        topRight.addEntity(entity);
        bottomLeft.addEntity(entity);
        bottomRight.addEntity(entity);

        return true;
    }

    private List<Entity> entities;
    private final Vector2d topLeftBoundary;
    private final Vector2d bottomRightBoundary;

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
        topLeft = new Quad(topLeftBoundary, new Vector2d(centerX, centerY));
        topRight = new Quad(new Vector2d(centerX, topLeftBoundary.y), new Vector2d(bottomRightBoundary.x, centerY));
        bottomLeft = new Quad(new Vector2d(topLeftBoundary.x, centerY), new Vector2d(centerX, bottomRightBoundary.y));
        bottomRight = new Quad(new Vector2d(centerX, centerY), bottomRightBoundary);

        // Move entities from the parent quad to the appropriate subdivisions
        for (Entity entity : entities) {
            if (topLeft.addEntity(entity)) {
                entities.remove(entity);
            }
            if (topRight.addEntity(entity)) {
                entities.remove(entity);
            }
            if (bottomLeft.addEntity(entity)) {
                entities.remove(entity);
            }
            if (bottomRight.addEntity(entity)) {
                entities.remove(entity);
            }
        }

        // Increase the depth of the subdivisions
        topLeft.depth = depth + 1;
        topRight.depth = depth + 1;
        bottomLeft.depth = depth + 1;
        bottomRight.depth = depth + 1;
    }
}
