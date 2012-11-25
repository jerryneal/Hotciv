package hotciv.variants;

import hotciv.common.BaseGame;
import hotciv.common.strategy.CityProductionStrategy;
import hotciv.variants.strategies.EtaCivCityProductionStrategy;

/**
 * //TODO: Document!
 *
 * @author : Erik
 *         Date: 25-11-12, 18:17
 */
public class EtaCivFactory extends AlphaCivFactory {
    @Override
    public CityProductionStrategy createCityProductionStrategy(BaseGame game) {
        return new EtaCivCityProductionStrategy(game);
    }
}
