import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Asteroid;
import models.Galaxy;
import models.Planet;
import states.entity_states.*;
import ui.Renderer;

import java.util.Random;

public class FlatGalaxySociety extends Application {
    public static final int MillisecondsPerTick = 10;

    @Override
    public void start(Stage stage) {

        var galaxy = new Galaxy();

        var earth = new Planet("Earth", galaxy, 600, 100, 0, 0, 4, Color.BLUE);
        earth.setState(new BlinkState(earth));

        var pluto = new Planet("Pluto", galaxy, 50, 50, 20, 20, 2, Color.GREEN);
        pluto.setState(new DisappearState(pluto));
        pluto.addConnectionNeighbour(earth);

        var mars = new Planet("Mars", galaxy, 300, 300, 0, 0, 8, Color.RED);
        mars.setState(new BlinkState(mars));

        Random random = new Random();
        for (int i = 0; i <= 40; i++) {
            var radius = random.nextInt(3) + 1;
            var asteroid = new Asteroid(galaxy, random.nextInt(Renderer.ScreenWidth - 2 * radius) + radius, random.nextInt(Renderer.ScreenHeight - 2 * radius) + radius, random.nextInt(20) - 10, random.nextInt(20) - 10, radius, Color.BLACK);
            asteroid.setState(new DisappearState(asteroid));
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
