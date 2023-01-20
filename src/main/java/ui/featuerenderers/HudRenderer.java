package ui.featuerenderers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import models.Galaxy;
import models.Planet;

import static main.FlatGalaxySociety.deltaMultiplier;

public class HudRenderer implements FeatureRenderer {
    private final Galaxy galaxy;

    public HudRenderer(Galaxy galaxy) {
        this.galaxy = galaxy;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFill(Color.BLACK);
        gc.fillText("Multiplier: " + deltaMultiplier, 20, 20);
        gc.fillText("Collision Mode: " + galaxy.collisionChecker.name, 20, 40);
        gc.fillText("Entities: " + galaxy.numberOfEntities(), 20, 60);
    }
}
