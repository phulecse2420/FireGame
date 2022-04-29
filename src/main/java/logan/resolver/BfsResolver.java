package logan.resolver;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import logan.model.GameStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class BfsResolver extends Resolver {

    private final Queue<GameStatus> queue;
    private final Set<Integer>      checkedStatus;

    BfsResolver () {
        this.queue = new LinkedList<>();
        this.checkedStatus = new HashSet<>();
        this.type = ResolverType.BFS;
    }

    @Override
    protected void solve (GameStatus gameStatus, int expectedMovesNumber) {
        this.expectedMovesNumber = expectedMovesNumber;
        queue.offer(gameStatus);
        solve();
        if ( log.isInfoEnabled() ) {
            logResult();
        }
    }

    private void solve () {
        while ( !queue.isEmpty() ) {
            var status = queue.poll();
            if ( status.isFinish() ) {
                if ( getExpectMoves(bestResolver) > status.getMoves() ) {
                    bestResolver = status;
                    log.info("Find a better solution with [{}] moves.", status.getMoves());
                }
            }
            else if ( getExpectMoves(bestResolver) > status.getMoves() + 1 ) {
                status.generateChildrenWithoutCheckSolutionPath()
                      .filter(g -> checkedStatus.add(g.hashCode()))
                      .forEach(this.queue::offer);
            }
        }
    }


}
