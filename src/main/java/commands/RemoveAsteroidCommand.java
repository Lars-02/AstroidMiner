package commands;

import models.Asteroid;
import models.Galaxy;

import java.util.Random;

public class RemoveAsteroidCommand extends Command {
    public RemoveAsteroidCommand(Galaxy galaxy) {
        super(galaxy, "Remove Asteroid");
    }

    @Override
    public void execute() {
        var asteroids = galaxy.getEntities().stream().filter(entity -> entity instanceof Asteroid).toList();

        if (asteroids.isEmpty())
            return;

        var random = new Random();
        galaxy.removeEntity(asteroids.get(random.nextInt(asteroids.size())));
    }
}
