package ui.scenerenderers;

import javafx.scene.Scene;
import ui.Renderer;

public abstract class SceneRenderer {
    protected final Renderer renderer;

    protected SceneRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public abstract Scene getScene();

    public void activate() {};

    public void deactivate() {};
}
