package algorithms;

import models.Planet;

import java.util.*;

public class DijkstraAlgorithm extends PathfindingAlgorithm {

    @Override
    protected Path getPath(List<Planet> nodes, Planet source, Planet target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Source and target nodes cannot be null");
        }
        if (source == target) {
            return new Path(new ArrayList<>(), new ArrayList<>());
        }
        Map<Planet, Double> distance = new HashMap<>();
        Map<Planet, Planet> parent = new HashMap<>();
        List<Planet> visited = new ArrayList<>();
        PriorityQueue<Planet> queue = new PriorityQueue<>(new PlanetComparator(distance));
        for (Planet node : nodes) {
            distance.put(node, Double.POSITIVE_INFINITY);
        }
        distance.put(source, 0.0);
        queue.add(source);
        parent.put(source, null);
        while (!queue.isEmpty()) {
            Planet current = queue.poll();
            visited.add(current);
            if (current == target) {
                return new Path(getPathFrom(current, parent), visited);
            }
            for (Planet neighbour : current.getNeighbours()) {
                double newDistance = distance.get(current) + current.position.dist(neighbour.position);
                if (newDistance < distance.get(neighbour)) {
                    distance.put(neighbour, newDistance);
                    parent.put(neighbour, current);
                    queue.add(neighbour);
                }
            }
        }
        return null;
    }

    private static class PlanetComparator implements Comparator<Planet> {
        Map<Planet, Double> distance;

        public PlanetComparator(Map<Planet, Double> distance) {
            this.distance = distance;
        }

        @Override
        public int compare(Planet planet1, Planet planet2) {
            return Double.compare(distance.get(planet1), distance.get(planet2));
        }
    }
}
