package logan.resolver;

import java.util.Deque;
import java.util.LinkedList;

import logan.model.GameStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class DfsResolver extends Resolver {

    private final Deque<GameStatus> stack;

    DfsResolver () {
        this.stack = new LinkedList<>();
        this.type = ResolverType.DFS;
    }

    protected void solve (GameStatus gameStatus, int expectStackLength) {
        this.expectedMovesNumber = expectStackLength;
        stack.add(gameStatus);
        solve();
        if ( log.isInfoEnabled() ) {
            logResult();
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

}
