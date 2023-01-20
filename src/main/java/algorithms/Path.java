package algorithms;

import models.Planet;

import java.util.List;

public record Path(List<Planet> nodes, List<Planet> visited) {
}
