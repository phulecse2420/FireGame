package logan.resolver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import logan.model.GameStatus;
import logan.utils.RangeUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class BfsResolver extends Resolver {

    private final Queue<GameStatus>        queue;
    private final Map<Integer, GameStatus> checkedStatus;

    BfsResolver () {
        this.queue = new LinkedList<>();
        this.checkedStatus = new HashMap<>();
        this.type = ResolverType.BFS;
    }

    @Override
    protected void solve (GameStatus gameStatus) {
        RangeUtil.init(gameStatus.getFires().length);
        queue.offer(gameStatus);
        solve();
    }

    void solve () {
        while ( !queue.isEmpty() ) {
            var status = queue.poll();
            log.debug("Check... [{}] checkedStatus size [{}]", status.hashCode(), checkedStatus.size());
            if ( status.isFinish() ) {
                if ( isBetterStatus(status) ) {
                    bestResolver = status;
                    log.info("Find a better solution with cost [{}] move [{}].", status.getCost(), status.getMoves());
                }
            }
            else {
                status.generateChildren()
                      .forEach(c -> {
                          var current = checkedStatus.get(c.hashCode());
                          if (null == current || GameStatus.compare(current, c)) {
                              checkedStatus.put(c.hashCode(), c);
                              queue.offer(c);
                          }
                      });
            }
        }
    }

}
