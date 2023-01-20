package ui;

import javafx.stage.Stage;
import ui.scenerenderers.SceneRenderer;

public class Renderer {
    private final Stage stage;
    private SceneRenderer currentSceneRenderer;

    public Renderer(Stage stage) {
        this.stage = stage;

        stage.setResizable(false);
        stage.setTitle("Flat Galaxy Society");
    }

    public void setSceneRenderer(SceneRenderer sceneRenderer) {
        if (currentSceneRenderer != null)
            currentSceneRenderer.deactivate();

        currentSceneRenderer = sceneRenderer;
        stage.setScene(currentSceneRenderer.getScene());

        currentSceneRenderer.activate();
        stage.show();
    }
}
