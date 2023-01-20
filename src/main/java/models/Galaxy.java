package models;

import algorithms.BreadthFirstSearchAlgorithm;
import algorithms.PathfindingAlgorithm;
import javafx.util.Pair;
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
    public PathfindingAlgorithm pathfindingAlgorithm = new BreadthFirstSearchAlgorithm();
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

    public List<Planet> getPlanets() {
        return entities.stream().filter(entity -> entity instanceof Planet).map(planet -> (Planet) planet).toList();
    }

    public void tick(long delta) {
        if (isPaused)
            return;

        for (var entity : getEntities()) {
            entity.translate(delta);
        }

        collisionChecker.checkCollisions();

        var biggestPlanets = getTwoBiggestPlanets();

        pathfindingAlgorithm.setPath(getPlanets(), biggestPlanets.getKey(), biggestPlanets.getValue());
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

    public Pair<Planet, Planet> getTwoBiggestPlanets() {
        Pair<Planet, Planet> planetPair = new Pair<>(null, null);
        for (var entity : entities) {
            if (entity instanceof Planet planet) {
                if (planetPair.getKey() == null || planetPair.getKey().radius < planet.radius) {
                    planetPair = new Pair<>(planet, planetPair.getValue());
                    continue;
                }
                if (planetPair.getValue() == null || planetPair.getValue().radius < planet.radius) {
                    planetPair = new Pair<>(planetPair.getKey(), planet);
                }
            }
        }
        return planetPair;
    }

    public int historySize() {
        return history.size();
    }

    public int historyStep() {
        return history.virtualSize();
    }
}
