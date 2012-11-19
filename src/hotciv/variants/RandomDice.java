package hotciv.variants;

import hotciv.variants.strategies.Dice;

import java.security.SecureRandom;
import java.util.Random;

/**
 * //TODO: Doc
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
