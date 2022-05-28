package logan.resolver;

import java.util.LinkedList;

import logan.model.GamePrinter;
import logan.model.GameStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Resolver {

    protected final ResolverConfig config;
    protected final ResolverType type;
    protected final LinkedList<GameStatus> resultList;

    private GameStatus bestResolver;

    protected Resolver (ResolverConfig config, ResolverType type) {
        this.config = config;
        this.type = type;
        if ( config.getNumberOfResult() > 1 ) {
            resultList = new LinkedList<>();
        }
        else {
            resultList = null;
        }
    }

    public GameStatus execute (GameStatus gameStatus) {
        log.info("[{}] resolver starts...", type);
        solve(gameStatus);
        if ( log.isInfoEnabled() ) {
            logResult();
        }
        log.info("[{}] resolver ends.", type);
        return bestResolver;
    }

    abstract void solve (GameStatus gameStatus);

    protected void logResult () {
        if ( null != bestResolver ) {
            if ( config.getNumberOfResult() > 1 ) {
                var descendingIterator = resultList.descendingIterator();
                for (int i = 0; i < config.getNumberOfResult() && descendingIterator.hasNext(); i++) {
                    var result = descendingIterator.next();
                    log.info(
                        "The result [{}] with cost [{}] move [{}] {}", i, result.getCost(), result.getMoves(),
                        GamePrinter.generateResolveTrace(result)
                    );
                }
            }
            else {
                log.info("The best solution {}", GamePrinter.generateResolveTrace(bestResolver));
            }
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
            if ( config.getMaxCost() > status.getCost() ) {
                return true;
            }
            else if ( config.getMaxCost() == status.getCost() ) {
                return config.getMaxMoves() > status.getMoves();
            }
        }
        return false;
    }

    protected void setBestResolver (GameStatus bestResolver) {
        if (config.getNumberOfResult() > 1) {
            resultList.add(bestResolver);
        }
        this.bestResolver = bestResolver;
    }
}
