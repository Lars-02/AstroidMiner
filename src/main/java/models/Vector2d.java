package models;

import java.io.Serializable;

public class Vector2d implements Serializable {
    public double x;
    public double y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d middle(Vector2d other) {
        return add(other.sub(this).div(2));
    }

    public double dist(Vector2d other) {
        return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
    }

    public Vector2d mul(double n) {
        return new Vector2d(x * n, y * n);
    }

    public Vector2d div(double n) {
        return new Vector2d(x / n, y / n);
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(x + other.x, y + other.y);
    }

    public Vector2d sub(Vector2d other) {
        return new Vector2d(x - other.x, y - other.y);
    }
}
