package models;

import java.io.Serializable;
import java.util.List;

public record GalaxyState(List<Entity> entities) implements Serializable {
}
