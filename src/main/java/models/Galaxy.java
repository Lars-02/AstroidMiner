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
    public CollisionChecker collisionChecker = new NaiveCollisionChecker(this);
    public List<Entity> entities = new ArrayList<>();
    public Map<Command, KeyCode> commandKeyMap = new HashMap<>(Map.of(
            new SpeedUpCommand(this), KeyCode.EQUALS,
            new SlowDownCommand(this), KeyCode.MINUS,
            new PauseCommand(this), KeyCode.P,
            new ResumeCommand(this), KeyCode.SPACE,
            new QuadtreeCommand(this), KeyCode.Q,
            new PreviousBookmarkCommand(this), KeyCode.LEFT,
            new NextBookmarkCommand(this), KeyCode.RIGHT,
            new SwitchCollosionModeCommand(this), KeyCode.C
    ));

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        if (entity instanceof Planet planet)
            planet.removeConnections();
        entities.remove(entity);
    }

    public List<Entity> getEntities() {
        return List.copyOf(entities);
    }

    public void tick(long delta) {
        // Translate entities
        for (var entity : entities) {
            entity.translate(delta);
        }

        collisionChecker.checkCollisions();
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
        return new GalaxyState(entities);
    }

    public void restore(GalaxyState state) {
        entities = state.entities();
    }
}
