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

package logan.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;

import logan.model.GameStatus;

class FiresKeeperTest {

    @Test
    void test () throws NoSuchFieldException, IllegalAccessException {
        var gameStatusMapField = FiresKeeper.class.getDeclaredField("gameStatusMap");
        gameStatusMapField.setAccessible(true);
        Map<Integer, GameStatus> gameStatusMap = (Map<Integer, GameStatus>) gameStatusMapField.get(null);

        var gameStatus = FiresKeeper.generateGameStatus(true, true);
        assertEquals(1, gameStatusMap.size());
        assertEquals(gameStatus, gameStatusMap.get(gameStatus.hashCode()));

        FiresKeeper.generateGameStatus(true, true);
        assertEquals(1, gameStatusMap.size());
        assertEquals(gameStatus, gameStatusMap.get(gameStatus.hashCode()));

        FiresKeeper.generateGameStatus(true, false);
        assertEquals(2, gameStatusMap.size());
        assertEquals(gameStatus, gameStatusMap.get(gameStatus.hashCode()));

    }

}
