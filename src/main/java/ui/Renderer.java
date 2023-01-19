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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import models.Entity;
import models.Galaxy;
import models.Planet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Renderer {

    public static final int ScreenWidth = 800;
    public static final int ScreenHeight = 600;

    private final Galaxy galaxy;
    private final Stage stage;
    private Text keyClicked;

    public Renderer(Stage stage, Galaxy galaxy) {
        this.galaxy = galaxy;
        this.stage = stage;
        stage.setResizable(false);
        stage.setTitle("Flat Galaxy Society");
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

    public Canvas initializeGalaxy() {
        var canvas = new Canvas(ScreenWidth, ScreenHeight);

        var scene = new Scene(new StackPane(canvas), ScreenWidth, ScreenHeight, Color.WHITE);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() != KeyCode.ESCAPE)
                return;

            initializeMenu(scene);
        });

        stage.setScene(scene);
        stage.show();

        return canvas;
    }

    public void renderGalaxy(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Entity entity : galaxy.getEntities()) {
            if (entity instanceof Planet) {
                List<Planet> connections = new ArrayList<>();
                for (Planet neighbour : ((Planet) entity).getNeighbours()) {
                    if (connections.stream().anyMatch(connection -> entity == connection))
                        continue;
                    connections.add(neighbour);
                    gc.strokeLine(entity.x, entity.y, neighbour.x, neighbour.y);
                }
            }
        }

        for (Entity entity : galaxy.getEntities()) {
            gc.setFill(entity.color);
            gc.fillOval(entity.x - entity.getRadius(), entity.y - entity.getRadius(), entity.getRadius() * 2, entity.getRadius() * 2);
        }
    }
}
