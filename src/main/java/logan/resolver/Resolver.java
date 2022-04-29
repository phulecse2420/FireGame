package logan.resolver;

import logan.model.GameResult;
import logan.model.GameStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Resolver {

    protected int        expectedMovesNumber;
    protected ResolverType type;
    protected GameStatus bestResolver;

    public GameResult execute (GameStatus gameStatus, int expectedMovesNumber) {
        log.info("[{}] resolver starts...", type);
        var startTimestamp = System.currentTimeMillis();
        solve(gameStatus, expectedMovesNumber);
        var runtime = System.currentTimeMillis() - startTimestamp;
        log.info("[{}] resolver ends in [{}] ms.", type, runtime);
        return new GameResult(bestResolver, runtime);
    }

    protected void solve (GameStatus gameStatus, int expectedMovesNumber) {
        this.expectedMovesNumber = expectedMovesNumber;
        solve();
        if ( log.isInfoEnabled() ) {
            logResult();
        }
    }

    abstract void solve();

    protected int getExpectMoves (GameStatus bestResolver) {
        if ( null == bestResolver ) {
            return expectedMovesNumber;
        }
        return bestResolver.getMoves();
    }

    protected void logResult () {
        if ( null != bestResolver ) {
            log.info("The best solution {}", bestResolver.generateResolveTrace());
        }
        else {
            log.info("No solution found.");
        }
    }
}
