package logan.model;

import lombok.Data;

@Data
public class GameResult {

    private final GameStatus bestGameStatus;
    private final long       runtime;

}
