package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Galaxy {
    private final HashMap<Entity, ArrayList<Entity>> collisions = new HashMap<>();

    private final List<Entity> entities = new ArrayList<>();
    private final List<Entity> removeEntityList = new ArrayList<>();
    private final List<Entity> addEntityList = new ArrayList<>();

    public void addEntity(Entity entity) {
        addEntityList.add(entity);
        collisions.put(entity, new ArrayList<>());
    }

    public void removeEntity(Entity entity) {
        removeEntityList.add(entity);
        collisions.remove(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void tick(long delta) {
        for (var entity1 : entities) {
            for (var entity2 : entities) {
                if (entity2 == entity1)
                    continue;

                if (!collidedWithCircle(entity1, entity2))
                    continue;

                if (collisions.get(entity1).contains(entity2))
                    continue;

                collisions.get(entity1).add(entity2);
                entity1.onCollision(this, entity2);
            }
        }

        for (var entity : entities) {
            entity.translate(delta);
        }

        collisions.forEach((entity1, collidingEntities) -> {
            for (var entity2 : collidingEntities) {
                if (collidedWithCircle(entity1, entity2))
                    continue;

                entity1.onExitCollision(this, entity2);
                collisions.get(entity1).remove(entity2);
                return;
            }
        });

        entities.addAll(addEntityList);
        addEntityList.clear();
        for (Entity entity : removeEntityList) {
            if (entity instanceof Planet planet)
                planet.removeConnections();
        }
        entities.removeAll(removeEntityList);
        removeEntityList.clear();
    }

    private boolean collidedWithCircle(Entity entity1, Entity entity2) {
        return entity1.position.dist(entity2.position) <= (entity1.getRadius() + entity2.getRadius());
    }
}
