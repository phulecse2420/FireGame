package logan;

import logan.model.GameStatus;
import logan.resolver.BfsResolver;
import logan.resolver.DfsResolver;
import logan.util.PerformanceHelper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FireGameApplication {

    public static void main (String[] args) {
        var gameStatus = GameStatus.builder().input(
            new boolean[] { false, true, false, true, true, false, true, true});
        var times             = 1;
        var expectStackLength = 15;
        var performanceHelper = new PerformanceHelper(times);
        performanceHelper.executeTest(() -> {
            var resolver = new BfsResolver();
            return resolver.execute(gameStatus.build(), expectStackLength).getRuntime();
        });
    }


}
