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
    private BaseGame game;

    public AgeBasedWinnerStrategy(BaseGame game) {
        this.game = game;
    }

    public Player getWinner() {
        if (game.getAge() >= -3000) {
            return Player.RED;
        } else {
            return null;
        }
    }
}
