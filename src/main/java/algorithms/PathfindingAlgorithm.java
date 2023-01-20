package algorithms;

import models.Planet;

import java.util.List;

public abstract class PathfindingAlgorithm {

    public final List<Planet> getShortestPath(List<Planet> nodes, Planet source, Planet target) {
        return getPath(nodes, source, target, true);

    }

    public final List<Planet> getCheapestPath(List<Planet> nodes, Planet source, Planet target) {
        return getPath(nodes, source, target, false);
    }

    protected abstract List<Planet> getPath(List<Planet> nodes, Planet source, Planet target, boolean ignoreWeights);

}
