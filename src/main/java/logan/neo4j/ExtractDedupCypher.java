package logan.neo4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import logan.neo4j.checker.ExistChecker;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExtractDedupCypher {

    public static void main (String[] args) {
        String inputFilePath        = "neo4j/neo4j_cypher_log_2024_10_18.csv";
        long   timestamp            = System.currentTimeMillis();
        String outputFileFullPath   = "neo4j/full_" + timestamp + ".csv";
        String outputFileCypherPath = "neo4j/cypher_" + timestamp + ".csv";

        try (
            BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
            BufferedWriter bwFull = new BufferedWriter(new FileWriter(outputFileFullPath));
            BufferedWriter bwCypher = new BufferedWriter(new FileWriter(outputFileCypherPath));
        ) {
            String line;
            while ( (line = br.readLine()) != null ) {
                var cypher = handleLine(line);
                if ( null != cypher ) {
                    writeToOutputFile(bwFull, line);
                    writeToOutputFile(bwCypher, cypher);
                }
            }
        }
        catch (IOException e) {
            log.error("Read file error", e);
        }
    }

    private static String handleLine (String line) {
        String cypher = extractCypher(line);
        if ( isNotExist(cypher) ) {
            return cypher;
        }
        return null;
    }

    private static void writeToOutputFile (BufferedWriter bw, String line) throws IOException {
        bw.write(line);
        bw.newLine();
        bw.flush();
    }

    private static String extractCypher (String line) {
        return CypherExtractor.extract(line);
    }

    private static boolean isNotExist (String cypher) {
        return !ExistChecker.check(cypher);
    }

}
