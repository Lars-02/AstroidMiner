package ui.featuerenderers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import models.Galaxy;

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
        gc.fillText("Multiplier: " + deltaMultiplier, 10, 20);
        gc.fillText("Collision Mode: " + galaxy.collisionChecker.name, 10, 40);
        gc.fillText("Pathfinder Mode: " + galaxy.pathfindingAlgorithm.name, 10, 60);
        gc.fillText("Snapshots: " + galaxy.historyStep() + "/" + galaxy.historySize(), 10, 80);
        gc.fillText("Entities: " + galaxy.numberOfEntities(), 10, 100);
    }
}
