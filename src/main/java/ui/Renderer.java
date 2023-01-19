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

import java.util.ArrayList;
import java.util.List;

import static main.FlatGalaxySociety.deltaMultiplier;
import static main.FlatGalaxySociety.isPaused;

public class Renderer {

    public static final int ScreenWidth = 800;
    public static final int ScreenHeight = 600;

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
        gc.setFont(Font.font("Arial", 14));

        stage.setResizable(false);
        stage.setTitle("Flat Galaxy Society");

        initializeGalaxy();
    }

    public void initializeMenu(Scene galaxyScene) {

        // Init grid
        var grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);

        // Init scene
        var scene = new Scene(grid, ScreenWidth, ScreenHeight, Color.WHITE);
        stage.setScene(scene);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() != KeyCode.ESCAPE)
                return;

            isPaused = false;
            stage.setScene(galaxyScene);
            stage.show();
        });

        int index = 0;
        for (var commandKey : galaxy.commandKeyMap.entrySet().stream().toList()) {
            final var descriptionText = new Text(commandKey.getKey().name);
            descriptionText.setStyle("-fx-font: 18 arial;");
            grid.add(descriptionText, 0, index);

            final var key = new Text(commandKey.getValue().getName());
            key.setStyle("-fx-font: 18 arial;");

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
                        stage.setScene(galaxyScene);
                        stage.show();
                        return;
                    }
                    if (galaxy.commandKeyMap.containsValue(keyEvent.getCode())) {
                        key.setFill(Color.RED);
                        return;
                    }
                    key.setText(keyEvent.getCode().getName());
                    galaxy.commandKeyMap.put(commandKey.getKey(), keyEvent.getCode());
                    key.setFill(Color.GREEN);
                });
            });

            grid.add(key, 1, index);
            index++;
        }

        stage.show();
    }

    private void initializeGalaxy() {
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
            initializeMenu(scene);
        });


        stage.setScene(scene);
        stage.show();

    }

    public void renderGalaxy() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Entity entity : galaxy.getEntities()) {
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

        for (Entity entity : galaxy.getEntities()) {
            gc.setFill(entity.color);
            gc.fillOval(entity.position.x - entity.getRadius(), entity.position.y - entity.getRadius(), entity.getRadius() * 2, entity.getRadius() * 2);

            if (entity instanceof Planet planet)
                gc.fillText(planet.name, entity.position.x, entity.position.y - planet.getRadius() - 2);
        }

        gc.fillText("Multiplier: " + deltaMultiplier, (double) ScreenWidth / 2, 20);
    }
}
