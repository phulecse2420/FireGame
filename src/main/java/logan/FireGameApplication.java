package logan;

import logan.model.GameStatus;
import logan.resolver.ResolverConfig;
import logan.resolver.ResolverFactory;
import logan.resolver.ResolverType;
import logan.utils.PerformanceHelper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FireGameApplication {

    public static void main (String[] args) {
        var config = ResolverConfig.builder().maxMoves(10).maxCost(10).numberOfResult(2).build();
        var gameStatus = GameStatus.initGameStatus(
            false, false, false, false, false, false, true, false, false, false);
        var times             = 1;
        var performanceHelper = new PerformanceHelper(times);
        performanceHelper.executeTest(() -> {
            var resolver = ResolverFactory.createResolver(ResolverType.BFS, config);
            return resolver.execute(gameStatus).getRuntime();
        });
    }


}
