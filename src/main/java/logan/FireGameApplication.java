package logan;

import logan.model.GameStatus;
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
            new boolean[] { false, false, false, false, false, false, false, false, false });
        var times             = 1;
        var expectStackLength = 20;
        var performanceHelper = new PerformanceHelper(times);
        performanceHelper.executeTest(() -> {
            var resolver = new DfsResolver();
            return resolver.execute(gameStatus.build(), expectStackLength);
        });
    }


}
