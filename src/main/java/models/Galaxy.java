package models;

import commands.*;
import javafx.scene.input.KeyCode;
import memento.History;
import memento.Memento;
import memento.Restorable;
import states.collosionchecks.CollisionChecker;
import states.collosionchecks.QuadCollisionChecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Galaxy implements Restorable<GalaxyState> {
    private final History<Galaxy, GalaxyState> history = new History<>();
    public CollisionChecker collisionChecker = new QuadCollisionChecker(this);
    public Map<Command, KeyCode> commandKeyMap = new HashMap<>(Map.ofEntries(
            Map.entry(new SpeedUpCommand(this), KeyCode.EQUALS),
            Map.entry(new SlowDownCommand(this), KeyCode.MINUS),
            Map.entry(new PauseCommand(this), KeyCode.P),
            Map.entry(new ResumeCommand(this), KeyCode.ENTER),
            Map.entry(new RenderQuadtreeCommand(this), KeyCode.Q),
            Map.entry(new PreviousBookmarkCommand(this), KeyCode.LEFT),
            Map.entry(new NextBookmarkCommand(this), KeyCode.RIGHT),
            Map.entry(new SwitchCollosionModeCommand(this), KeyCode.C),
            Map.entry(new AddAsteroidCommand(this), KeyCode.A),
            Map.entry(new RemoveAsteroidCommand(this), KeyCode.R),
            Map.entry(new TogglePauseCommand(this), KeyCode.SPACE)
    ));
    private List<Entity> entities = new ArrayList<>();

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
        for (var entity : getEntities()) {
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
        return new GalaxyState(getEntities());
    }

    public void restore(GalaxyState state) {
        entities = state.entities();
    }


    public int numberOfEntities() {
        return entities.size();
    }
}
