package commands;

import algorithms.BreadthFirstSearchAlgorithm;
import algorithms.DijkstraAlgorithm;
import main.FlatGalaxySociety;

public class SwitchPathFindingAlgorithmCommand extends Command {
    public SwitchPathFindingAlgorithmCommand(FlatGalaxySociety game) {
        super(game, "Pathfinding Mode");
    }

    @Override
    public void execute() {
        if (game.galaxy.pathfindingAlgorithm instanceof DijkstraAlgorithm) {
            game.galaxy.pathfindingAlgorithm = new BreadthFirstSearchAlgorithm();
            return;
        }
        game.galaxy.pathfindingAlgorithm = new DijkstraAlgorithm();
    }
}
