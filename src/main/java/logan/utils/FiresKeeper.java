package logan.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import logan.model.GameStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FiresKeeper {

    private static final Map<Integer, GameStatus> gameStatusMap = new HashMap<>();

    public static GameStatus generateGameStatus (boolean... fires) {
        return gameStatusMap.computeIfAbsent(Arrays.hashCode(fires), k -> GameStatus.initGameStatus(fires));
    }
}
