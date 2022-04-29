package logan.model;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GameStatus {

    private static final String BORDER = "\n==================================================================";
    private static       int    maxLength;

    private final Boolean[]  fires;
    private final GameStatus parent;
    private final Integer    step;
    @Getter
    private final int        moves;
    private       Integer    hashCode;

    private GameStatus (Boolean[] fires) {
        this(fires, null, null, 0);
    }

    private GameStatus (GameStatus parent, int step) {
        this.parent = parent;
        this.fires = Arrays.copyOf(parent.fires, parent.fires.length);
        this.step = step;
        this.moves = parent.moves + 1;
    }

    @Builder
    public static GameStatus initGameStatus (Boolean[] input) {
        if ( null == input ) {
            throw new IllegalArgumentException("Invalid input");
        }
        maxLength = input.length;
        return new GameStatus(input);
    }

    public boolean isFinish () {
        return Arrays.stream(fires).allMatch(f -> f);
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
        return IntStream.rangeClosed(1, maxLength).mapToObj(index -> {
            var child = new GameStatus(this, index);
            child.touch(index);
            return child;
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

    private void touch (int index) {
        validateIndex(index);
        invert(index);
        invert(getBeforeIndex(index));
        invert(getNextIndex(index));
    }

    private int getNextIndex (int index) {
        validateIndex(index);
        var result = index + 1;
        if ( result > maxLength ) {
            result = result - maxLength;
        }
        return result;
    }

    private int getBeforeIndex (int index) {
        validateIndex(index);
        var result = index - 1;
        if ( result < 1 ) {
            result = result + maxLength;
        }
        return result;
    }

    private void invert (int index) {
        validateIndex(index);
        synchronized (fires) {
            fires[index - 1] = !fires[index - 1];
        }
    }

    private void validateIndex (int index) {
        if ( index < 1 || index > maxLength ) {
            throw new IllegalArgumentException("Invalid touch index " + index);
        }
    }

    public String generateResolveTrace () {
        var sb = new StringBuilder();
        sb.append(BORDER).append("\n0    ").append(Arrays.toString(fires)).append("    ").append(step);
        if ( null != parent ) {
            parent.generateResolveTrace(sb, 1);
        }
        else {
            sb.append(BORDER);
        }
        return sb.toString();
    }

    private void generateResolveTrace (StringBuilder sb, int index) {
        sb.append('\n').append(index).append("    ").append(Arrays.toString(fires)).append("    ").append(step);
        if ( null != parent ) {
            parent.generateResolveTrace(sb, index + 1);
        }
        else {
            sb.append(BORDER);
        }
    }
}
