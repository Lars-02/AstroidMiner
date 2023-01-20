package algorithms;

import models.Planet;

import java.util.*;

public class BreadthFirstSearchAlgorithm extends PathfindingAlgorithm {

    public BreadthFirstSearchAlgorithm() {
        super("BFS");
    }

    @Override
    protected Path getPath(List<Planet> nodes, Planet source, Planet target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Source and target nodes cannot be null");
        }
        if (source == target) {
            return new Path(new ArrayList<>(), new ArrayList<>());
        }
        Queue<Planet> queue = new LinkedList<>();
        Map<Planet, Planet> parent = new HashMap<>();
        List<Planet> visited = new ArrayList<>();
        queue.add(source);
        parent.put(source, null);
        while (!queue.isEmpty()) {
            Planet current = queue.poll();
            visited.add(current);
            for (Planet neighbour : current.getNeighbours()) {
                if (!parent.containsKey(neighbour)) {
                    parent.put(neighbour, current);
                    queue.add(neighbour);
                }
                if (neighbour == target) {
                    return new Path(getPathFrom(current, parent, target), visited);
                }
            }
        }
        return null;
    }
}
