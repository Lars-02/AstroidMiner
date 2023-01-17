package exceptions;

public class FileReaderException extends Exception {
    protected FileReaderException(Exception exception) {
        super(exception);
    }
}
