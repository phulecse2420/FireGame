package logan.neo4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RepeatedRelationshipChecker {

    private static final Pattern PATTERN = Pattern.compile("\\[\\w*:?\\w*\\|?\\w*(\\*\\d..)?\\]");

    public static void main (String[] args) {
        String inputFilePath      = "neo4j/sorted.csv";
        String outputFileFullPath = "neo4j/repeated_" + System.currentTimeMillis() + ".csv";

        try (
            BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
            BufferedWriter bwFull = new BufferedWriter(new FileWriter(outputFileFullPath))
        ) {
            String line;
            while ( (line = br.readLine()) != null ) {
                var cypher = handleLine(line);
                if ( null != cypher ) {
                    writeToOutputFile(bwFull, line);
                }
            }
        }
        catch (IOException e) {
            log.error("Read file error", e);
        }
    }

    private static String handleLine (String line) {
        var matcher = PATTERN.matcher(line);
        while ( matcher.find() ) {
            var relationship = matcher.group();
            if ( countOccurrences(line, relationship) > 1 ) {
                return line;
            }
        }
        return null;
    }

    public static int countOccurrences (String str, String subStr) {
        int count = 0;
        int index = 0;

        while ( (index = str.indexOf(subStr, index)) != -1 ) {
            count++;
            index += subStr.length();
        }

        return count;
    }

    private static void writeToOutputFile (BufferedWriter bw, String line) throws IOException {
        bw.write(line);
        bw.newLine();
        bw.flush();
    }
}
