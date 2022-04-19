package logan.model;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;

import lombok.Builder;
import lombok.Getter;

public class GameStatus {

    private static final String BORDER     = "\n==================================================================";
    private static final int    MAX_LENGTH = 8;

    private final List<Boolean> fires;
    private final GameStatus    parent;
    private final Integer       step;
    @Getter
    private final int           stackLength;
    private final Set<Integer>  checkedHash;

    private GameStatus () {
        this(new ArrayList<>(MAX_LENGTH), null, null, 0, new HashSet<>());
    }

    public GameStatus (List<Boolean> fires, GameStatus parent, Integer step, int stackLength,
                       Set<Integer> checkedHash) {
        this.fires = fires;
        this.parent = parent;
        this.step = step;
        this.stackLength = stackLength;
        this.checkedHash = checkedHash;
    }

    private GameStatus (GameStatus parent, int step) {
        this.parent = parent;
        this.fires = new ArrayList<>(parent.fires);
        this.step = step;
        this.stackLength = parent.stackLength + 1;
        this.checkedHash = new HashSet<>(parent.checkedHash);
    }

    @Builder
    public static GameStatus initGameStatus (boolean[] input) {
        if ( null == input || input.length != MAX_LENGTH ) {
            throw new IllegalArgumentException("Invalid input");
        }
        var gameStatus = new GameStatus();
        for (int i = 0; i < MAX_LENGTH; i++) {
            gameStatus.fires.add(input[i]);
        }
        gameStatus.checkedHash.add(gameStatus.hashCode());
        return gameStatus;
    }

    public boolean isFinish () {
        return fires.stream().allMatch(fire -> fire);
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
        return fires.equals(that.fires);
    }

    @Override
    public int hashCode () {
        return Objects.hash(fires);
    }

    @Override
    public String toString () {
        return fires.toString();
    }

    public List<GameStatus> generateChildren () {
        return IntStream.rangeClosed(1, MAX_LENGTH).mapToObj(index -> {
            var child = new GameStatus(this, index);
            child.touch(index);
            child.checkedHash.add(child.hashCode());
            return child;
        }).filter(child -> !this.checkedHash.contains(child.hashCode())).collect(toList());
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
        if ( result > MAX_LENGTH ) {
            result = result - MAX_LENGTH;
        }
        return result;
    }

    private int getBeforeIndex (int index) {
        validateIndex(index);
        var result = index - 1;
        if ( result < 1 ) {
            result = result + MAX_LENGTH;
        }
        return result;
    }

    private void invert (int index) {
        validateIndex(index);
        synchronized (fires) {
            var newStatus = !fires.get(index - 1);
            fires.set(index - 1, newStatus);
        }
    }

    private void validateIndex (int index) {
        if ( index < 1 || index > MAX_LENGTH ) {
            throw new IllegalArgumentException("Invalid touch index " + index);
        }
    }

    public String generateResolveTrace () {
        var sb = new StringBuilder();
        sb.append(BORDER).append("\n0    ").append(fires).append("    ").append(step);
        if ( null != parent ) {
            parent.generateResolveTrace(sb, 1);
        }
        else {
            sb.append(BORDER);
        }
        return sb.toString();
    }

    private void generateResolveTrace (StringBuilder sb, int index) {
        sb.append('\n').append(index).append("    ").append(fires).append("    ").append(step);
        if ( null != parent ) {
            parent.generateResolveTrace(sb, index + 1);
        }
        else {
            sb.append(BORDER);
        }
    }
}
