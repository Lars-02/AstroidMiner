package ui.featuerenderers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import models.Galaxy;
import quadtree.Quad;
import states.collosionchecks.QuadCollisionChecker;

public class QuadTreeRenderer implements FeatureRenderer {
    private final Galaxy galaxy;

    public QuadTreeRenderer(Galaxy galaxy) {
        this.galaxy = galaxy;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (galaxy.collisionChecker instanceof QuadCollisionChecker quadCollision) {
            gc.setStroke(Color.GREEN);
            renderQuadtree(gc, quadCollision.quadtree);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
        }
    }

    private void renderQuadtree(GraphicsContext gc, Quad quadtree) {
        if (quadtree == null)
            return;

        gc.setLineWidth((double) 8 / (quadtree.getDepth() + 1));
        gc.strokeRect(quadtree.topLeftBoundary.x, quadtree.topLeftBoundary.y, quadtree.bottomRightBoundary.x - quadtree.topLeftBoundary.x, quadtree.bottomRightBoundary.y - quadtree.topLeftBoundary.y);

        renderQuadtree(gc, quadtree.getTopLeft());
        renderQuadtree(gc, quadtree.getTopRight());
        renderQuadtree(gc, quadtree.getBottomLeft());
        renderQuadtree(gc, quadtree.getBottomRight());
    }
}
