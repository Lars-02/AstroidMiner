package commands;

import models.Galaxy;

import static ui.Renderer.RenderQuadtree;

public class RenderQuadtreeCommand extends Command {
    public RenderQuadtreeCommand(Galaxy galaxy) {
        super(galaxy, "Render Quadtree");
    }

    @Override
    public void execute() {
        RenderQuadtree = !RenderQuadtree;
    }
}
