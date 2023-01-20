package main;

import exceptions.filereader.FileReaderException;
import exceptions.galaxyparser.GalaxyParserException;
import factories.GalaxyFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import ui.Renderer;

public class FlatGalaxySociety extends Application {
    private static final int BookmarkIntervalMillis = 5000;

    public static boolean isPaused = false;
    public static int deltaMultiplier = 10;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        try {
            var galaxy = GalaxyFactory.fromFile(
                    "./src/main/resources/planetsExtended.csv"
//                    "./src/main/resources/planetsExtended.xml"
//                    "https://firebasestorage.googleapis.com/v0/b/dpa-files.appspot.com/o/planetsExtended.csv?alt=media"
//                    "https://firebasestorage.googleapis.com/v0/b/dpa-files.appspot.com/o/planetsExtended.xml?alt=media"
            );


            var renderer = new Renderer(stage, galaxy);

            renderer.renderGalaxy();
            new Thread(() -> {
                var lastSave = System.currentTimeMillis();
                var lastTick = System.currentTimeMillis();
                while (true) {
                    final var current = System.currentTimeMillis();
                    final var delta = current - lastTick;
                    lastTick = current;

                    galaxy.tick(isPaused ? 0 : delta * deltaMultiplier);

                    if (!isPaused && current - lastSave > BookmarkIntervalMillis) {
                        galaxy.save();
                        lastSave = current;
                    }

                    Platform.runLater(renderer::renderGalaxy);

                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException ignored) {
                    }
                }
            }).start();
        } catch (FileReaderException | GalaxyParserException e) {
            throw new RuntimeException(e);
        }
    }
}
