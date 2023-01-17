package ui;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Entity;
import models.Galaxy;

public class Renderer {
    final Canvas canvas;
    GraphicsContext gc;

    public Renderer(Stage stage) {
        canvas = new Canvas(800,600);
        gc = canvas.getGraphicsContext2D();

        var scene = new Scene(new StackPane(canvas), 800, 600, Color.WHITE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("FlatGalaxySociety");
        stage.show();
    }

    public void render(Galaxy galaxy) {
        if (galaxy.entities == null)
            return;
        for (Entity entity: galaxy.entities) {
            drawEntity(entity);
        }
    }

    private void drawEntity(Entity entity) {
        gc.fillOval(entity.x, entity.y, entity.getRadius(), entity.getRadius());
    }
}
