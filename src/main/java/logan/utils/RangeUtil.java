package logan.utils;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RangeUtil {

    private static List<Integer> range;

    public static void init (int length) {
        range = IntStream.rangeClosed(1, length).boxed().collect(toList());
    }

    public static Stream<Integer> getStream () {
        Collections.shuffle(range, new Random(System.nanoTime()));
        return range.stream();
    }

}