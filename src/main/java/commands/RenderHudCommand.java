package commands;

import main.FlatGalaxySociety;
import models.priority.Priority;
import ui.featuerenderers.HudRenderer;
import ui.scenerenderers.GameRenderer;

public class RenderHudCommand extends Command {
    private final GameRenderer galaxyRenderer;

    public RenderHudCommand(FlatGalaxySociety game, GameRenderer galaxyRenderer) {
        super(game, "Render HUD");
        this.galaxyRenderer = galaxyRenderer;
    }

    @Override
    public void execute() {
        var existingPathRenderer = galaxyRenderer.featureRenderers.findAny(featureRenderer -> featureRenderer instanceof HudRenderer);

        if (existingPathRenderer.isPresent())
            galaxyRenderer.featureRenderers.remove(existingPathRenderer.get());
        else
            galaxyRenderer.featureRenderers.add(new HudRenderer(game.galaxy), Priority.HIGHEST);
    }
}
