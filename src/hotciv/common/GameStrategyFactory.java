package hotciv.common;

import hotciv.common.strategy.*;

/**
 * The interface describes a factory that creates game strategies.
 *
 * @author Erik
 *         Created: 22-11-12, 13:06
 */
public interface GameStrategyFactory {
    /**
     * Creates the winner strategy that calculates who has won The game.
     *
     * @param game The BaseGame to calculate on.
     * @return A GetWinner instance.
     */
    public GetWinner createWinnerStrategy(BaseGame game);

    /**
     * Creates the NewAgeCalculator that calculates the new age, when the age is incremented.
     *
     * @param game The BaseGame to calculate on.
     * @return A NewAgeCalculator instance.
     */
    public NewAgeCalculator createNewAgeCalculatorStrategy(BaseGame game);

    /**
     * Creates the UnitFactory that makes new units for use in the game.
     *
     * @param game The game.
     * @return A UnitFactory instance.
     */
    public UnitFactory createUnitFactoryStrategy(BaseGame game);

    /**
     * Creates the WorldLayoutStrategy that defines the world in a game.
     *
     * @param game The game.
     * @return A WorldLayoutStrategy instance.
     */
    public WorldLayoutStrategy createWorldLayoutStrategy(BaseGame game);

    /**
     * Creates the AttackResolver that determines whether or not an attack is succesfull.
     *
     * @param game The game.
     * @return A AttackResolver instance.
     */
    public AttackResolver createAttackResolverStrategy(BaseGame game);
}
