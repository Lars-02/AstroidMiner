import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Asteroid;
import models.Galaxy;
import models.Planet;
import states.entity_states.BlinkState;
import ui.Renderer;

import java.util.Random;

public class FlatGalaxySociety extends Application {
    public static final int MillisecondsPerTick = 100;

    @Override
    public void start(Stage stage) {

        var galaxy = new Galaxy();

        var earth = new Planet("Earth", 600, 100, 0, 0, 4, Color.BLUE);
        earth.setState(new BlinkState(earth));
        galaxy.entities.add(earth);

        var pluto = new Planet("Pluto", 50, 50, 10, 10, 2, Color.GREEN);
        pluto.setState(new BlinkState(pluto));
        pluto.addConnectionNeighbour(earth);
        galaxy.entities.add(pluto);

        var mars = new Planet("Mars", 300, 300, 0, 0, 8, Color.RED);
        mars.setState(new BlinkState(mars));
        galaxy.entities.add(mars);

        Random random = new Random();
        for (int i = 0; i <= 20; i++) {
            var radius = random.nextInt(3) + 1;
            var asteroid = new Asteroid(random.nextInt(Renderer.ScreenWidth - 2 * radius) + radius, random.nextInt(Renderer.ScreenHeight - 2 * radius) + radius, random.nextInt(40) -20, random.nextInt(40) - 20, radius, Color.BLACK);
            asteroid.setState(new BlinkState(asteroid));
            galaxy.entities.add(asteroid);
        }

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
    }

    public static void main(String[] args) {
        launch();
    }
}
