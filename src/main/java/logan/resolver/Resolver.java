package logan.resolver;

import logan.model.GameResult;
import logan.model.GameStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Resolver {

    protected int        expectedMovesNumber;
    protected GameStatus bestResolver;

    public GameResult execute (GameStatus gameStatus, int expectedMovesNumber) {
        log.info("Fire Game start...");
        var startTimestamp = System.currentTimeMillis();
        solve(gameStatus, expectedMovesNumber);
        var runtime = System.currentTimeMillis() - startTimestamp;
        log.info("Fire Game end in [{}] ms.", runtime);
        return new GameResult(bestResolver, runtime);
    }

    protected abstract void solve (GameStatus gameStatus, int expectedMovesNumber);

    protected int getExpectStackLength (GameStatus bestResolver) {
        if ( null == bestResolver ) {
            return expectedMovesNumber;
        }
        return bestResolver.getStackLength();
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
