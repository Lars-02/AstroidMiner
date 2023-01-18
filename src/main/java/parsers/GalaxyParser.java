package parsers;

import exceptions.galaxyparser.GalaxyParserException;
import exceptions.galaxyparser.InvalidFileTypeException;
import models.Galaxy;

public interface GalaxyParser {
    static GalaxyParser getParser(String fileExtension) throws InvalidFileTypeException {
        return switch (fileExtension) {
            case "xml" -> new XmlParser();
            case "csv" -> new CsvParser();
            default -> throw new InvalidFileTypeException(fileExtension);
        };
    }

    Galaxy parse(String fileContents) throws GalaxyParserException;
}
