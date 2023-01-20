package models;

import java.io.Serializable;

public class Color implements Serializable {
    public static final Color BLACK = new Color(javafx.scene.paint.Color.BLACK);
    public static final Color WHITE = new Color(javafx.scene.paint.Color.WHITE);
    private final double red;
    private final double green;
    private final double blue;
    private final double alpha;

    public Color(javafx.scene.paint.Color color) {
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.alpha = color.getOpacity();
    }

    public Color(double red, double green, double blue, double alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public static Color valueOf(String value) {
        return new Color(javafx.scene.paint.Color.valueOf(value));
    }

    public Color interpolate(Color endValue, double t) {
        return new Color(getFXColor().interpolate(endValue.getFXColor(), t));
    }

    public javafx.scene.paint.Color getFXColor() {
        return new javafx.scene.paint.Color(red, green, blue, alpha);
    }
}