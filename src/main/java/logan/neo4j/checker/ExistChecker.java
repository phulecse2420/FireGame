package logan.neo4j.checker;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class ExistChecker {

    protected ExistChecker checker;

    private static ExistChecker initInstance () {
        var defaultChecker = new DefaultChecker(null);
        var hash128Checker = new Hash128Checker(defaultChecker);
        var hash64Checker  = new Hash64Checker(hash128Checker);
        return new Hash32Checker(hash64Checker);
    }

    protected abstract boolean executeCheck (String cypher);

    public static boolean check (String cypher) {
        if ( null == cypher || cypher.isBlank() ) {
            return true;
        }
        return Holder.INSTANCE.executeCheck(cypher);
    }

    private static class Holder {
        private static final ExistChecker INSTANCE = ExistChecker.initInstance();
    }
}
