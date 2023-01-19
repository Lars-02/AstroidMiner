package factories.filereader;

import exceptions.filereader.RemoteFileReaderException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class RemoteFileReader implements FileReader {
    @Override
    public String readFile(String remotePath) throws RemoteFileReaderException {
        try {
            var url = new URL(remotePath);
            var input = url.openStream();

            var scanner = new Scanner(input);
            scanner.useDelimiter("\\A");

            var fileContent = scanner.hasNext() ? scanner.next() : "";

            scanner.close();

            return fileContent;
        } catch (MalformedURLException e) {
            throw new RemoteFileReaderException(e);
        } catch (IOException e) {
            throw new RemoteFileReaderException(e);
        }
    }
}
