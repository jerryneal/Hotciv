package hotciv.variants;

import hotciv.common.GameStrategyFactory;
import hotciv.common.strategy.*;
import hotciv.variants.strategies.*;

/**
 * // TODO: Doc
 * <p/>
 * // TODO: Change docs in methods and export the anonymous classes.
 *
 * @author Erik
 *         Created: 22-11-12, 13:09
 */
public class AlphaCivFactory implements GameStrategyFactory {

    /**
     * Gets an instance of the default strategy to calculate the winner of the game.
     *
     * @return The default GetWinner
     */
    @Override
    public GetWinner createWinnerStrategy() {
        return new AgeBasedWinnerStrategy();
    }

    /**
     * Gets an instance of the default strategy to calculate the new age of the game after each round.
     *
     * @return the default NewAgeCalculator
     */
    @Override
    public NewAgeCalculator createNewAgeCalculatorStrategy() {
        return new LinearAgeStrategy();
    }

    /**
     * Gets an instance of the default strategy for making units.
     *
     * @return the default UnitFactory.
     */
    @Override
    public UnitFactory createUnitFactoryStrategy() {
        return new DefaultUnitFactory();
    }

    /**
     * Gets an instance of the default strategy for creating the world.
     *
     * @return the default WorldLayoutStrategy.
     */
    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy() {
        return new AlphaCivWorldLayout();
    }

    /**
     * Gets the default attackResolver where the attacker always wins.
     *
     * @return The attackResolver
     */
    @Override
    public AttackResolver createAttackResolverStrategy() {
        return new AttackerWinsAttackResolver();
    }
}
