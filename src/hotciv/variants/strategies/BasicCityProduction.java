package hotciv.variants.strategies;

import hotciv.common.CityImpl;
import hotciv.common.strategy.CityProductionStrategy;

/**
 * //TODO: Document!
 *
 * @author : Erik
 *         Date: 25-11-12, 19:49
 */
public class BasicCityProduction implements CityProductionStrategy {
    @Override
    public void produceOnCity(CityImpl city) {
        city.increaseProductionAmount(6);
    }
}
