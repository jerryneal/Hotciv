package hotciv.variants.strategies;

import java.util.Random;

/**
 * Creates a new RandomDice that returns a value between 1 and 6.
 *
 * @author Erik
 *         Created: 19-11-12, 14:45
 */
public class RandomDice implements Dice {
    private Random dice = new Random();

    @Override
    public int getNext() {
        return (Math.abs(dice.nextInt()) % 6) + 1;
    }
}
