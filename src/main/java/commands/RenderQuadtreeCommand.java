package commands;

import main.FlatGalaxySociety ;
import ui.scenerenderers.GameRenderer;

public class RenderQuadtreeCommand extends Command {
    private final GameRenderer galaxyRenderer;

    public RenderQuadtreeCommand(FlatGalaxySociety game, GameRenderer galaxyRenderer) {
        super(game, "Render Quadtree");
        this.galaxyRenderer = galaxyRenderer;
    }

    @Override
    public void execute() {
        galaxyRenderer.shouldRenderQuadtree = !galaxyRenderer.shouldRenderQuadtree;
    }
}
