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

import java.util.Arrays;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TouchHelper {

    public static boolean[] touch (final int index, final boolean... fires) {
        validateInput(fires, index);
        var result = Arrays.copyOf(fires, fires.length);
        invert(result, index);
        invert(result, getBeforeIndex(index, fires.length));
        invert(result, getNextIndex(index, fires.length));
        return result;
    }

    private static void validateInput (boolean[] fires, int index) {
        Objects.requireNonNull(fires, "The fires is required.");
        if ( fires.length < 1 ) {
            throw new IllegalArgumentException("The fires is required.");
        }
        if ( fires.length < index || index < 1 ) {
            throw new IllegalArgumentException("Invalid touch index " + index);
        }
    }

    private static int getNextIndex (int index, int maxLength) {
        var result = index + 1;
        if ( result > maxLength ) {
            result = result - maxLength;
        }
        return result;
    }

    private static int getBeforeIndex (int index, int maxLength) {
        var result = index - 1;
        if ( result < 1 ) {
            result = result + maxLength;
        }
        return result;
    }

    private static void invert (boolean[] fires, int index) {
        fires[index - 1] = !fires[index - 1];
    }

}
