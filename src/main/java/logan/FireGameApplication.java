package logan;

import logan.model.GameStatus;
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
        var gameStatus        = GameStatus.initGameStatus(false, false, false, false, false);
        var times             = 1;
        var expectStackLength = 15;
        var performanceHelper = new PerformanceHelper(times);
        performanceHelper.executeTest(() -> {
            var resolver = ResolverFactory.createResolver(ResolverType.BFS);
            return resolver.execute(gameStatus, expectStackLength).getRuntime();
        });
    }


}
