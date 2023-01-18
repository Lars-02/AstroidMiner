package exceptions.filereader;

public class FileReaderException extends Exception {
    protected FileReaderException(Exception exception) {
        super(exception);
    }
}
