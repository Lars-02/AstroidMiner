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
    final Galaxy galaxy;

    public Renderer(Stage stage, Galaxy galaxy) {
        this.galaxy = galaxy;
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();

        var scene = new Scene(new StackPane(canvas), 800, 600, Color.WHITE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("FlatGalaxySociety");
        stage.show();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Entity entity : galaxy.entities) {
            gc.setFill(entity.color);
            gc.fillOval(entity.x, entity.y, entity.getRadius(), entity.getRadius());
        }
    }
}
