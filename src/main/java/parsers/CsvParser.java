package parsers;

import java.util.*;

public class CsvParser {
    public static List<Map<String, String>> parse(String fileContents) {
        var data = new ArrayList<Map<String, String>>();

        var lines = fileContents.split("\n");
        var header = lines[0];
        var dataLines = Arrays.copyOfRange(lines, 1, lines.length);

        var names = header.split(";");

        for (var line : dataLines) {
            var values = line.split(";");
            var map = new HashMap<String, String>();

            for (int i = 0; i < values.length; i++) {
                map.put(names[i], values[i]);
            }

            data.add(map);
        }

        return data;
    }
}
