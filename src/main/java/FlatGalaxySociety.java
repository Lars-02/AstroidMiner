import exceptions.filereader.FileReaderException;
import exceptions.galaxyparser.GalaxyParserException;
import factories.GalaxyFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import ui.Renderer;

public class FlatGalaxySociety extends Application {
    public static final int MillisecondsPerTick = 10;

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
                long waitUntil = System.currentTimeMillis() - 1;
                while (true) {
                    if (waitUntil > System.currentTimeMillis())
                        continue;

                    final long delta = System.currentTimeMillis() + MillisecondsPerTick - waitUntil;
                    galaxy.tick(delta);
                    renderer.render();

                    waitUntil = System.currentTimeMillis() + MillisecondsPerTick;
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
