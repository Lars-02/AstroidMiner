package exceptions.filereader;

import java.io.IOException;

public class LocalFileReaderException extends FileReaderException {
    public LocalFileReaderException(IOException exception) {
        super(exception);
    }
}
