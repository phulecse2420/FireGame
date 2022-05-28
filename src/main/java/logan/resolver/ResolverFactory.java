package logan.resolver;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResolverFactory {

    public static Resolver createResolver (ResolverType type, ResolverConfig config) {
        switch (type) {
            case DFS:
                return new DfsResolver(config);
            case BFS:
                return new BfsResolver(config);
            default:
                throw new IllegalArgumentException("Resolver type " + type + " is not supported!");
        }
    }
}
