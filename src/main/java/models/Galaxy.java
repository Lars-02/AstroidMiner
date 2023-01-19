package models;

import commands.*;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Galaxy {

    private final List<Entity> entities = new ArrayList<>();
    private final List<Entity> removeEntityList = new ArrayList<>();
    private final List<Entity> addEntityList = new ArrayList<>();

    public Map<Command, KeyCode> commandKeyMap = new HashMap<>(Map.of(
            new SpeedUpCommand(this), KeyCode.EQUALS,
            new SlowDownCommand(this), KeyCode.MINUS,
            new PauseCommand(this), KeyCode.P,
            new ResumeCommand(this), KeyCode.SPACE,
            new QuadtreeCommand(this), KeyCode.Q,
            new RevertCommand(this), KeyCode.R
    ));

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
