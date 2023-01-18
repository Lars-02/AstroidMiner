package models;

import java.util.ArrayList;
import java.util.List;

public class Galaxy {

    public List<Entity> entities = new ArrayList<>();

    public void tick(long delta) {
        for (Entity entity : entities) {
            entity.tick(delta, entities.stream().filter(entityFromList -> entityFromList != entity).toList());
        }
    }
}
