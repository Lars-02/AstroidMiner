package exceptions.galaxyparser;

public class InvalidFileTypeException extends GalaxyParserException {
    public InvalidFileTypeException(String fileExtension) {
        super("File extension \"" + fileExtension + "\" is not supported.");
    }
}
