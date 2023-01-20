import models.Vector2d;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VectorTests {
    @Test
    @DisplayName("equals should work")
    void equals() {
        var vec1 = new Vector2d(1, 1);

        assertEquals(vec1, new Vector2d(1, 1));
        assertNotEquals(vec1, new Vector2d(1, 2));
        assertNotEquals(vec1, new Vector2d(2, 1));
    }

    @Test
    @DisplayName("Add should work")
    void add() {
        var vec1 = new Vector2d(1, 1);
        var vec2 = new Vector2d(2, 2);

        assertEquals(vec1.add(vec2), new Vector2d(3, 3));
    }

    @Test
    @DisplayName("Sub should work")
    void sub() {
        var vec1 = new Vector2d(3, 3);
        var vec2 = new Vector2d(2, 2);

        assertEquals(vec1.sub(vec2), new Vector2d(1, 1));
    }

    @Test
    @DisplayName("Mul should work")
    void mul() {
        var vec = new Vector2d(3, 3);

        assertEquals(vec.mul(2), new Vector2d(6, 6));
    }

    @Test
    @DisplayName("Div should work")
    void div() {
        var vec = new Vector2d(4, 4);

        assertEquals(vec.div(2), new Vector2d(2, 2));
    }
}
