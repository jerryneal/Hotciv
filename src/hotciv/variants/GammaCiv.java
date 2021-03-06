package hotciv.variants;

import hotciv.common.BaseGame;
import hotciv.framework.Game;
import hotciv.framework.GameFactory;

/**
 * This variant handles unit actions, that the settler can fortify and the archer can
 *
 * @author Erik
 *         Created: 09-11-12, 23:35
 */
public class GammaCiv implements GameFactory {
    /**
     * Returns a game instance that behaves according to the rules of GammaCiv.
     *
     * @return The game.
     */
    public BaseGame newGame() {
        return new BaseGame(new GammaCivFactory());
    }
}
