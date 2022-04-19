package logan;

import java.util.LinkedList;

import logan.model.GameStatus;
import logan.resolver.FireGameResolver;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FireGameApplication {

    public static void main (String[] args) {
        var gameStatus = GameStatus.builder().input(
            new boolean[] { false, true, false, true, true, false, true, true });
        var times      = 1;
        var runtimes   = new LinkedList<Long>();
        for (int i = 0; i < times; i++) {
            runtimes.add(execute(gameStatus.build()));
            log.warn("Test time [{}]", i);
            System.gc();
        }
        log.warn("Min [{}] ms", runtimes.stream().mapToLong(i -> i).min().orElse(0));
        log.warn("Max [{}] ms", runtimes.stream().mapToLong(i -> i).max().orElse(0));
        log.warn("Average [{}] ms", runtimes.stream().mapToLong(i -> i).average().orElse(0));
    }

    private static long execute (GameStatus gameStatus) {
        log.info("Fire Game start...");
        var startTimestamp = System.currentTimeMillis();
        FireGameResolver.solve(gameStatus);
        var runtime = System.currentTimeMillis() - startTimestamp;
        log.info("Fire Game end in [{}] ms.", runtime);
        return runtime;
    }

}
