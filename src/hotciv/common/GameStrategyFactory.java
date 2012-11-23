package hotciv.common;

import hotciv.common.strategy.*;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 22-11-12, 13:06
 */
public interface GameStrategyFactory {
    GetWinner createWinnerStrategy(BaseGame game);

    NewAgeCalculator createNewAgeCalculatorStrategy(BaseGame game);

    UnitFactory createUnitFactoryStrategy(BaseGame game);

    WorldLayoutStrategy createWorldLayoutStrategy(BaseGame game);

    AttackResolver createAttackResolverStrategy(BaseGame game);
}
