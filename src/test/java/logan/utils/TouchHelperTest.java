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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TouchHelperTest {

    @Test
    void testInvalid () {
        Exception ex = assertThrows(NullPointerException.class, () -> TouchHelper.touch(1, null));
        assertEquals("The fires is required.", ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, () -> TouchHelper.touch(2, new boolean[] {}));
        assertEquals("The fires is required.", ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, () -> TouchHelper.touch(2, true));
        assertEquals("Invalid touch index 2", ex.getMessage());

        ex = assertThrows(IllegalArgumentException.class, () -> TouchHelper.touch(-1, true));
        assertEquals("Invalid touch index -1", ex.getMessage());
    }

    @Test
    void testResult () {
        assertArrayEquals(new boolean[] { true, false, true }, TouchHelper.touch(1, false, true, false));
        assertArrayEquals(new boolean[] { true, false, true }, TouchHelper.touch(2, false, true, false));
        assertArrayEquals(new boolean[] { true, false, true }, TouchHelper.touch(3, false, true, false));
        assertArrayEquals(new boolean[] { true, false, true, true }, TouchHelper.touch(2, false, true, false, true));
        assertArrayEquals(new boolean[] { true, false, false, false }, TouchHelper.touch(1, false, true, false, true));
    }
}
