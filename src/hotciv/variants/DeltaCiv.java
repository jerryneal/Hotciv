package hotciv.variants;

import hotciv.common.*;
import hotciv.common.strategy.*;
import hotciv.framework.*;
import hotciv.variants.strategies.DeltaWorldLayout;

import java.util.HashSet;

/**
 * @author: Erik
 * Date: 09-11-12, Time: 11:38
 */
public class DeltaCiv implements GameFactory{
    /**
     * Returns a game instance that behaves according to the rules of DeltaCiv.
     * @return The game.
     */
    public Game getGame() {
        return new GameBuilder()
        .setWorldLayoutStrategy(new DeltaWorldLayout())
        .build();
    }
}
