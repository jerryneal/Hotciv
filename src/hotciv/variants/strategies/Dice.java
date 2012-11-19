package hotciv.variants.strategies;

/**
 * @author : Erik
 *         created: 18-11-12, 20:40
 */
public interface Dice {
    /**
     * Returns the result of the next dice roll.
     * For a normal 6 sided dice, this number would be between 1 and 6.
     *
     * @return The result of the dice roll.
     */
    public int getNext();
}
