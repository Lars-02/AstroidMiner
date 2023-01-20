package models;

import java.util.ArrayList;
import java.util.List;

public class Planet extends Entity {
    public final String name;
    private final List<Planet> neighbours = new ArrayList<>();

    public Planet(String name, double x, double y, double velocityX, double velocityY, int radius, Color color) {
        super(x, y, velocityX, velocityY, radius, color);
        this.name = name;
    }

    public List<Planet> getNeighbours() {
        return neighbours;
    }

    public void addNeighbour(Planet neighbour) {
        this.neighbours.add(neighbour);
    }

    public void removeConnections() {
        for (var neighbour : neighbours) {
            neighbour.getNeighbours().remove(this);
        }
    }
}
