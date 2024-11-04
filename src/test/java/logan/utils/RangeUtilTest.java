package logan.utils;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RangeUtilTest {

    @Test
    void test () {
        for (int i = 0; i < 50; i++) {
            RangeUtil.init(i);
            var list = RangeUtil.getStream().collect(toList());
            assertEquals(i, list.size());
            for (int j = 1; j <= i; j++) {
                assertTrue(list.contains(j));
            }
        }
    }
}
