package main;

import javafx.application.Application;
import javafx.stage.Stage;
import models.Galaxy;
import ui.scenerenderers.GalaxyLoaderRenderer;
import ui.Renderer;

public class FlatGalaxySociety extends Application {
    public static int deltaMultiplier = 10;

    private Renderer renderer;
    public Galaxy galaxy;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        this.renderer = new Renderer(stage);

        renderer.setSceneRenderer(new GalaxyLoaderRenderer(this));
    }

    public Renderer getRenderer() {
        return renderer;
    }
}
