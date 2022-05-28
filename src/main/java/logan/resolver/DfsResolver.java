package logan.resolver;

import java.util.Deque;
import java.util.LinkedList;

import logan.model.GameStatus;
import logan.utils.RangeUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class DfsResolver extends Resolver {

    private final Deque<GameStatus> stack;

    DfsResolver () {
        this.stack = new LinkedList<>();
        this.type = ResolverType.DFS;
    }

    @Override
    protected void solve (GameStatus gameStatus) {
        RangeUtil.init(gameStatus.getFires().length);
        stack.add(gameStatus);
        solve();
    }

    void solve () {
        while ( !stack.isEmpty() ) {
            var status = stack.pop();
            if ( status.isFinish() ) {
                if ( getExpectMoves(bestResolver) > status.getMoves() ) {
                    bestResolver = status;
                    log.info("Find a better solution with cost [{}] move [{}].", status.getCost(), status.getMoves());
                }
            }
            else if ( getExpectMoves(bestResolver) > status.getMoves() + 1 ) {
                status.generateChildren().forEach(this.stack::push);
            }

        }
    }

}
