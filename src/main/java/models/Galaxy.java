package models;

import memento.History;
import memento.Memento;
import memento.Restorable;
import states.collosionchecks.CollisionChecker;
import states.collosionchecks.QuadCollisionChecker;

import java.util.ArrayList;
import java.util.List;

public class Galaxy implements Restorable<GalaxyState> {
    private final History<Galaxy, GalaxyState> history = new History<>();
    public CollisionChecker collisionChecker = new QuadCollisionChecker(this);
    private List<Entity> entities = new ArrayList<>();

    public boolean isPaused = false;

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
        if (isPaused)
            return;

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
