package hotciv.variants;

import hotciv.common.BaseGame;
import hotciv.framework.Game;
import hotciv.framework.GameFactory;

/**
 * @author Erik
 *         Created: 09-11-12, 11:38
 */
public class DeltaCiv implements GameFactory {
    /**
     * Returns a game instance that behaves according to the rules of DeltaCiv.
     *
     * @return The game.
     */
    public Game newGame() {
        return new BaseGame(new DeltaCivFactory());
    }
}
