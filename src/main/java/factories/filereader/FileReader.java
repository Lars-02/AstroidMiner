package factories.filereader;

import exceptions.FileReaderException;

public interface FileReader {
    public String readFile(String path) throws FileReaderException;
}
