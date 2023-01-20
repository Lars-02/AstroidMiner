package states.collosionchecks;

import models.Entity;
import models.Galaxy;
import models.Vector2d;
import quadtree.Quad;
import ui.Renderer;

public class QuadCollisionChecker extends CollisionChecker {

    public Quad quadtree;

    public QuadCollisionChecker(Galaxy galaxy) {
        super("Quadtree", galaxy);
    }

    @Override
    public void checkCollisions() {
        quadtree = new Quad(galaxy.getEntities(), new Vector2d(0, 0), new Vector2d(Renderer.ScreenWidth, Renderer.ScreenHeight));
        checkCollisionsInQuad(quadtree);
    }

    private void checkCollisionsInQuad(Quad quadtree) {
        if (quadtree == null)
            return;

        for (Entity entity : quadtree.getEntities()) {
            entity.checkForCollisionsOn(galaxy, quadtree.getEntities());
        }

        checkCollisionsInQuad(quadtree.getTopLeft());
        checkCollisionsInQuad(quadtree.getTopRight());
        checkCollisionsInQuad(quadtree.getBottomLeft());
        checkCollisionsInQuad(quadtree.getBottomRight());
    }
}
