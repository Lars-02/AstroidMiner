package ui.featuerenderers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.TextAlignment;
import models.Entity;
import models.Galaxy;
import models.Planet;

import java.util.ArrayList;
import java.util.List;

public class EntityRenderer implements FeatureRenderer {
    private final Galaxy galaxy;

    public EntityRenderer(Galaxy galaxy) {
        this.galaxy = galaxy;
    }

    @Override
    public void render(GraphicsContext gc) {
        for (var entity : galaxy.getEntities()) {
            gc.setFill(entity.color.getFXColor());
            gc.fillOval(entity.position.x - entity.radius, entity.position.y - entity.radius, entity.radius * 2, entity.radius * 2);


            gc.setTextAlign(TextAlignment.CENTER);
            if (entity instanceof Planet planet)
                gc.fillText(planet.name, entity.position.x, entity.position.y - planet.radius - 2);
        }
    }
}
