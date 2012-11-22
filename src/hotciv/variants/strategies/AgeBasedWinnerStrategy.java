package hotciv.variants.strategies;

import hotciv.common.BaseGame;
import hotciv.common.strategy.GetWinner;
import hotciv.framework.Player;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 22-11-12, 15:43
 */
public class AgeBasedWinnerStrategy implements GetWinner {
    public Player getWinner(BaseGame game) {
        if (game.getAge() >= -3000) {
            return Player.RED;
        } else {
            return null;
        }
    }
}
