package logan.model;

import java.util.Arrays;
import java.util.stream.Stream;

import logan.utils.RangeUtil;
import logan.utils.TouchHelper;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GameStatus {

    private static final String BORDER = "\n==================================================================";
    private static       int    maxLength;

    @Getter
    private final boolean[]  fires;
    @Getter
    private final GameStatus parent;
    @Getter
    private final Integer    step;
    @Getter
    private final int        moves;
    protected     Integer    cost;
    private       Integer    hashCode;

    private GameStatus (boolean[] fires) {
        this(fires, null, null, 0);
    }

    protected GameStatus (GameStatus parent, int step, boolean[] childFires) {
        this.fires = childFires;
        this.parent = parent;
        this.step = step;
        this.moves = parent.moves + 1;
    }

    @Builder
    public static GameStatus initGameStatus (boolean... fires) {
        if ( null == fires ) {
            throw new IllegalArgumentException("Invalid input");
        }
        maxLength = fires.length;
        return new GameStatus(fires);
    }

    public boolean isFinish () {
        for (var fire : fires) {
            if ( !fire ) {
                return false;
            }
        }
        return true;
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

    public Stream<GameStatus> generateChildren () {
        return generateChildrenWithoutCheckSolutionPath().filter(child -> isNotExistInSolutionPath(child.hashCode()));
    }

    public Stream<GameStatus> generateChildrenWithoutCheckSolutionPath () {
        return RangeUtil.getStream().map(index -> {
            var childFires = TouchHelper.touch(index, fires);
            return new GameStatus(this, index, childFires);
        });
    }

    private boolean isNotExistInSolutionPath (int childHashCode) {
        var result   = true;
        var ancestor = this.parent;
        while ( null != ancestor ) {
            if ( ancestor.hashCode() == childHashCode ) {
                result = false;
                break;
            }
            ancestor = ancestor.parent;
        }
        return result;
    }

}
