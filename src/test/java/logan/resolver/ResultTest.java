package logan.resolver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import logan.model.GameStatus;

class ResultTest {

    private static final int EXPECT_MOVES = 20;

    @Test
    void noMoveTest () {
        var gameStatus = GameStatus.initGameStatus(new boolean[] { true, true, true, true, true });
        executeTest(gameStatus, 0);
    }

    private void executeTest (final GameStatus gameStatus, final int bestResultMoves) {
        Arrays.stream(ResolverType.values()).forEach(type -> {
            var resolver = ResolverFactory.createResolver(type);
            var result   = resolver.execute(gameStatus, EXPECT_MOVES);
            assertEquals(bestResultMoves, result.getBestGameStatus().getStackLength());
        });
    }

    @Test
    void oneMoveTest () {
        var gameStatus = GameStatus.initGameStatus(new boolean[] { false, false, false, true, true });
        executeTest(gameStatus, 1);
    }

    @Test
    void eightFiresTest () {
        var gameStatus = GameStatus.initGameStatus(new boolean[] { false, true, false, true, true, false, true, true });
        executeTest(gameStatus, 5);
    }

    @Test
    void tenFiresTest1 () {
        var gameStatus = GameStatus.initGameStatus(
            new boolean[] { false, true, false, true, true, false, true, true, true, true });
        executeTest(gameStatus, 5);
    }

    @Test
    void tenFiresTest2 () {
        var gameStatus = GameStatus.initGameStatus(
            new boolean[] { false, false, false, false, false, false, false, false, false, false });
        executeTest(gameStatus, 10);
    }
}