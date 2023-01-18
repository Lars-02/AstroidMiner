package parsers;

import enums.OnCollision;
import exceptions.galaxyparser.GalaxyParserException;
import exceptions.galaxyparser.InvalidDataException;
import exceptions.galaxyparser.InvalidEntityTypeException;
import javafx.scene.paint.Color;
import models.Asteroid;
import models.Galaxy;
import models.Planet;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

public class XmlParser implements GalaxyParser {
    private String getStringValue(Element element, String tag) {
        return getElement(element, tag).getTextContent();
    }

    private double getDoubleValue(Element element, String tag) {
        return Double.parseDouble(getStringValue(element, tag));
    }

    private int getIntValue(Element element, String tag) {
        return Integer.parseInt(getStringValue(element, tag));
    }

    private Element getElement(Element element, String tag) {
        return (Element) element.getElementsByTagName(tag).item(0);
    }

    @Override
    public Galaxy parse(String fileContents) throws GalaxyParserException {
        try {
            var galaxy = new Galaxy();

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
                var positionX = getDoubleValue(positionElement, "x");
                var positionY = getDoubleValue(positionElement, "y");
                var radius = getIntValue(positionElement, "radius");

                var speedElement = getElement(entityElement, "speed");
                var speedX = getDoubleValue(speedElement, "x");
                var speedY = getDoubleValue(speedElement, "y");

                var color = Color.valueOf(getStringValue(entityElement, "color"));
                var oncollision = OnCollision.parseOnCollision(getStringValue(entityElement, "oncollision"));

                var entity = switch (type) {
                    case "planet" -> new Planet(
                            galaxy,
                            getStringValue(entityElement, "name"),
                            positionX,
                            positionY,
                            speedX,
                            speedY,
                            radius,
                            color
                    );
                    case "asteroid" -> new Asteroid(
                            galaxy,
                            positionX,
                            positionY,
                            speedX,
                            speedY,
                            radius,
                            color
                    );
                    default -> throw new InvalidEntityTypeException(type);
                };

                galaxy.addEntity(entity);
            }

            return galaxy;
        } catch (ParserConfigurationException | IOException | SAXException | NullPointerException |
                 IllegalArgumentException e) {
            throw new InvalidDataException(e);
        }
    }
}
