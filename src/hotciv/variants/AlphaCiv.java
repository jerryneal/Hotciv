package hotciv.variants;

import hotciv.common.*;
import hotciv.framework.*;

/**
 * @author: Erik
 * Date: 06-11-12, Time: 12:52
 */
public class AlphaCiv {
    private AlphaCiv() {

    }

    /**
     * Returns a game instance that behaves according to the rules of AlphaCiv.
     * @return The game.
     */
    public static Game getGame() {
        return new GameBuilder().build();
    }

}
