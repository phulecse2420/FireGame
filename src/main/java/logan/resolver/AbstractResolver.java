package logan.resolver;

import logan.model.GameStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractResolver {

    public long execute (GameStatus gameStatus, int expectedMovesNumber) {
        log.info("Fire Game start...");
        var startTimestamp = System.currentTimeMillis();
        solve(gameStatus, expectedMovesNumber);
        var runtime = System.currentTimeMillis() - startTimestamp;
        log.info("Fire Game end in [{}] ms.", runtime);
        return runtime;
    }

    protected abstract void solve (GameStatus gameStatus, int expectedMovesNumber);
}
