import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Asteroid;
import models.Galaxy;
import models.Planet;
import states.entity_states.BlinkState;
import ui.Renderer;

import java.util.Collections;

public class FlatGalaxySociety extends Application {
    public static final int TickPerMilliSeconds = 100;

    @Override
    public void start(Stage stage) {

        var galaxy = new Galaxy();

        var pluto = new Planet("Pluto", Collections.emptyList(), 50, 50, 50, 0, 2, Color.GREEN);
        pluto.setState(new BlinkState(pluto));
        galaxy.entities.add(pluto);

        var mars = new Planet("Mars", Collections.emptyList(), 200, 50, 0, 0, 8, Color.BLUE);
        mars.setState(new BlinkState(mars));
        galaxy.entities.add(mars);

        var renderer = new Renderer(stage, galaxy);

        renderer.render();
        new Thread(() -> {
            long waitUntil = System.currentTimeMillis() - 1;
            while (true) {
                if (waitUntil > System.currentTimeMillis())
                    continue;

                final long delta = System.currentTimeMillis() + TickPerMilliSeconds - waitUntil;
                galaxy.tick(delta);
                renderer.render();

                waitUntil = System.currentTimeMillis() + TickPerMilliSeconds;
            }
        }).start();
    }

    public static void main(String[] args) {
        launch();
    }
}
