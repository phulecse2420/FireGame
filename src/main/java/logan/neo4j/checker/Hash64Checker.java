package logan.neo4j.checker;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class Hash64Checker extends ExistChecker {

    private final HashFunction hash;
    private final Set<Long>    hashCodes;

    public Hash64Checker (ExistChecker checker) {
        super(checker);
        hash = Hashing.goodFastHash(64);
        hashCodes = new HashSet<>();
    }

    @Override
    protected boolean executeCheck (String cypher) {
        var hashcode = hash.hashString(cypher, StandardCharsets.UTF_8);
        return !hashCodes.add(hashcode.asLong()) || checker.executeCheck(cypher);
    }

}
