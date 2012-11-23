package hotciv.variants;

import hotciv.common.BaseGame;
import hotciv.common.strategy.UnitFactory;
import hotciv.variants.strategies.GammaUnitFactory;

/**
 * * A factory that creates the strategies needed by the game to make it act like GammaCiv.
 *
 * @author Erik
 *         Created: 22-11-12, 13:30
 */
public class GammaCivFactory extends AlphaCivFactory {
    @Override
    public UnitFactory createUnitFactoryStrategy(BaseGame game) {
        return new GammaUnitFactory(game);
    }
}
