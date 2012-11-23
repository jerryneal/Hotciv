package hotciv.variants;

import hotciv.common.BaseGame;
import hotciv.framework.Game;
import hotciv.framework.GameFactory;

/**
 * @author Erik
 *         Created: 09-11-12, 11:38
 */
public class BetaCiv implements GameFactory {
    /**
     * Returns a game instance that behaves according to the rules of BetaCiv.
     *
     * @return The game.
     */
    public Game newGame() {
        return new BaseGame(new BetaCivFactory());
    }
}
