package models;

import commands.*;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.util.*;

public class Galaxy {
    private final History history = new History();
    private HashMap<Entity, ArrayList<Entity>> collisions = new HashMap<>();
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> removeEntityList = new ArrayList<>();
    private List<Entity> addEntityList = new ArrayList<>();

    public Map<Command, KeyCode> commandKeyMap = new HashMap<>(Map.of(
            new SpeedUpCommand(this), KeyCode.EQUALS,
            new SlowDownCommand(this), KeyCode.MINUS,
            new PauseCommand(this), KeyCode.P,
            new ResumeCommand(this), KeyCode.SPACE,
            new QuadtreeCommand(this), KeyCode.Q,
            new PreviousBookmarkCommand(this), KeyCode.LEFT,
            new NextBookmarkCommand(this), KeyCode.RIGHT
    ));

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

    public void undo() {
        history.undo();
    }

    public void redo() {
        history.redo();
    }

    public void save() {
        history.push(new Memento(this));
    }

    public String serialize() {
        try {
            var baos = new ByteArrayOutputStream();
            var out = new ObjectOutputStream(baos);
            out.writeObject(new GalaxyState(collisions, entities, addEntityList, removeEntityList));
            out.close();
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            return "";
        }
    }

    public void restore(String save) {
        try {
            byte[] data = Base64.getDecoder().decode(save);
            var in = new ObjectInputStream(new ByteArrayInputStream(data));
            var state = (GalaxyState) in.readObject();

            collisions = state.collisions();
            entities = state.entities();
            addEntityList = state.addEntityList();
            removeEntityList = state.removeEntityList();

            in.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
