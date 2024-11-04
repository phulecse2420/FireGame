package logan.neo4j;

public class SubExtractor {

    private static final String context = " - {";

    public static String extract (String line) {
        var index = line.indexOf(context);
        return line.substring(0, index);
    }
}
