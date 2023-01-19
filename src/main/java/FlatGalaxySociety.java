import exceptions.filereader.FileReaderException;
import exceptions.galaxyparser.GalaxyParserException;
import factories.GalaxyFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import ui.Renderer;

public class FlatGalaxySociety extends Application {
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

            renderer.render();
            new Thread(() -> {
                var lastTick = System.currentTimeMillis();
                while (true) {
                    final var current = System.currentTimeMillis();
                    final var delta = current - lastTick;
                    lastTick = current;

                    galaxy.tick(delta);
                    renderer.render();
                }
            }).start();

        } catch (FileReaderException | GalaxyParserException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
