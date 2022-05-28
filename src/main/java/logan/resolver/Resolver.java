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
        if ( log.isInfoEnabled() ) {
            logResult();
        }
        var runtime = System.currentTimeMillis() - startTimestamp;
        log.info("[{}] resolver ends in [{}] ms.", type, runtime);
        return new GameResult(bestResolver, runtime);
    }

    abstract void solve (GameStatus gameStatus);

    protected void logResult () {
        if ( null != bestResolver ) {
            log.info("The best solution {}", GamePrinter.generateResolveTrace(bestResolver));
        }
        else {
            log.info("No solution found.");
        }
    }

    protected boolean isBetterStatus (GameStatus status) {
        if ( null != bestResolver ) {
            return GameStatus.compare(bestResolver, status);
        }
        else {
            if ( expectedCost > status.getCost() ) {
                return true;
            }
            else if ( expectedCost == status.getCost() ) {
                return expectedMovesNumber > status.getMoves();
            }
        }
        return false;
    }
}
