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