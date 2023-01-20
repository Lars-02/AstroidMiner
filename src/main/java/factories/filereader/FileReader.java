package factories.filereader;

import exceptions.filereader.FileReaderException;

public interface FileReader {
    static FileReader getFileReader(String fileLocation) {
        if (fileLocation.startsWith("http"))
            return new RemoteFileReader();
        else
            return new LocalFileReader();
    }

    static String getFileType(String fileLocation) {
        var cutoffLength = fileLocation.indexOf('?');

        if (cutoffLength == -1)
            cutoffLength = fileLocation.length();

        return fileLocation
                .substring(0, cutoffLength)
                .substring(fileLocation.lastIndexOf('.') + 1);
    }

    String readFile(String path) throws FileReaderException;
}
