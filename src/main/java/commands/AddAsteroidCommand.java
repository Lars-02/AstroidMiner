package commands;

import models.Asteroid;
import models.Galaxy;
import states.behaviourrules.BounceBehaviourRule;
import ui.Renderer;

import java.util.Random;

import static models.Color.BLACK;

public class AddAsteroidCommand extends Command {
    public AddAsteroidCommand(Galaxy galaxy) {
        super(galaxy, "Add Asteroid");
    }

    @Override
    public void execute() {
        Random random = new Random();
        var radius = random.nextInt(3) + 1;
        var asteroid = new Asteroid(random.nextDouble(Renderer.ScreenWidth - 2 * radius) + radius, random.nextDouble(Renderer.ScreenHeight - 2 * radius) + radius, random.nextDouble(20) - 10, random.nextDouble(20) - 10, radius, BLACK);
        asteroid.addBehaviourRule(new BounceBehaviourRule(asteroid));
        galaxy.addEntity(asteroid);
    }
}
