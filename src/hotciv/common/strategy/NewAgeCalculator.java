package hotciv.common.strategy;

/**
 * This strategy calculates the new age of the game after each round.
 *
 * @author : Erik
 *         Created: 09-11-12, 11:46
 */
public interface NewAgeCalculator {
    /**
     * Is called in the end of each round to get the new age of the game.
     *
     * @return The new age.
     */
    public int getNewAge();
}
