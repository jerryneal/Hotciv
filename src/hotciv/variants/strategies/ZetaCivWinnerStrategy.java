package hotciv.variants.strategies;

import hotciv.common.BaseGame;
import hotciv.common.observers.EndOfRoundObserver;
import hotciv.common.strategy.GetWinner;
import hotciv.framework.Player;

/**
 * This strategy is an implemetation of the ZetaCiv specification, it uses the ConquerWinnerStrategy and the TripleWinnerWinsStrategy in tandem,
 * if less than twenty rounds have passed, the player to conquer all cities wins, if twenty rounds have passed, whoever wins 3 battles first wins,
 * counting from the twentyfirst round onwards.
 *
 * @author Erik
 *         Created: 16-11-12, 17:14
 */
public class ZetaCivWinnerStrategy implements GetWinner, EndOfRoundObserver {
    private BaseGame game;
    private ConquerWinnerStrategy conquerWinnerStrategy;
    private TripleWinnerWins tripleWinnerWinsStrategy;

    private Player winner = null;
    private int roundCount = 1;

    public ZetaCivWinnerStrategy(final BaseGame game) {
        this.game = game;
        conquerWinnerStrategy = new ConquerWinnerStrategy(game);
        this.tripleWinnerWinsStrategy = null;

        game.addEndOfRoundObserver(this);
    }

    @Override
    public Player getWinner() {
        if (winner == null) {
            if (roundCount <= 20) {
                winner = conquerWinnerStrategy.getWinner();
            } else {
                if (tripleWinnerWinsStrategy == null) {
                    tripleWinnerWinsStrategy = new TripleWinnerWins(game);
                }
                winner = tripleWinnerWinsStrategy.getWinner();
            }
        }
        return winner;
    }

    @Override
    public void endOfRound() {
        roundCount++;
    }
}
