package models;

import javafx.scene.paint.Color;
import states.entity_states.EntityState;

import java.util.ArrayList;
import java.util.List;

public class Planet extends Entity {
    public Planet(String name, double x, double y, double velocityX, double velocityY, int radius, Color color) {
        super(x, y, velocityX, velocityY, radius, color);
        this.name = name;
    }

    public final String name;

    private final List<Planet> neighbours = new ArrayList<>();

    public List<Planet> getNeighbours() {
        return neighbours;
    }

    public void addNeighbour(Planet neighbour) {
        this.neighbours.add(neighbour);
    }

    public void addConnectionNeighbour(Planet neighbour) {
        this.neighbours.add(neighbour);
        neighbour.addNeighbour(this);
    }
}
