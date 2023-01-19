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

        var fileType = FileReader.getFileType(fileLocation);
        var galaxyParser = GalaxyParser.getParser(fileType);

        return galaxyParser.parse(fileContent);
    }
}
