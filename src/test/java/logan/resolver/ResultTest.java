package logan.resolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import logan.model.GameStatus;

class ResultTest {

    private static ResolverConfig CONFIG = ResolverConfig.builder().maxMoves(10).maxCost(10).numberOfResult(2).build();

    @Test
    void noSolutionFound () {
        var gameStatus = GameStatus.initGameStatus(false, false, true);
        Arrays.stream(ResolverType.values()).forEach(type -> {
            var resolver = ResolverFactory.createResolver(type, CONFIG);
            var result   = resolver.execute(gameStatus);
            assertNull(result.getBestGameStatus());
        });
    }

    @Test
    void noMoveTest () {
        var gameStatus = GameStatus.initGameStatus(true, true, true, true, true);
        executeTest(gameStatus, 0, 0);
    }

    private void executeTest (final GameStatus gameStatus, final int bestResultMoves, final int bestCost,
                              final ResolverType... skipTypes) {
        for (int i = 0; i < 10; i++) {
            Arrays.stream(ResolverType.values())
                  .filter(t -> Arrays.stream(skipTypes).allMatch(s -> s != t))
                  .forEach(type -> {
                      var resolver = ResolverFactory.createResolver(type, CONFIG);
                      var result   = resolver.execute(gameStatus);
                      assertNotNull(result.getBestGameStatus());
                      assertEquals(bestResultMoves, result.getBestGameStatus().getMoves());
                      assertEquals(bestCost, result.getBestGameStatus().getCost());
                  });
        }
    }

    @Test
    void fiveFiresTest () {
        var gameStatus = GameStatus.initGameStatus(false, false, false, false, false);
        executeTest(gameStatus, 5, 2);
        gameStatus = GameStatus.initGameStatus(true, false, false, false, false);
        executeTest(gameStatus, 4, 1);
        gameStatus = GameStatus.initGameStatus(true, true, false, false, false);
        executeTest(gameStatus, 1, 1);
        gameStatus = GameStatus.initGameStatus(true, false, true, false, false);
        executeTest(gameStatus, 3, 1);
    }

    @Test
    void eightFiresTest () {
        var gameStatus = GameStatus.initGameStatus(false, true, false, true, true, false, true, true);
        executeTest(gameStatus, 9, 1);
    }

    @Test
    void tenFiresTest () {
        var gameStatus = GameStatus.initGameStatus(false, true, false, true, true, false, true, true, true, true);
        executeTest(gameStatus, 7, 1);
        gameStatus = GameStatus.initGameStatus(
            false, false, false, false, false, false, false, false, false, false);
        executeTest(gameStatus, 10, 2);
    }
}