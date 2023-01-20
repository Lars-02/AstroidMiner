package commands;

import main.FlatGalaxySociety;
import models.Asteroid;
import states.behaviourrules.BounceBehaviourRule;
import ui.model.Config;

import java.util.Random;

import static models.Color.BLACK;

public class AddAsteroidCommand extends Command {
    public AddAsteroidCommand(FlatGalaxySociety game) {
        super(game, "Add Asteroid");
    }

    @Override
    public void execute() {
        var random = new Random();
        var radius = random.nextInt(3) + 1;
        var asteroid = new Asteroid(
                random.nextDouble(Config.SCREEN_WIDTH - 2 * radius) + radius,
                random.nextDouble(Config.SCREEN_HEIGHT - 2 * radius) + radius,
                random.nextDouble(20) - 10,
                random.nextDouble(20) - 10, radius, BLACK
        );

        asteroid.addBehaviourRule(new BounceBehaviourRule(asteroid));
        game.galaxy.addEntity(asteroid);
    }
}
