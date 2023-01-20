package commands;

import main.FlatGalaxySociety;
import models.priority.Priority;
import ui.featuerenderers.PathRenderer;
import ui.scenerenderers.GameRenderer;

public class RenderPathCommand extends Command {
    private final GameRenderer galaxyRenderer;

    public RenderPathCommand(FlatGalaxySociety game, GameRenderer galaxyRenderer) {
        super(game, "Render Pathfinding");
        this.galaxyRenderer = galaxyRenderer;
    }

    @Override
    public void execute() {
        var existingPathRenderer = galaxyRenderer.featureRenderers.findAny(featureRenderer -> featureRenderer instanceof PathRenderer);

        if (existingPathRenderer.isPresent())
            galaxyRenderer.featureRenderers.remove(existingPathRenderer.get());
        else
            galaxyRenderer.featureRenderers.add(new PathRenderer(game.galaxy), Priority.HIGH);
    }
}
