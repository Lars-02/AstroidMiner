import javafx.application.Application;
import javafx.stage.Stage;
import models.Galaxy;
import models.Planet;
import states.entity_states.BlinkState;
import ui.Renderer;


public class FlatGalaxySociety extends Application {
    @Override
    public void start(Stage stage) {
        var renderer = new Renderer(stage);

        var galaxy = new Galaxy();

//        var planet1 = new Planet();
//        var blink = new BlinkState(planet1);

        renderer.render(galaxy);
    }

    public static void main(String[] args) {
        launch();
    }
}
