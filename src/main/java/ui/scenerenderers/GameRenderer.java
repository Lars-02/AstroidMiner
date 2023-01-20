package ui.scenerenderers;

import commands.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import main.FlatGalaxySociety;
import models.priority.Priority;
import models.priority.PriorityList;
import ui.Renderer;
import ui.featuerenderers.EntityRenderer;
import ui.featuerenderers.FeatureRenderer;
import ui.featuerenderers.HudRenderer;
import ui.featuerenderers.PlanetConnectionRenderer;
import ui.model.Config;

import java.util.HashMap;
import java.util.Map;

import static main.FlatGalaxySociety.deltaMultiplier;
import static ui.model.Config.SCREEN_HEIGHT;
import static ui.model.Config.SCREEN_WIDTH;

public class GameRenderer extends SceneRenderer {
    private static final Font font = Font.font("Arial", 14);

    public final PriorityList<FeatureRenderer> featureRenderers = new PriorityList<>();

    private final UpdateRunnable updateRunnable;
    private final Scene scene;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final FlatGalaxySociety game;
    private final Map<KeyCode, Command> keybindings;

    public GameRenderer(Renderer renderer, FlatGalaxySociety game) {
        super(renderer);
        this.game = game;

        this.keybindings = new HashMap<>(Map.ofEntries(
                Map.entry(KeyCode.EQUALS, new SpeedUpCommand(game)),
                Map.entry(KeyCode.MINUS, new SlowDownCommand(game)),
                Map.entry(KeyCode.P, new PauseCommand(game)),
                Map.entry(KeyCode.ENTER, new ResumeCommand(game)),
                Map.entry(KeyCode.Q, new RenderQuadtreeCommand(game, this)),
                Map.entry(KeyCode.LEFT, new PreviousBookmarkCommand(game)),
                Map.entry(KeyCode.RIGHT, new NextBookmarkCommand(game)),
                Map.entry(KeyCode.C, new SwitchCollosionModeCommand(game)),
                Map.entry(KeyCode.F, new SwitchPathFindingAlgorithmCommand(game)),
                Map.entry(KeyCode.X, new RenderPathCommand(game, this)),
                Map.entry(KeyCode.A, new AddAsteroidCommand(game)),
                Map.entry(KeyCode.R, new RemoveAsteroidCommand(game)),
                Map.entry(KeyCode.SPACE, new TogglePauseCommand(game)),
                Map.entry(KeyCode.O, new GalaxySelectorCommand(game)),
                Map.entry(KeyCode.H, new RenderHudCommand(game, this))
        ));

        this.canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.gc = canvas.getGraphicsContext2D();
        this.scene = new Scene(new StackPane(canvas), SCREEN_WIDTH, SCREEN_HEIGHT, Color.WHITE);

        this.updateRunnable = new UpdateRunnable(this);

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(font);

        scene.setOnKeyPressed(event -> {
            var command = keybindings.get(event.getCode());

            if (command != null)
                command.execute();

            if (event.getCode() == KeyCode.ESCAPE) {
                renderer.setSceneRenderer(new KeybindingsRenderer(renderer, this, keybindings));
            }
        });

        featureRenderers.add(new PlanetConnectionRenderer(game.galaxy), Priority.NORMAL);
        featureRenderers.add(new EntityRenderer(game.galaxy), Priority.NORMAL);
        featureRenderers.add(new HudRenderer(game.galaxy), Priority.HIGHEST);
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void activate() {
        var updateThread = new Thread(updateRunnable);
        updateThread.start();

    }

    @Override
    public void deactivate() {
        updateRunnable.stop();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        featureRenderers.forEach(fr -> fr.render(gc));
    }

    private class UpdateRunnable implements Runnable {
        private final GameRenderer gameRenderer;
        private long lastSave;
        private long lastTick;
        private boolean shouldStop = false;

        public UpdateRunnable(GameRenderer gameRenderer) {
            this.gameRenderer = gameRenderer;
            reset();
        }

        private void reset() {
            shouldStop = false;

            lastSave = System.currentTimeMillis();
            lastTick = System.currentTimeMillis();
        }

        public void stop() {
            shouldStop = true;
        }

        private void update() {
            final var current = System.currentTimeMillis();
            final var delta = current - lastTick;
            lastTick = current;

            game.galaxy.tick(delta * deltaMultiplier);

            if (!game.galaxy.isPaused && current - lastSave > Config.BOOKMARK_INTERVAL_MILLIS) {
                game.galaxy.save();
                lastSave = current;
            }

            Platform.runLater(gameRenderer::render);
        }

        @Override
        public void run() {
            reset();

            while (!shouldStop) {
                update();
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
