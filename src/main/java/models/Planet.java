package models;

import javafx.scene.paint.Color;
import states.entity_states.EntityState;

import java.util.List;

public class Planet extends Entity {
    public Planet(String name,  List<Planet> neighbours,  double x, double y, double velocityX, double velocityY, int radius, Color color) {
        super(x, y, velocityX, velocityY, radius, color);
        this.name = name;
        this.neighbours = neighbours;
    }

    public final String name;

    public final List<Planet> neighbours;
}
