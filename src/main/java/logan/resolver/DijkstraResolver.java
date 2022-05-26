////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/*
 * Copyright © 2022 Unified Social, Inc.
 * 180 Madison Avenue, 23rd Floor, New York, NY 10016, U.S.A.
 * All rights reserved.
 *
 * This software (the "Software") is provided pursuant to the license agreement you entered into with Unified Social,
 * Inc. (the "License Agreement").  The Software is the confidential and proprietary information of Unified Social,
 * Inc., and you shall use it only in accordance with the terms and conditions of the License Agreement.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND "AS AVAILABLE."  UNIFIED SOCIAL, INC. MAKES NO WARRANTIES OF ANY KIND, WHETHER
 * EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO THE IMPLIED WARRANTIES AND CONDITIONS OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT.
 */

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

package logan.resolver;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import logan.model.DijkstraGameStatus;
import logan.model.GameStatus;
import logan.utils.RangeUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DijkstraResolver extends Resolver {

    private final Queue<GameStatus>        queue;
    private final Map<Integer, GameStatus> checkedStatus;

    DijkstraResolver () {
        this.queue = new LinkedList<>();
        this.checkedStatus = new HashMap<>();
        this.type = ResolverType.DIJKSTRA;
    }

    @Override
    protected void solve (GameStatus gameStatus) {
        RangeUtil.init(gameStatus.getFires().length);
        var dijkstraGameStatus = new DijkstraGameStatus(gameStatus.getFires());
        this.queue.offer(dijkstraGameStatus);
        this.checkedStatus.put(dijkstraGameStatus.hashCode(), dijkstraGameStatus);
        super.solve(dijkstraGameStatus);
    }

    @Override
    void solve () {
        while ( !queue.isEmpty() ) {
            var status = queue.poll();
            log.debug("Checking [{}] CheckedStatus size [{}]", status.hashCode(), checkedStatus.size());
            if ( status.isFinish() ) {
                if ( isBetterStatus(status) ) {
                    bestResolver = status;
                    log.info("Find a better solution with cost [{}] move [{}].", status.getCost(), status.getMoves());
                }
            }
            else {
                status.generateChildren()
                      .filter(c -> !checkedStatus.containsKey(c.hashCode()))
                      .forEach(c -> {
                          this.queue.offer(c);
                          this.checkedStatus.put(c.hashCode(), c);
                      });
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
