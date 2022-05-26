package logan.resolver;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import logan.model.GameStatus;
import logan.utils.RangeUtil;
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
    protected void solve (GameStatus gameStatus) {
        RangeUtil.init(gameStatus.getFires().length);
        queue.offer(gameStatus);
        super.solve(gameStatus);
    }

    void solve () {
        while ( !queue.isEmpty() ) {
            var status = queue.poll();
            if ( status.isFinish() ) {
                if ( isBetterStatus(status) ) {
                    bestResolver = status;
                    log.info("Find a better solution with cost [{}] move [{}].", status.getCost(), status.getMoves());
                }
            }
            else {
                status.generateChildren().filter(c -> checkedStatus.add(c.hashCode())).forEach(this.queue::offer);
            }
        }
    }

    private boolean isBetterStatus (GameStatus status) {
        if ( null != bestResolver ) {
            if ( bestResolver.getCost() > status.getCost() ) {
                return true;
            }
            else if ( bestResolver.getCost() == status.getCost() ) {
                return bestResolver.getMoves() > status.getMoves();
            }
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
