package hotciv.variants;

import hotciv.common.BaseGame;
import hotciv.framework.Game;
import hotciv.framework.GameFactory;
import hotciv.variants.strategies.Dice;
import hotciv.variants.strategies.RandomDice;

/**
 * The SemiCiv variant combines 5 of the strategies from different variants, and combines them into one.
 *
 * @author Erik
 *         Created: 23-11-12, 13:04
 */
public class SemiCiv implements GameFactory {
    private Dice dice;

    public SemiCiv() {
        this(new RandomDice());
    }

    public SemiCiv(Dice dice) {
        this.dice = dice;
    }

    @Override
    public Game newGame() {
        return new BaseGame(new SemiCivFactory(dice));
    }
}
