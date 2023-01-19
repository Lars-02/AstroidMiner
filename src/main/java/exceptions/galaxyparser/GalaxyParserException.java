package exceptions.galaxyparser;

public class GalaxyParserException extends Exception {
    protected GalaxyParserException(Exception exception) {
        super(exception);
    }

    protected GalaxyParserException(String exception) {
        super(exception);
    }
}
