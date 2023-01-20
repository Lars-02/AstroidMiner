package algorithms;

import models.Planet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class PathfindingAlgorithm {

    public final List<Planet> getShortestPath(List<Planet> nodes, Planet source, Planet target) {
        return getPath(nodes, source, target, true);

    }

    public final List<Planet> getCheapestPath(List<Planet> nodes, Planet source, Planet target) {
        return getPath(nodes, source, target, false);
    }

    protected abstract List<Planet> getPath(List<Planet> nodes, Planet source, Planet target, boolean ignoreWeights);

    protected final List<Planet> getPathFrom(Planet current, Map<Planet, Planet> parent) {
        List<Planet> path = new ArrayList<>();
        while (current != null) {
            path.add(0, current);
            current = parent.get(current);
        }
        return path;
    }
}
