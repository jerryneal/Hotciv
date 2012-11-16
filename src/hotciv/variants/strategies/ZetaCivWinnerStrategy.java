package hotciv.variants.strategies;

import hotciv.common.BaseGame;
import hotciv.common.strategy.GetWinner;
import hotciv.framework.Player;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 16-11-12, 17:14
 */
public class ZetaCivWinnerStrategy implements GetWinner {
    ConquerWinnerStrategy conquerWinnerStrategy = new ConquerWinnerStrategy();
    TripleWinnerWins tripleWinnerWinsStrategy = new TripleWinnerWins();
    boolean moveCountHasBeenReset = false;

    @Override
    public Player getWinner(BaseGame game) {
        if (game.getRoundCount() <= 20) {
            return conquerWinnerStrategy.getWinner(game);
        } else {
            if (!moveCountHasBeenReset) {
                moveCountHasBeenReset = true;
                game.resetAttacksWon();
            }
            return tripleWinnerWinsStrategy.getWinner(game);
        }
    }
}
