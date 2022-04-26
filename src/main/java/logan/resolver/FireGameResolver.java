package logan.resolver;

import java.util.Deque;
import java.util.LinkedList;

import logan.model.GameStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FireGameResolver {

    private final Deque<GameStatus> stack;
    private final int               expectStackLength;
    private       GameStatus        bestResolver;

    private FireGameResolver (int expectStackLength) {
        this.stack = new LinkedList<>();
        this.expectStackLength = expectStackLength;
    }

    public static void solve (GameStatus gameStatus, int expectStackLength) {
        var resolver = new FireGameResolver(expectStackLength);
        resolver.stack.add(gameStatus);
        resolver.solve();
        if ( log.isInfoEnabled() ) {
            resolver.logResult();
        }
    }

    private void solve () {
        while ( !stack.isEmpty() ) {
            var status = stack.pop();
            if ( status.isFinish() ) {
                if ( getExpectStackLength(bestResolver) > status.getStackLength() ) {
                    bestResolver = status;
                    log.info("Find a better solution with [{}] moves.", status.getStackLength());
                }
            }
            else if ( getExpectStackLength(bestResolver) > status.getStackLength() + 1 ) {
                status.generateChildren().forEach(this.stack::push);
            }

        }
    }

    private int getExpectStackLength (GameStatus bestResolver) {
        if ( null == bestResolver ) {
            return expectStackLength;
        }
        return bestResolver.getStackLength();
    }

    private void logResult () {
        if ( null != bestResolver ) {
            log.info("The best solution {}", bestResolver.generateResolveTrace());
        }
        else {
            log.info("No solution found.");
        }
    }

}
