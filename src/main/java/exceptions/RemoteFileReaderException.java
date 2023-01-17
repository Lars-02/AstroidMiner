package exceptions;

import java.io.IOException;
import java.net.MalformedURLException;

public class RemoteFileReaderException extends FileReaderException {
    public RemoteFileReaderException(IOException exception) {
        super(exception);
    }

    public RemoteFileReaderException(MalformedURLException exception) {
        super(exception);
    }
}
