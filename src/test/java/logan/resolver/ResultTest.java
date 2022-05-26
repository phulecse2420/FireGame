package logan.resolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import logan.model.GameStatus;

class ResultTest {

    private static final int EXPECT_MOVES = 20;

    @Test
    void noMoveTest () {
        var gameStatus = GameStatus.initGameStatus(true, true, true, true, true);
        executeTest(gameStatus, 0, 0, ResolverType.DFS, ResolverType.DIJKSTRA);
    }

    private void executeTest (final GameStatus gameStatus, final int bestResultMoves, final int bestCost,
                              final ResolverType... skipTypes) {
        for (int i = 0; i < 10; i++) {
            Arrays.stream(ResolverType.values())
                  .filter(t -> Arrays.stream(skipTypes).allMatch(s -> s != t))
                  .forEach(type -> {
                      var resolver = ResolverFactory.createResolver(type);
                      var result   = resolver.execute(gameStatus, EXPECT_MOVES);
                      assertNotNull(result.getBestGameStatus());
                      assertEquals(bestResultMoves, result.getBestGameStatus().getMoves());
                      assertEquals(bestCost, result.getBestGameStatus().getCost());
                  });
        }
    }

    @Test
    void noSolutionFound () {
        var gameStatus = GameStatus.initGameStatus(false, false, true);
        Arrays.stream(ResolverType.values()).forEach(type -> {
            var resolver = ResolverFactory.createResolver(type);
            var result   = resolver.execute(gameStatus, EXPECT_MOVES);
            assertNull(result.getBestGameStatus());
        });
    }

    @Test
    void fiveFiresTest () {
        var gameStatus = GameStatus.initGameStatus(false, false, false, false, false);
        executeTest(gameStatus, 5, 2, ResolverType.DFS, ResolverType.DIJKSTRA);
        gameStatus = GameStatus.initGameStatus(true, false, false, false, false);
        executeTest(gameStatus, 2, 2, ResolverType.DFS, ResolverType.DIJKSTRA);
        gameStatus = GameStatus.initGameStatus(true, true, false, false, false);
        executeTest(gameStatus, 2, 2, ResolverType.DFS, ResolverType.DIJKSTRA);
        gameStatus = GameStatus.initGameStatus(true, false, true, false, false);
        executeTest(gameStatus, 2, 2, ResolverType.DFS, ResolverType.DIJKSTRA);
    }

    @Test
    void twoMovesTest2 () {
        var gameStatus = GameStatus.initGameStatus(false, false, false, false, false, false);
        executeTest(gameStatus, 2, 2, ResolverType.DFS, ResolverType.DIJKSTRA);
    }

    @Test
    void eightFiresTest () {
        var gameStatus = GameStatus.initGameStatus(false, true, false, true, true, false, true, true);
        executeTest(gameStatus, 5, 0, ResolverType.DFS, ResolverType.DIJKSTRA);
    }

    @Test
    void tenFiresTest1 () {
        var gameStatus = GameStatus.initGameStatus(false, true, false, true, true, false, true, true, true, true);
        executeTest(gameStatus, 5, 0, ResolverType.DFS, ResolverType.DIJKSTRA);
    }

    @Test
    void tenFiresTest2 () {
        var gameStatus = GameStatus.initGameStatus(
            false, false, false, false, false, false, false, false, false, false);
        executeTest(gameStatus, 10, 0, ResolverType.DFS, ResolverType.DIJKSTRA);
    }
}