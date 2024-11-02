package logan.neo4j;

import java.util.regex.Pattern;

public class CypherExtractor {

    private static Pattern PATTERN = Pattern.compile(
        "\\-\\s*\\-\\s*(MATCH|CALL|UNWIND|WITH|USING PERIODIC).*( - \\{)(0:|} )");

    public static String extract (String line) {
        var matcher = PATTERN.matcher(line);
        return matcher.find() ? matcher.group() : null;
    }
}
