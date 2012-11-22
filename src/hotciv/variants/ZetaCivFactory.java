package hotciv.variants;

import hotciv.common.strategy.GetWinner;
import hotciv.variants.strategies.ZetaCivWinnerStrategy;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 22-11-12, 13:32
 */
public class ZetaCivFactory extends AlphaCivFactory {
    @Override
    public GetWinner createWinnerStrategy() {
        return new ZetaCivWinnerStrategy();
    }
}
