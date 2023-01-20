package ui.featuerenderers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import models.Entity;
import models.Galaxy;
import models.Planet;

import java.util.List;

public class PathRenderer implements FeatureRenderer {

    private static final int BorderWidth = 4;

    private final Galaxy galaxy;

    public PathRenderer(Galaxy galaxy) {
        this.galaxy = galaxy;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (galaxy.pathfindingAlgorithm.path == null)
            return;

        // Draw path connections
        final List<Planet> nodes = galaxy.pathfindingAlgorithm.path.nodes();
        gc.setLineWidth((double) BorderWidth / 2);
        gc.setStroke(Color.RED);
        gc.setFont(Font.font("Arial Bold", 18));
        gc.setTextAlign(TextAlignment.CENTER);
        for (int i = 0; i < nodes.size() - 1; i++) {
            Planet planet = nodes.get(i);
            for (Planet neighbour : planet.getNeighbours()) {
                if (neighbour == nodes.get(i + 1)) {
                    gc.strokeLine(planet.position.x, planet.position.y, neighbour.position.x, neighbour.position.y);
                    var middle = planet.position.middle(neighbour.position);
                    gc.fillText(String.valueOf(i + 1), middle.x, middle.y);
                    break;
                }
            }
        }
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setLineWidth(1);
        gc.setStroke(Color.BLACK);
        gc.setFont(Font.font("Arial", 14));

        // Draw visited planets
        for (Planet planet : galaxy.pathfindingAlgorithm.path.visited()) {
            gc.setFill(Color.GREEN);
            drawPlanetWithBorder(gc, planet);
        }

        // Draw planets on path
        for (Planet planet : nodes) {
            gc.setFill(Color.RED);
            drawPlanetWithBorder(gc, planet);
        }
    }

    private void drawPlanetWithBorder(GraphicsContext gc, Entity entity) {
        gc.fillOval(entity.position.x - entity.radius, entity.position.y - entity.radius, entity.radius * 2, entity.radius * 2);
        gc.setFill(entity.color.getFXColor());
        gc.fillOval(entity.position.x - entity.radius + (double) BorderWidth / 2, entity.position.y - entity.radius + (double) BorderWidth / 2, entity.radius * 2 - BorderWidth, entity.radius * 2 - BorderWidth);

        gc.setTextAlign(TextAlignment.CENTER);
    }
}
