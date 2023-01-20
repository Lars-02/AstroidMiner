package algorithms;

import models.Planet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class PathfindingAlgorithm {

    public Path path;
    public final String name;

    PathfindingAlgorithm(String name) {
        this.name = name;
    }

    public void setPath(List<Planet> nodes, Planet source, Planet target) {
        path = getPath(nodes, source, target);
    }

    protected abstract Path getPath(List<Planet> nodes, Planet source, Planet target);

    protected final List<Planet> getPathFrom(Planet current, Map<Planet, Planet> parent, Planet target) {
        List<Planet> path = new ArrayList<>();
        while (current != null) {
            path.add(0, current);
            current = parent.get(current);
        }
        path.add(target);
        return path;
    }
}
