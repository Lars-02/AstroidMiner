package enums;

public enum OnCollision {
    BLINK(),
    BOUNCE(),
    DISAPPEAR(),
    EXPLODE(),
    GROW(),
    ;

    public static OnCollision parseOnCollision(String value) {
        return valueOf(OnCollision.class, value.toUpperCase());
    }
}