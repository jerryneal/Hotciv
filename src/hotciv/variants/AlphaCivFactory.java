package hotciv.variants;

import hotciv.common.BaseGame;
import hotciv.common.GameStrategyFactory;
import hotciv.common.strategy.*;
import hotciv.variants.strategies.*;

/**
 * // TODO: Doc
 *
 * @author Erik
 *         Created: 22-11-12, 13:09
 */
public class AlphaCivFactory implements GameStrategyFactory {

    /**
     * Gets an instance of the default strategy to calculate the winner of the game.
     *
     * @param game
     * @return The default GetWinner
     */
    @Override
    public GetWinner createWinnerStrategy(BaseGame game) {
        return new AgeBasedWinnerStrategy(game);
    }

    /**
     * Gets an instance of the default strategy to calculate the new age of the game after each round.
     *
     * @param game
     * @return the default NewAgeCalculator
     */
    @Override
    public NewAgeCalculator createNewAgeCalculatorStrategy(BaseGame game) {
        return new LinearAgeStrategy(game);
    }

    /**
     * Gets an instance of the default strategy for making units.
     *
     * @param game
     * @return the default UnitFactory.
     */
    @Override
    public UnitFactory createUnitFactoryStrategy(BaseGame game) {
        return new DefaultUnitFactory();
    }

    /**
     * Gets an instance of the default strategy for creating the world.
     *
     * @param game
     * @return the default WorldLayoutStrategy.
     */
    @Override
    public WorldLayoutStrategy createWorldLayoutStrategy(BaseGame game) {
        return new AlphaCivWorldLayout(game);
    }

    /**
     * Gets the default attackResolver where the attacker always wins.
     *
     * @param game
     * @return The attackResolver
     */
    @Override
    public AttackResolver createAttackResolverStrategy(BaseGame game) {
        return new AttackerWinsAttackResolver();
    }
}
