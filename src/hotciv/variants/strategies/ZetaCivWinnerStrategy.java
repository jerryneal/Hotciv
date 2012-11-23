package hotciv.variants.strategies;

import hotciv.common.BaseGame;
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
public class ZetaCivWinnerStrategy implements GetWinner {
    ConquerWinnerStrategy conquerWinnerStrategy = new ConquerWinnerStrategy();
    TripleWinnerWins tripleWinnerWinsStrategy = new TripleWinnerWins();
    boolean moveCountHasBeenReset = false;
    private Player winner;

    @Override
    public Player getWinner(BaseGame game) {
        if (winner == null) {
            if (game.getCurrentRoundCount() <= 20) {
                winner = conquerWinnerStrategy.getWinner(game);
            } else {
                if (!moveCountHasBeenReset) {
                    moveCountHasBeenReset = true;
                    game.resetAttacksWon();
                }
                winner = tripleWinnerWinsStrategy.getWinner(game);
            }
        }
        return winner;
    }
}
