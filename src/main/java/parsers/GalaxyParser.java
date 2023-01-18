package parsers;

import models.Galaxy;

public interface GalaxyParser {
    Galaxy parse(String fileContents);
}
