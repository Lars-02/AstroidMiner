package commands;

import main.FlatGalaxySociety;
import models.priority.Priority;
import ui.featuerenderers.QuadTreeRenderer;
import ui.scenerenderers.GameRenderer;

public class RenderQuadtreeCommand extends Command {
    private final GameRenderer galaxyRenderer;

    public RenderQuadtreeCommand(FlatGalaxySociety game, GameRenderer galaxyRenderer) {
        super(game, "Render Quadtree");
        this.galaxyRenderer = galaxyRenderer;
    }

    @Override
    public void execute() {
        var existingQuadTreeRenderer = galaxyRenderer.featureRenderers.findAny(featureRenderer -> featureRenderer instanceof QuadTreeRenderer);

        if (existingQuadTreeRenderer.isPresent())
            galaxyRenderer.featureRenderers.remove(existingQuadTreeRenderer.get());
        else
            galaxyRenderer.featureRenderers.add(new QuadTreeRenderer(game.galaxy), Priority.LOWEST);
    }
}
