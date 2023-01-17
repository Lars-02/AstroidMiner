package models;

import javafx.scene.paint.Color;
import states.entity_states.EntityState;

import java.util.List;

public class Planet extends Entity {
    Planet(double x, double y, double velocityX, double velocityY, int radius, Color color) {
        super(x, y, velocityX, velocityY, radius, color);
    }

    private String name;

    private List<Planet> neighbours;
}
