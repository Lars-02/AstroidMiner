package exceptions.galaxyparser;

public class InvalidDataException extends GalaxyParserException {
    public InvalidDataException(Exception exception) {
        super(exception);
    }
}
