package exceptions.galaxyparser;

public class InvalidEntityTypeException extends GalaxyParserException {
    public InvalidEntityTypeException(String entityName) {
        super("An invalid entity type \"" + entityName + "\" was found.");
    }
}
