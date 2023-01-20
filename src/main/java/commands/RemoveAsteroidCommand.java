package commands;

import main.FlatGalaxySociety ;
import models.Asteroid;

import java.util.Random;

public class RemoveAsteroidCommand extends Command {
    public RemoveAsteroidCommand(FlatGalaxySociety game) {
        super(game, "Remove Asteroid");
    }

    @Override
    public void execute() {
        var asteroids = game.galaxy.getEntities().stream().filter(entity -> entity instanceof Asteroid).toList();

        if (asteroids.isEmpty())
            return;

        var random = new Random();
        game.galaxy.removeEntity(asteroids.get(random.nextInt(asteroids.size())));
    }
}
