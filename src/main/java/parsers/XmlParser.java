package parsers;

import enums.OnCollision;
import exceptions.galaxyparser.GalaxyParserException;
import exceptions.galaxyparser.InvalidDataException;
import exceptions.galaxyparser.InvalidEntityTypeException;
import factories.EntityFactory;
import factories.GalaxyBuilder;
import models.Color;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class XmlParser implements GalaxyParser {
    private String getStringValue(Element element, String tag) {
        return getElement(element, tag).getTextContent();
    }

    private List<String> getStringValues(Element parentElement, String tag) {
        return getElements(parentElement, tag).stream().map(Node::getTextContent).toList();
    }

    private double getDoubleValue(Element parentElement, String tag) {
        return Double.parseDouble(getStringValue(parentElement, tag));
    }

    private int getIntValue(Element parentElement, String tag) {
        return Integer.parseInt(getStringValue(parentElement, tag));
    }

    private Element getElement(Element parentElement, String tag) {
        var elements = getElements(parentElement, tag);

        if (elements.size() == 0)
            return null;

        return elements.get(0);
    }

    private List<Element> getElements(Element parentElement, String tag) {
        var nodes = parentElement.getElementsByTagName(tag);
        var elements = new ArrayList<Element>();

        for (int i = 0; i < nodes.getLength(); i++) {
            elements.add((Element) nodes.item(i));
        }

        return elements;
    }

    private List<String> getNeighbours(Element planet) {
        var neighboursElement = getElement(planet, "neighbours");

        if (neighboursElement == null)
            return List.of();

        var neighbourNodes = neighboursElement.getElementsByTagName("planet");
        var neighbourNames = new ArrayList<String>();

        for (var i = 0; i < neighbourNodes.getLength(); i++) {
            neighbourNames.add(neighbourNodes.item(i).getTextContent());
        }

        return neighbourNames;
    }

    @Override
    public GalaxyBuilder parse(String fileContents) throws GalaxyParserException {
        try {
            var galaxyBuilder = new GalaxyBuilder();

            var dbFactory = DocumentBuilderFactory.newInstance();
            var dBuilder = dbFactory.newDocumentBuilder();
            var doc = dBuilder.parse(new InputSource(new StringReader(fileContents)));

            doc.getDocumentElement().normalize();
            var galaxyElement = doc.getDocumentElement();
            var entityNodes = galaxyElement.getChildNodes();

            for (int planetNodeIndex = 0; planetNodeIndex < entityNodes.getLength(); planetNodeIndex++) {
                var entityNode = entityNodes.item(planetNodeIndex);
                if (entityNode.getNodeType() != Node.ELEMENT_NODE) continue;

                var entityElement = (Element) entityNode;
                var type = entityElement.getNodeName();

                var positionElement = getElement(entityElement, "position");
                var speedElement = getElement(entityElement, "speed");

                var entityFactory = new EntityFactory(
                        getDoubleValue(positionElement, "x"),
                        getDoubleValue(positionElement, "y"),
                        getDoubleValue(speedElement, "x"),
                        getDoubleValue(speedElement, "y"),
                        getIntValue(positionElement, "radius"),
                        Color.valueOf(getStringValue(entityElement, "color"))
                );

                getStringValues(entityElement, "oncollision")
                        .stream()
                        .map(OnCollision::parseOnCollision)
                        .forEach(entityFactory::addOnCollision);


                var entity = switch (type) {
                    case "planet" -> entityFactory.createPlanet(getStringValue(entityElement, "name"));
                    case "asteroid" -> entityFactory.createAsteroid();
                    default -> throw new InvalidEntityTypeException(type);
                };

                galaxyBuilder.addEntity(entity, getNeighbours(entityElement));
            }

            return galaxyBuilder;
        } catch (ParserConfigurationException | IOException | SAXException | NullPointerException |
                 IllegalArgumentException e) {
            throw new InvalidDataException(e);
        }
    }
}
