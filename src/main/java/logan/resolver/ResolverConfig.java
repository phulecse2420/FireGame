package logan.resolver;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResolverConfig {

    @Builder.Default
    private final int maxMoves       = Integer.MAX_VALUE;
    @Builder.Default
    private final int maxCost        = Integer.MAX_VALUE;
    @Builder.Default
    private final int numberOfResult = 1;

}
