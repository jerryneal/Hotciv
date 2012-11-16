package hotciv.variants.strategies;

import hotciv.common.BaseGame;
import hotciv.common.strategy.GetWinner;
import hotciv.framework.Player;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 16-11-12, 16:42
 */
public class TripleWinnerWins implements GetWinner {
    Player winner;

    @Override
    public Player getWinner(BaseGame game) {
        if (winner == null) {
            for (Player player : Player.values()) {
                if (game.getAttacksWon(player) >= 3) {
                    winner = player;
                    break;
                }
            }
        }
        return winner;
    }
}
