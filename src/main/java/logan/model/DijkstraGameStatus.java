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

package logan.model;

import java.util.Objects;
import java.util.stream.Stream;

import logan.utils.RangeUtil;
import logan.utils.TouchHelper;

public class DijkstraGameStatus extends GameStatus {

    public DijkstraGameStatus (boolean... fires) {
        super(fires);
    }

    protected DijkstraGameStatus (DijkstraGameStatus dfsGameStatus, int index, boolean[] childFires) {
        super(dfsGameStatus, index, childFires);
    }

    @Override
    public Stream<DijkstraGameStatus> generateChildren () {
        return RangeUtil.getStream()
                        .filter(i -> !Objects.equals(i, step))
                        .map(index -> {
                            var childFires = TouchHelper.touch(index, fires);
                            return new DijkstraGameStatus(this, index, childFires);
                        });
    }

}
