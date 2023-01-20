package algorithms;

import models.Planet;

import java.util.*;

public class BreadthFirstSearchAlgorithm extends PathfindingAlgorithm {

    @Override
    protected List<Planet> getPath(List<Planet> nodes, Planet source, Planet target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Source and target nodes cannot be null");
        }
        if (source == target) {
            return new ArrayList<>();
        }
        Queue<Planet> queue = new LinkedList<>();
        Map<Planet, Planet> parent = new HashMap<>();
        queue.add(source);
        parent.put(source, null);
        while (!queue.isEmpty()) {
            Planet current = queue.poll();
            for (Planet neighbour : current.getNeighbours()) {
                if (!parent.containsKey(neighbour)) {
                    parent.put(neighbour, current);
                    queue.add(neighbour);
                }
                if (neighbour == target) {
                    return getPathFrom(current, parent);
                }
            }
        }
        return null;
    }
}
