package logan.resolver;

import logan.model.GamePrinter;
import logan.model.GameResult;
import logan.model.GameStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Resolver {

    protected int          expectedMovesNumber;
    protected int          expectedCost;
    protected ResolverType type;
    protected GameStatus   bestResolver;

    public GameResult execute (GameStatus gameStatus, int expectedMovesNumber) {
        return execute(gameStatus, expectedMovesNumber, Integer.MAX_VALUE);
    }

    public GameResult execute (GameStatus gameStatus, int expectedMovesNumber, int expectedCost) {
        log.info("[{}] resolver starts...", type);
        var startTimestamp = System.currentTimeMillis();
        this.expectedMovesNumber = expectedMovesNumber;
        this.expectedCost = expectedCost;
        solve(gameStatus);
        var runtime = System.currentTimeMillis() - startTimestamp;
        log.info("[{}] resolver ends in [{}] ms.", type, runtime);
        return new GameResult(bestResolver, runtime);
    }

    protected void solve (GameStatus gameStatus) {
        solve();
        if ( log.isInfoEnabled() ) {
            logResult();
        }
    }

    abstract void solve ();

    protected int getExpectMoves () {
        if ( null == bestResolver ) {
            return expectedMovesNumber;
        }
        return bestResolver.getMoves();
    }

    protected void logResult () {
        if ( null != bestResolver ) {
            log.info("The best solution {}", GamePrinter.generateResolveTrace(bestResolver));
        }
        else {
            log.info("No solution found.");
        }
    }
}
