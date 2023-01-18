package factories.filereader;

import exceptions.filereader.FileReaderException;

public interface FileReader {
    String readFile(String path) throws FileReaderException;

    static FileReader getFileReader(String fileLocation) {
        if (fileLocation.startsWith("http"))
            return new RemoteFileReader();
        else
            return new LocalFileReader();
    }
}
