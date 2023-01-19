package models;

import commands.*;
import javafx.scene.input.KeyCode;
import memento.History;
import memento.Memento;
import memento.Restorable;
import states.collosionchecks.CollisionChecker;
import states.collosionchecks.NaiveCollisionChecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Galaxy implements Restorable<GalaxyState> {
    private final History<Galaxy, GalaxyState> history = new History<>();
    public HashMap<Entity, ArrayList<Entity>> collisions = new HashMap<>();
    CollisionChecker collisionChecker = new NaiveCollisionChecker(this);
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
        // Translate entities
        for (var entity : entities) {
            entity.translate(delta);
        }

        collisionChecker.checkCollisions();

        // Add all new entities
        entities.addAll(addEntityList);
        addEntityList.clear();

        // Remove all entities
        for (Entity entity : removeEntityList) {
            if (entity instanceof Planet planet)
                planet.removeConnections();
        }
        entities.removeAll(removeEntityList);
        removeEntityList.clear();
    }

    public void undo() {
        history.undo();
    }

    public void redo() {
        history.redo();
    }

    public void save() {
        history.push(new Memento<>(this));
    }

    public GalaxyState serializableState() {
        return new GalaxyState(collisions, entities, addEntityList, removeEntityList);
    }

    public void restore(GalaxyState state) {
        collisions = state.collisions();
        entities = state.entities();
        addEntityList = state.addEntityList();
        removeEntityList = state.removeEntityList();
    }
}
