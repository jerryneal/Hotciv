package hotciv.variants;

import hotciv.common.strategy.UnitFactory;
import hotciv.variants.strategies.GammaUnitFactory;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 22-11-12, 13:30
 */
public class GammaCivFactory extends AlphaCivFactory {
    @Override
    public UnitFactory createUnitFactoryStrategy() {
        return new GammaUnitFactory();
    }
}
