package hotciv.variants;

import hotciv.common.BaseGame;
import hotciv.common.strategy.WorldLayoutStrategy;
import hotciv.variants.strategies.DeltaWorldLayout;

/**
 * A factory that creates the strategies needed by the game to make it act like DeltaCiv.
 *
 * @author Erik
 *         Created: 22-11-12, 13:20
 */
public class DeltaCivFactory extends AlphaCivFactory {
    public WorldLayoutStrategy createWorldLayoutStrategy(BaseGame game) {
        return new DeltaWorldLayout(game);
    }
}
