package logan.model;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import logan.utils.RangeUtil;
import logan.utils.TouchHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
public class GameStatus {

    @Getter
    protected final boolean[]  fires;
    @Getter
    protected       GameStatus parent;
    @Getter
    protected       Integer    step;
    @Getter
    protected       int        moves;
    protected       Integer    cost;
    protected       Integer    hashCode;

    protected GameStatus (boolean... fires) {
        this(fires, null, null, 0, 0, null);
    }

    @Builder
    public static GameStatus initGameStatus (boolean... fires) {
        if ( null == fires ) {
            throw new IllegalArgumentException("Invalid input");
        }
        return new GameStatus(fires);
    }

    protected GameStatus (GameStatus parent, int step, boolean[] childFires) {
        this.fires = childFires;
        this.parent = parent;
        this.step = step;
        this.moves = parent.moves + 1;
    }

    public int getCost () {
        if ( null == cost ) {
            cost = calculateCost();
        }
        return cost;
    }

    private Integer calculateCost () {
        if ( null == parent ) {
            return 0;
        }
        return getStepCost() + parent.getCost();
    }

    public int getStepCost () {
        if ( null == parent ) {
            return 0;
        }
        return parent.fires[step - 1] ? 0 : 1;
    }

    public boolean isFinish () {
        for (var fire : fires) {
            if ( !fire ) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals (Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        GameStatus that = (GameStatus) o;
        return Arrays.equals(fires, that.fires);
    }

    @Override
    public int hashCode () {
        if ( null == hashCode ) {
            hashCode = Arrays.hashCode(fires);
        }
        return hashCode;
    }

    @Override
    public String toString () {
        return Arrays.toString(fires);
    }

    public Stream<? extends GameStatus> generateChildren () {
        return RangeUtil.getStream()
                        .filter(i -> !Objects.equals(i, step))
                        .map(index -> {
                            var childFires = TouchHelper.touch(index, fires);
                            return new GameStatus(this, index, childFires);
                        });
    }
}
