package logan.resolver;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import logan.model.GameStatus;

class ResultTest {

    private static final int EXPECT_MOVES = 20;

    @Test
    void noMoveTest () {
        var gameStatus = GameStatus.initGameStatus(new boolean[] { true, true, true, true, true });
        test(gameStatus, 0);
    }

    private void test (GameStatus gameStatus, int bestResultMoves) {
        assertTrue(Arrays.stream(ResolverType.values()).allMatch(type -> {
            var resolver = ResolverFactory.createResolver(type);
            var result   = resolver.execute(gameStatus, EXPECT_MOVES);
            return result.getBestGameStatus().getStackLength() == bestResultMoves;
        }));
    }

    @Test
    void oneMoveTest () {
        var gameStatus = GameStatus.initGameStatus(new boolean[] { false, false, false, true, true });
        test(gameStatus, 1);
    }
}