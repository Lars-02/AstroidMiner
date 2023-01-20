package states.collosionchecks;

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
    }
}
