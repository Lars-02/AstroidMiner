package commands;

import main.FlatGalaxySociety ;
import ui.scenerenderers.GalaxyLoaderRenderer;

import static main.FlatGalaxySociety.deltaMultiplier;

public class GalaxySelectorCommand extends Command {
    public GalaxySelectorCommand(FlatGalaxySociety game) {
        super(game, "Load Other Galaxy");
    }

    @Override
    public void execute() {
        game.getRenderer().setSceneRenderer(new GalaxyLoaderRenderer(game));
    }
}
