package factories;

import models.Entity;
import models.Galaxy;
import models.Planet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GalaxyBuilder {
    private final Galaxy galaxy;
    private final Map<Planet, List<String>> planetNeighbours;

    public GalaxyBuilder() {
        this.galaxy = new Galaxy();
        this.planetNeighbours = new HashMap<>();
    }

    public void addEntity(Entity entity, List<String> neighbours) {
        galaxy.addEntity(entity);

        if (entity instanceof Planet)
            this.planetNeighbours.put((Planet) entity, neighbours);
    }

    public Galaxy getResult() {
        planetNeighbours.forEach((planet, neighbourNames) -> {
            for (var neighbourName : neighbourNames) {
                planetNeighbours
                        .keySet()
                        .stream()
                        .filter(entity2 -> entity2.name.equals(neighbourName))
                        .findFirst()
                        .get()
                        .addNeighbour(planet);
            }
        });
        return this.galaxy;
    }
}
