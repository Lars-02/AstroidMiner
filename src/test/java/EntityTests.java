import models.Color;
import models.Entity;
import models.Galaxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EntityTests {
    private static class TestEntity extends Entity {

        public TestEntity(double x, double y, double velocityX, double velocityY, int radius, Color color) {
            super(x, y, velocityX, velocityY, radius, color);
        }
    }

    Galaxy galaxy;

    Entity entity;
    Entity bounceEntity1;
    Entity bounceEntity2;
    Entity collidingEntity1;
    Entity collidingEntity2;
    Entity collidingEntity3;
    Entity notCollidingEntity1;
    Entity notCollidingEntity2;

    @BeforeEach
    void setUp() {
        entity = new TestEntity(5, 5, 1, 1, 2, Color.BLACK);
        bounceEntity1 = new TestEntity(1, 5, -1, 1, 2, Color.BLACK);
        bounceEntity2 = new TestEntity(5, 2, -1, -1, 2, Color.BLACK);
        collidingEntity1 = new TestEntity(5, 5, 0, 0, 2, Color.BLACK);
        collidingEntity2 = new TestEntity(5, 8, 0, 0, 2, Color.BLACK);
        collidingEntity3 = new TestEntity(9, 7, 0, 0, 5, Color.BLACK);
        notCollidingEntity1 = new TestEntity(10, 10, 0, 0, 2, Color.BLACK);
        notCollidingEntity2 = new TestEntity(9, 5, 0, 0, 1, Color.BLACK);

        galaxy = new Galaxy();
        galaxy.addEntity(entity);
        galaxy.addEntity(collidingEntity1);
        galaxy.addEntity(collidingEntity2);
        galaxy.addEntity(collidingEntity3);
        galaxy.addEntity(notCollidingEntity1);
        galaxy.addEntity(notCollidingEntity2);
    }

    @Test
    @DisplayName("collidedWith should work")
    void collidedWith() {
        entity.checkForCollisionsOn(galaxy, galaxy.getEntities());

        assertTrue(entity.collidedWith(collidingEntity1));
        assertTrue(entity.isCollidingWith(collidingEntity1));
        assertTrue(entity.collidedWith(collidingEntity2));
        assertTrue(entity.collidedWith(collidingEntity3));
        assertFalse(entity.collidedWith(notCollidingEntity1));
        assertFalse(entity.collidedWith(notCollidingEntity1));

        assertTrue(entity.isColliding());

        entity.translate(4000);

        entity.checkForCollisionsOn(galaxy, galaxy.getEntities());
        assertFalse(entity.collidedWith(collidingEntity1));

    }

    @Test
    @DisplayName("Regular translation should work")
    void regularTranslations() {
        assertEquals(5, entity.position.x);
        assertEquals(5, entity.position.y);

        entity.translate(1000);

        assertEquals(6, entity.position.x);
        assertEquals(6, entity.position.y);

        entity.translate(500);

        assertEquals(6.5, entity.position.x);
        assertEquals(6.5, entity.position.y);
    }

    @Test
    @DisplayName("Bounce translation should work")
    void bounceTranslations() {
        bounceEntity1.translate(1000);

        assertEquals(2, bounceEntity1.position.x);
        assertEquals(6, bounceEntity1.position.y);

        bounceEntity1.translate(500);

        assertEquals(2.5, bounceEntity1.position.x);
        assertEquals(6.5, bounceEntity1.position.y);

        bounceEntity2.translate(1000);

        assertEquals(4, bounceEntity2.position.x);
        assertEquals(2, bounceEntity2.position.y);
    }

    @Test
    @DisplayName("isCollidingWith should work")
    void isCollidingWith() {
        assertTrue(entity.isCollidingWith(collidingEntity1));
        assertFalse(entity.isCollidingWith(notCollidingEntity1));

        entity.translate(4000);

        assertFalse(entity.isCollidingWith(collidingEntity1));
    }
}
