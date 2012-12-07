package hotciv.variants;

import hotciv.common.BaseGame;
import hotciv.framework.Game;
import hotciv.framework.GameFactory;

/**
 * @author Erik
 *         Created: 06-11-12, 12:52
 */
public class AlphaCiv implements GameFactory {
    /**
     * Returns a game instance that behaves according to the rules of AlphaCiv.
     *
     * @return The game.
     */
    public BaseGame newGame() {
        return new BaseGame(new AlphaCivFactory());
    }

}
