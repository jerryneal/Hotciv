package hotciv.variants;

import hotciv.common.GameBuilder;
import hotciv.framework.Game;
import hotciv.framework.GameFactory;
import hotciv.variants.strategies.GammaUnitFactory;

/**
 * This variant handles unit actions, that the settler can fortify and the archer can
 *
 * @author Erik
 *         Date: 09-11-12, 23:35
 */
public class GammaCiv implements GameFactory {
    /**
     * Returns a game instance that behaves according to the rules of GammaCiv.
     *
     * @return The game.
     */
    public Game newGame() {
        return new GameBuilder()
                .setUnitFactoryStrategy(new GammaUnitFactory())
                .build();
    }
}
