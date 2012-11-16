package hotciv.variants;

import hotciv.common.GameBuilder;
import hotciv.framework.Game;
import hotciv.framework.GameFactory;

/**
 * @author Erik
 *         Date: 06-11-12, Time: 12:52
 */
public class AlphaCiv implements GameFactory {
    /**
     * Returns a game instance that behaves according to the rules of AlphaCiv.
     *
     * @return The game.
     */
    public Game getGame() {
        return new GameBuilder().build();
    }

}
