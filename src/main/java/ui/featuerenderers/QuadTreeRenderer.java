package ui.featuerenderers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.FlatGalaxySociety;
import quadtree.Quad;
import states.collosionchecks.QuadCollisionChecker;

public class QuadTreeRenderer implements FeatureRenderer {
    private final FlatGalaxySociety game;

    public QuadTreeRenderer(FlatGalaxySociety game) {
        this.game = game;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (game.galaxy.collisionChecker instanceof QuadCollisionChecker quadCollision) {
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
