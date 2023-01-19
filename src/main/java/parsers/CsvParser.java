package parsers;

import exceptions.galaxyparser.InvalidEntityTypeException;
import factories.EntityFactory;
import factories.GalaxyBuilder;
import javafx.scene.paint.Color;

import java.util.*;

public class CsvParser implements GalaxyParser {
    private List<Map<String, String>> toMapList(String fileContents) {
        var data = new ArrayList<Map<String, String>>();

        var lines = fileContents.split("\n");
        var header = lines[0];
        var dataLines = Arrays.copyOfRange(lines, 1, lines.length);

        var names = header.split(";");

        for (var line : dataLines) {
            var values = line.split(";");
            var map = new HashMap<String, String>();

            for (int i = 0; i < values.length; i++) {
                map.put(names[i], values[i]);
            }

            data.add(map);
        }

        return data;
    }


    @Override
    public GalaxyBuilder parse(String fileContents) throws InvalidEntityTypeException {
        var data = toMapList(fileContents);

        var galaxyBuilder = new GalaxyBuilder();

        for (var entityMap : data) {
            var entityFactory = new EntityFactory(
                    Double.parseDouble(entityMap.get("x")),
                    Double.parseDouble(entityMap.get("y")),
                    Double.parseDouble(entityMap.get("vy")),
                    Double.parseDouble(entityMap.get("vy")),
                    Integer.parseInt(entityMap.get("radius")),
                    Color.valueOf(entityMap.get("color"))
            );

            var type = entityMap.get("type");

            var entity = switch (type) {
                case "Planet" -> entityFactory.createPlanet(entityMap.get("name"));
                case "Asteroid" -> entityFactory.createAsteroid();
                default -> throw new InvalidEntityTypeException(type);
            };

            galaxyBuilder.addEntity(entity);
        }

        return galaxyBuilder;
    }
}
