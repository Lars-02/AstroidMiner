package ui.featuerenderers;

import javafx.scene.canvas.GraphicsContext;
import models.Entity;
import models.Galaxy;
import models.Planet;

import java.util.ArrayList;
import java.util.List;

public class PlanetConnectionRenderer implements FeatureRenderer {
    private final Galaxy galaxy;

    public PlanetConnectionRenderer(Galaxy galaxy) {
        this.galaxy = galaxy;
    }

    @Override
    public void render(GraphicsContext gc) {
        for (var entity : galaxy.getEntities()) {
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
    }
}
