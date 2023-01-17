package factories;

import exceptions.FileReaderException;
import factories.filereader.FileReader;
import factories.filereader.LocalFileReader;
import factories.filereader.RemoteFileReader;
import models.Galaxy;

public class GalaxyFactory {
    public static Galaxy fromFile(String fileLocation) throws FileReaderException {
        FileReader fileReader;
        if (fileLocation.startsWith("http"))
            fileReader = new RemoteFileReader();
        else
            fileReader = new LocalFileReader();

        var fileContent = fileReader.readFile(fileLocation);

        return null; // TODO: Construct factory
    }
}
