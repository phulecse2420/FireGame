package logan.model;

import java.util.LinkedList;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GamePrinter {

    private static final String BORDER =
        "\n======================================================================================";

    public static String generateResolveTrace (final GameStatus gameStatus) {
        LinkedList<GameStatus> resolveTrace = new LinkedList<>();
        resolveTrace.add(gameStatus);
        var parent = gameStatus.getParent();
        while ( null != parent ) {
            resolveTrace.add(parent);
            parent = parent.getParent();
        }
        return printResolveTrace(resolveTrace);
    }

    private static String printResolveTrace (LinkedList<GameStatus> resolveTrace) {
        var sb    = new StringBuilder().append(BORDER);
        var index = 0;
        for (var descendingIterator = resolveTrace.descendingIterator(); descendingIterator.hasNext(); ) {
            var gameStatus = descendingIterator.next();
            sb.append('\n').append(index).append("    ");
            arrayToString(gameStatus.getFires(), sb);
            sb.append("    ");
            if ( null != gameStatus.getStep() ) {
                sb.append(gameStatus.getStep()).append("    ").append(gameStatus.getStepCost());
            }
            index++;
        }
        sb.append(BORDER);

        return sb.toString();
    }

    private static void arrayToString (boolean[] a, StringBuilder destination) {
        int iMax = a.length - 1;
        destination.append('[');
        for (int i = 0; ; i++) {
            destination.append(a[i] ? '+' : '-');
            if ( i == iMax ) {
                destination.append(']');
                break;
            }
        }
    }
}