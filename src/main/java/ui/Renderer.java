package ui;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Entity;
import models.Galaxy;

public class Renderer {

    final Galaxy galaxy;
    final Pane pane;

    public Renderer(Stage stage, Galaxy galaxy) {
        this.galaxy = galaxy;
        pane = new Pane();
        pane.getChildren().addAll(galaxy.entities.stream().map(Entity::getShape).toList());
        pane.setPrefSize(800, 600);

        var scene = new Scene(pane, Color.WHITE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Flat Galaxy Society");
        stage.show();
    }
}
