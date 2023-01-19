package parsers;

import exceptions.galaxyparser.GalaxyParserException;
import exceptions.galaxyparser.InvalidFileTypeException;
import factories.GalaxyBuilder;

public interface GalaxyParser {
    static GalaxyParser getParser(String fileExtension) throws InvalidFileTypeException {
        return switch (fileExtension) {
            case "xml" -> new XmlParser();
            case "csv" -> new CsvParser();
            default -> throw new InvalidFileTypeException(fileExtension);
        };
    }

    GalaxyBuilder parse(String fileContents) throws GalaxyParserException;
}
