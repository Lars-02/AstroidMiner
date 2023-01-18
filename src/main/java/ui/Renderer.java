package ui;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Entity;
import models.Galaxy;
import models.Planet;

import java.util.ArrayList;
import java.util.List;

public class Renderer {

    public static final int ScreenWidth = 800;
    public static final int ScreenHeight = 600;

    final Canvas canvas;
    GraphicsContext gc;
    final Galaxy galaxy;

    public Renderer(Stage stage, Galaxy galaxy) {
        this.galaxy = galaxy;
        canvas = new Canvas(ScreenWidth, ScreenHeight);
        gc = canvas.getGraphicsContext2D();

        var scene = new Scene(new StackPane(canvas), ScreenWidth, ScreenHeight, Color.WHITE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("FlatGalaxySociety");
        stage.show();
    }

    public void render() {
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
