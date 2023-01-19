package models;

import java.util.ArrayList;
import java.util.List;

public class Galaxy {

    private final List<Entity> entities = new ArrayList<>();
    private final List<Entity> removeEntityList = new ArrayList<>();
    private final List<Entity> addEntityList = new ArrayList<>();

    public void addToGalaxy(Entity entity) {
        addEntityList.add(entity);
    }

    public void removeFromGalaxy(Entity entity) {
        removeEntityList.add(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void tick(long delta) {
        for (Entity entity : entities) {
            entity.translate(delta, entities.stream().filter(entityFromList -> entityFromList != entity).toList());
        }
        for (Entity entity : entities) {
            entity.checkForEntityCollisions(entities.stream().filter(entityFromList -> entityFromList != entity).toList());
        }
        entities.addAll(addEntityList);
        addEntityList.clear();
        for (Entity entity : removeEntityList) {
            if (entity instanceof Planet planet)
                planet.removeConnections();
        }
        entities.removeAll(removeEntityList);
        removeEntityList.clear();
    }
}
