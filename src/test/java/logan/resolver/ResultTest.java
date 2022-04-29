package logan.resolver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import logan.model.GameStatus;

class ResultTest {

    private static final int EXPECT_MOVES = 20;

    @Test
    void noMoveTest () {
        var gameStatus = GameStatus.initGameStatus(new Boolean[] { true, true, true, true, true });
        executeTest(gameStatus, 0);
    }

    private void executeTest (final GameStatus gameStatus, final int bestResultMoves) {
        Arrays.stream(ResolverType.values()).forEach(type -> {
            var resolver = ResolverFactory.createResolver(type);
            var result   = resolver.execute(gameStatus, EXPECT_MOVES);
            assertNotNull(result.getBestGameStatus());
            assertEquals(bestResultMoves, result.getBestGameStatus().getMoves());
        });
    }

    @Test
    void oneMoveTest () {
        var gameStatus = GameStatus.initGameStatus(new Boolean[] { false, false, false, true, true });
        executeTest(gameStatus, 1);
    }

    @Test
    void eightFiresTest () {
        var gameStatus = GameStatus.initGameStatus(new Boolean[] { false, true, false, true, true, false, true, true });
        executeTest(gameStatus, 5);
    }

    @Test
    void tenFiresTest1 () {
        var gameStatus = GameStatus.initGameStatus(
            new Boolean[] { false, true, false, true, true, false, true, true, true, true });
        executeTest(gameStatus, 5);
    }

    @Test
    void tenFiresTest2 () {
        var gameStatus = GameStatus.initGameStatus(
            new Boolean[] { false, false, false, false, false, false, false, false, false, false });
        executeTest(gameStatus, 10);
    }
}