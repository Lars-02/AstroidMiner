package factories.filereader;

import exceptions.filereader.LocalFileReaderException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LocalFileReader implements FileReader {
    @Override
    public String readFile(String localPath) throws LocalFileReaderException {
        var path = Paths.get(localPath);

        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new LocalFileReaderException(e);
        }
    }
}
