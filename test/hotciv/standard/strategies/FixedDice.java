package hotciv.standard.strategies;

import hotciv.variants.strategies.Dice;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 19-11-12, 14:47
 */
public class FixedDice implements Dice {
    private int value;

    public FixedDice(int value) {
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int getNext() {
        return value;
    }
}
