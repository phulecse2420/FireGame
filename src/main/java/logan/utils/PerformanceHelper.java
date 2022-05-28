package logan.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongSupplier;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PerformanceHelper {

    private final int        times;
    private final List<Long> executeTimes;

    public PerformanceHelper (int times) {
        this.times = times;
        this.executeTimes = new ArrayList<>(times);
    }

    public void executeTest (LongSupplier longSupplier) {
        for (int i = 0; i < times; i++) {
            if ( times > 1 ) {
                log.warn("Test time [{}]", i);
                System.gc();
            }
            executeTimes.add(longSupplier.getAsLong());
        }
        if ( times > 1 ) {
            log.warn("Min [{}] ms", executeTimes.stream().mapToLong(i -> i).min().orElse(0));
            log.warn("Max [{}] ms", executeTimes.stream().mapToLong(i -> i).max().orElse(0));
            log.warn("Average [{}] ms", executeTimes.stream().mapToLong(i -> i).average().orElse(0));
        }
    }
}
