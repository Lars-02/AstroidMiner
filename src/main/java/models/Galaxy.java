package models;

import java.util.ArrayList;
import java.util.List;

public class Galaxy {

    private final List<Entity> entities = new ArrayList<>();

    public void addToGalaxy(Entity entity) {
        entities.add(entity);
    }

    public void removeFromGalaxy(Entity entity) {
        entities.remove(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void tick(long delta) {
        for (Entity entity : entities) {
            entity.tick(delta, entities.stream().filter(entityFromList -> entityFromList != entity).toList());
        }
    }
}
