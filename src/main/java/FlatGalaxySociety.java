import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Galaxy;
import models.Planet;
import states.entity_states.BlinkState;
import ui.Renderer;

import java.util.Collections;

public class FlatGalaxySociety extends Application {
    public static final int MilliSecondsPerTick = 100;

    @Override
    public void start(Stage stage) {

        var galaxy = new Galaxy();

        var earth = new Planet("Earth", Collections.emptyList(), 600, 100, 0, 0, 4, Color.BLUE);
        earth.setState(new BlinkState(earth));
        galaxy.entities.add(earth);

        var pluto = new Planet("Pluto", Collections.emptyList(), 50, 50, 50, 50, 2, Color.GREEN);
        pluto.setState(new BlinkState(pluto));
        galaxy.entities.add(pluto);

        var mars = new Planet("Mars", Collections.emptyList(), 300, 300, 0, 0, 8, Color.RED);
        mars.setState(new BlinkState(mars));
        galaxy.entities.add(mars);

        var renderer = new Renderer(stage, galaxy);

        renderer.render();
        new Thread(() -> {
            long waitUntil = System.currentTimeMillis() - 1;
            while (true) {
                if (waitUntil > System.currentTimeMillis())
                    continue;

                final long delta = System.currentTimeMillis() + MilliSecondsPerTick - waitUntil;
                galaxy.tick(delta);
                renderer.render();

                waitUntil = System.currentTimeMillis() + MilliSecondsPerTick;
            }
        }).start();
    }

    public static void main(String[] args) {
        launch();
    }
}
