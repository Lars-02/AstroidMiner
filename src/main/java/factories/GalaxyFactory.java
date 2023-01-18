package factories;

import exceptions.filereader.FileReaderException;
import factories.filereader.FileReader;
import factories.filereader.LocalFileReader;
import factories.filereader.RemoteFileReader;
import models.Galaxy;
import parsers.CsvParser;
import parsers.XmlParser;

import java.util.List;
import java.util.Map;

public class GalaxyFactory {
    public static Galaxy fromFile(String fileLocation) throws FileReaderException {
        var fileReader = FileReader.getFileReader(fileLocation);
        var fileContent = fileReader.readFile(fileLocation);

        List<Map<String, String>> data;

        if (fileLocation.endsWith("xml"))
            data = XmlParser.parse(fileContent, "planet");
        else
            data = CsvParser.parse(fileContent);

//        var data = CsvParser.parse(fileContent);
//        var data = CsvParser.parse(fileContent);

        for (var map : data) {
            for (String s : map.keySet()) {
                System.out.println(s);
                System.out.println(map.get(s));
                System.out.println("------");
            }
        }

        return null; // TODO: Construct factory
    }
}
