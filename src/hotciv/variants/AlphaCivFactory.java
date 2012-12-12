package hotciv.variants;

import hotciv.common.BaseGame;
import hotciv.common.GameStrategyFactory;
import hotciv.common.strategy.*;
import hotciv.variants.strategies.*;

/**
 * A factory that creates the strategies needed by the game to make it act like AlphaCiv.
 *
 * @author Erik
 *         Created: 22-11-12, 13:09
 */
public class AlphaCivFactory implements GameStrategyFactory {
    @Override
    public GetWinner createWinnerStrategy(BaseGame game) {
        return new AgeBasedWinnerStrategy(game);
    }

    @Override
    public NewAgeCalculator createNewAgeCalculatorStrategy(BaseGame game) {
        return new LinearAgeStrategy(game);
    }

    @Override
    public UnitFactory createUnitFactoryStrategy(BaseGame game) {
        return new DefaultUnitFactory();
    }

    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy(BaseGame game) {
        return new AlphaCivWorldLayout(game);
    }

    @Override
    public AttackResolver createAttackResolverStrategy(BaseGame game) {
        return new AttackerWinsAttackResolver();
    }
}
