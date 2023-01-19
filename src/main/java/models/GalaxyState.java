package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public record GalaxyState(
        HashMap<Entity, ArrayList<Entity>> collisions,
        List<Entity> entities,
        List<Entity> removeEntityList,
        List<Entity> addEntityList) implements Serializable {
}
