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
import main.FlatGalaxySociety ;
import models.Entity;
import models.Planet;
import quadtree.Quad;
import states.collosionchecks.QuadCollisionChecker;
import ui.model.Config;
import ui.Renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.FlatGalaxySociety.deltaMultiplier;
import static ui.model.Config.SCREEN_HEIGHT;
import static ui.model.Config.SCREEN_WIDTH;

public class GameRenderer extends SceneRenderer {
    private static final Font font = Font.font("Arial", 14);

    public boolean shouldRenderQuadtree = false;

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
                Map.entry(KeyCode.A, new AddAsteroidCommand(game)),
                Map.entry(KeyCode.R, new RemoveAsteroidCommand(game)),
                Map.entry(KeyCode.SPACE, new TogglePauseCommand(game)),
                Map.entry(KeyCode.O, new GalaxySelectorCommand(game))
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
        var entities = game.galaxy.getEntities();

        // Clear screen;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Render quad
        if (shouldRenderQuadtree && game.galaxy.collisionChecker instanceof QuadCollisionChecker quadCollision) {
            gc.setStroke(Color.GREEN);
            renderQuadtree(quadCollision.quadtree);
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
        }

        // Draw planet connections
        for (Entity entity : entities) {
            if (entity instanceof Planet planet) {
                List<Planet> connections = new ArrayList<>();
                for (Planet neighbour : planet.getNeighbours()) {
                    if (connections.stream().anyMatch(connection -> entity == connection))
                        continue;
                    connections.add(neighbour);
                    gc.strokeLine(entity.position.x, entity.position.y, neighbour.position.x, neighbour.position.y);
                }
            }
        }

        // Draw planets
        for (Entity entity : entities) {
            gc.setFill(entity.color.getFXColor());
            gc.fillOval(entity.position.x - entity.radius, entity.position.y - entity.radius, entity.radius * 2, entity.radius * 2);


            gc.setTextAlign(TextAlignment.CENTER);
            if (entity instanceof Planet planet)
                gc.fillText(planet.name, entity.position.x, entity.position.y - planet.radius - 2);
        }

        // Print HUD
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFill(Color.BLACK);
        gc.fillText("Multiplier: " + deltaMultiplier, 20, 20);
        gc.fillText("Collision Mode: " + game.galaxy.collisionChecker.name, 20, 40);
        gc.fillText("Entities: " + game.galaxy.numberOfEntities(), 20, 60);
    }

    private void renderQuadtree(Quad quadtree) {
        if (quadtree == null)
            return;

        gc.setLineWidth((double) 8 / (quadtree.getDepth() + 1));
        gc.strokeRect(quadtree.topLeftBoundary.x, quadtree.topLeftBoundary.y, quadtree.bottomRightBoundary.x - quadtree.topLeftBoundary.x, quadtree.bottomRightBoundary.y - quadtree.topLeftBoundary.y);
        renderQuadtree(quadtree.getTopLeft());
        renderQuadtree(quadtree.getTopRight());
        renderQuadtree(quadtree.getBottomLeft());
        renderQuadtree(quadtree.getBottomRight());
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
