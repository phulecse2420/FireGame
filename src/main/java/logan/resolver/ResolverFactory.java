package logan.resolver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResolverFactory {

    public static Resolver createResolver (ResolverType type) {
        switch (type) {
            case DFS:
                return new DfsResolver();
            case BFS:
                return new BfsResolver();
            case DIJKSTRA:
                return new DijkstraResolver();
            default:
                throw new IllegalArgumentException("Resolver type " + type + " is not supported!");
        }
    }
}
