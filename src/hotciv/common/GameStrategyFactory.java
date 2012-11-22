package hotciv.common;

import hotciv.common.strategy.*;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 22-11-12, 13:06
 */
public interface GameStrategyFactory {
    GetWinner createWinnerStrategy();

    NewAgeCalculator createNewAgeCalculatorStrategy();

    UnitFactory createUnitFactoryStrategy();

    WorldLayoutStrategy createWorldLayoutStrategy();

    AttackResolver createAttackResolverStrategy();
}
