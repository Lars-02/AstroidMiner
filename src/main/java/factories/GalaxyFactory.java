package factories;

import exceptions.filereader.FileReaderException;
import exceptions.galaxyparser.GalaxyParserException;
import factories.filereader.FileReader;
import models.Galaxy;
import parsers.GalaxyParser;

public class GalaxyFactory {
    public static Galaxy fromFile(String fileLocation) throws FileReaderException, GalaxyParserException {
        var fileReader = FileReader.getFileReader(fileLocation);
        var fileContent = fileReader.readFile(fileLocation);

        var fileExtension = fileLocation.substring(fileLocation.lastIndexOf('.') + 1);
        var fileParser = GalaxyParser.getParser(fileExtension);

        return fileParser.parse(fileContent);
    }
}
