package hotciv.variants;

import hotciv.common.BaseGame;
import hotciv.common.GameStrategyFactory;
import hotciv.common.strategy.*;
import hotciv.variants.strategies.*;

/**
 * * A factory that creates the strategies needed by the game to make it act like SemiCiv.
 *
 * @author Erik
 *         Created: 23-11-12, 13:05
 */
public class SemiCivFactory implements GameStrategyFactory {
    private Dice dice;

    public SemiCivFactory(Dice dice) {
        this.dice = dice;
    }

    @Override
    public GetWinner createWinnerStrategy(BaseGame game) {
        return new TripleWinnerWins(game);
    }

    @Override
    public NewAgeCalculator createNewAgeCalculatorStrategy(BaseGame game) {
        return new PeriodicAgingStrategy(game);
    }

    @Override
    public UnitFactory createUnitFactoryStrategy(BaseGame game) {
        // TODO: Unit actions: Settler like GammaCiv
        return new GammaUnitFactory(game);
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy(BaseGame game) {
        return new DeltaWorldLayout(game);
    }

    @Override
    public AttackResolver createAttackResolverStrategy(BaseGame game) {
        return new EpsilonCivAttackResolver(game, dice);
    }

    @Override
    public CityProductionStrategy createCityProductionStrategy(BaseGame game) {
        return new EtaCivCityProductionStrategy(game);
    }
}
