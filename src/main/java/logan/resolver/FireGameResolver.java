package logan.resolver;

import java.util.Deque;
import java.util.LinkedList;

import logan.model.GameStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FireGameResolver {

    private final Deque<GameStatus> stack;
    private       GameStatus        bestResolver;

    private FireGameResolver () {
        this.stack = new LinkedList<>();
    }

    public static void solve (GameStatus gameStatus) {
        var resolver = new FireGameResolver();
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
                if ( null == bestResolver || bestResolver.getStackLength() > status.getStackLength() ) {
                    bestResolver = status;
                    log.info("Find a better solution with [{}] moves.", status.getStackLength());
                }
            }
            else {
                if ( null == bestResolver || bestResolver.getStackLength() > status.getStackLength() + 1 ) {
                    status.generateChildren().forEach(this.stack::push);
                }
            }
        }
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
