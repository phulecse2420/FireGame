package logan.neo4j.checker;

import java.util.HashSet;
import java.util.Set;

class DefaultChecker extends ExistChecker {

    private final Set<Integer> hashCodes = new HashSet<>();

    public DefaultChecker (ExistChecker checker) {
        super(checker);
    }

    @Override
    protected boolean executeCheck (String cypher) {
        return !hashCodes.add(cypher.hashCode());
    }

}
