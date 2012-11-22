package hotciv.variants;

import hotciv.common.BaseGame;
import hotciv.framework.Game;
import hotciv.framework.GameFactory;
import hotciv.variants.strategies.Dice;
import hotciv.variants.strategies.RandomDice;

/**
 * Conforming to the EpsilonCiv specification, this class creates an instance of a game where battles are resolves using the
 * EpsilonCiv algorithm, and the winner of the game is whoever wins 3 battles first.
 *
 * @author Erik
 *         Created: 16-11-12, 15:38
 */
public class EpsilonCiv implements GameFactory {
    private Dice dice;

    /**
     * TODO: Doc.
     */
    public EpsilonCiv() {
        this.dice = new RandomDice();
    }

    /**
     * TODO: Doc.
     *
     * @param dice
     */
    public EpsilonCiv(Dice dice) {
        this.dice = dice;
    }

    @Override
    public Game newGame() {
        return new BaseGame(new EpsilonCivFactory(dice));
    }
}
