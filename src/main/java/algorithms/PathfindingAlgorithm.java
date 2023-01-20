package algorithms;

import models.Planet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class PathfindingAlgorithm {

    protected abstract List<Planet> getPath(List<Planet> nodes, Planet source, Planet target);

    protected final List<Planet> getPathFrom(Planet current, Map<Planet, Planet> parent) {
        List<Planet> path = new ArrayList<>();
        while (current != null) {
            path.add(0, current);
            current = parent.get(current);
        }
        return path;
    }
}
