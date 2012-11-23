package hotciv.variants;

import hotciv.common.BaseGame;
import hotciv.common.strategy.GetWinner;
import hotciv.variants.strategies.ZetaCivWinnerStrategy;

/**
 * A factory that creates the strategies needed by the game to make it act like Zeta.
 *
 * @author Erik
 *         Created: 22-11-12, 13:32
 */
public class ZetaCivFactory extends AlphaCivFactory {
    @Override
    public GetWinner createWinnerStrategy(BaseGame game) {
        return new ZetaCivWinnerStrategy(game);
    }
}
