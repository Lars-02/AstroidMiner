package ui;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import models.Entity;
import models.Galaxy;
import models.Planet;
import quadtree.Quad;
import states.collosionchecks.QuadCollisionChecker;

import java.util.ArrayList;
import java.util.List;

import static main.FlatGalaxySociety.deltaMultiplier;
import static main.FlatGalaxySociety.isPaused;

public class Renderer {

    public static final int ScreenWidth = 800;
    public static final int ScreenHeight = 600;
    public static final boolean RenderQuadtree = true;

    private static final Font menuFont = Font.font("Arial", 18);
    private static final Font galaxyFont = Font.font("Arial", 14);
    private final Galaxy galaxy;
    private final Stage stage;
    private final Canvas canvas;
    private final GraphicsContext gc;
    private Text keyClicked;

    public Renderer(Stage stage, Galaxy galaxy) {
        this.galaxy = galaxy;
        this.stage = stage;
        this.canvas = new Canvas(ScreenWidth, ScreenHeight);
        this.gc = canvas.getGraphicsContext2D();

        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(galaxyFont);

        stage.setResizable(false);
        stage.setTitle("Flat Galaxy Society");

        initializeGalaxyScene();
    }

    public void openKeybindingMenu(Scene parent) {
        var grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);

        var scene = new Scene(grid, ScreenWidth, ScreenHeight, Color.WHITE);
        stage.setScene(scene);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() != KeyCode.ESCAPE)
                return;

            isPaused = false;
            stage.setScene(parent);
            stage.show();
        });

        int gridY = 0;
        for (var commandKey : galaxy.commandKeyMap.entrySet().stream().toList()) {
            final var descriptionText = new Text(commandKey.getKey().name);
            descriptionText.setFont(menuFont);

            grid.add(descriptionText, 0, gridY);

            final var key = new Text(commandKey.getValue().getName());
            key.setFont(menuFont);

            key.setOnMouseEntered(event -> {
                if (keyClicked != null && keyClicked == key)
                    return;
                key.setFill(Color.GRAY);
                scene.setCursor(Cursor.HAND);
            });

            key.setOnMouseExited(event -> {
                scene.setCursor(Cursor.DEFAULT);
                if (keyClicked == null || keyClicked != key)
                    key.setFill(Color.BLACK);
            });

            key.setOnMouseClicked(event -> {
                if (keyClicked != null) {
                    for (var child : grid.getChildren()) {
                        if (child instanceof Text text)
                            text.setFill(Color.BLACK);
                    }
                }

                key.setFill(Color.DARKBLUE);
                keyClicked = key;

                stage.getScene().setOnKeyPressed(keyEvent -> {
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        isPaused = false;
                        stage.setScene(parent);
                        stage.show();
                        return;
                    }

                    if (galaxy.commandKeyMap.containsValue(keyEvent.getCode())) {
                        key.setFill(Color.RED);
                        return;
                    }

                    galaxy.commandKeyMap.put(commandKey.getKey(), keyEvent.getCode());
                    key.setText(keyEvent.getCode().getName());
                    key.setFill(Color.GREEN);
                });
            });

            grid.add(key, 1, gridY);
            gridY++;
        }

        stage.show();
    }

    private void initializeGalaxyScene() {
        var scene = new Scene(new StackPane(canvas), ScreenWidth, ScreenHeight, Color.WHITE);

        scene.setOnKeyPressed(event -> {
            for (var command : galaxy.commandKeyMap.entrySet()) {
                if (event.getCode() != command.getValue())
                    continue;

                command.getKey().execute();
            }

            if (event.getCode() != KeyCode.ESCAPE)
                return;

            isPaused = true;
            openKeybindingMenu(scene);
        });


        stage.setScene(scene);
        stage.show();

    }

    public void renderGalaxy() {
        // Set entities
        var entities = galaxy.getEntities();

        // Clear screen;
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Render quad
        if (RenderQuadtree && galaxy.collisionChecker instanceof QuadCollisionChecker quadCollision) {
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
        gc.fillText("Multiplier: " + deltaMultiplier, 20, 20);
        gc.fillText("Collision Mode: " + galaxy.collisionChecker.name, 20, 40);
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
}
