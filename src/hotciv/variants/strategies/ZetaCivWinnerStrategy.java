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
    private BaseGame game;
    private ConquerWinnerStrategy conquerWinnerStrategy;
    private TripleWinnerWins tripleWinnerWinsStrategy;

    public ZetaCivWinnerStrategy(BaseGame game) {
        this.game = game;
        conquerWinnerStrategy = new ConquerWinnerStrategy(game);
        tripleWinnerWinsStrategy = new TripleWinnerWins(game);
    }

    // TODO: Observer pattern.
    boolean moveCountHasBeenReset = false;
    private Player winner;

    @Override
    public Player getWinner() {
        if (winner == null) {
            if (game.getCurrentRoundCount() <= 20) {
                winner = conquerWinnerStrategy.getWinner();
            } else {
                if (!moveCountHasBeenReset) {
                    moveCountHasBeenReset = true;
                    game.resetAttacksWon();
                }
                winner = tripleWinnerWinsStrategy.getWinner();
            }
        }
        return winner;
    }
}
