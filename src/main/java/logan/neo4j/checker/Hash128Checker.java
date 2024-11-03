package logan.neo4j.checker;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

class Hash128Checker extends ExistChecker {

    private final HashFunction hash;
    private final Set<Long>    hashCodes;

    public Hash128Checker (ExistChecker checker) {
        super(checker);
        hash = Hashing.goodFastHash(128);
        hashCodes = new HashSet<>();
    }

    @Override
    protected boolean executeCheck (String cypher) {
        var hashcode = hash.hashString(cypher, StandardCharsets.UTF_8);
        var subCheck = checker.executeCheck(cypher);
        return !hashCodes.add(hashcode.asLong()) || subCheck;
    }

}
