package hotciv.variants;

import hotciv.variants.strategies.Dice;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Creates a new RandomDice that returns a value between 1 and 6.
 *
 * @author Erik
 *         Created: 19-11-12, 14:45
 */
public class RandomDice implements Dice {
    private Random dice = new SecureRandom();

    @Override
    public int getNext() {
        return (dice.nextInt() % 6) + 1;
    }
}
