package hotciv.variants;

import hotciv.common.*;
import hotciv.common.strategy.UnitAction;
import hotciv.common.strategy.UnitFactory;
import hotciv.common.units.Archer;
import hotciv.framework.*;
import hotciv.variants.strategies.GammaUnitAction;
import hotciv.variants.strategies.GammaUnitFactory;
import hotciv.variants.units.GammaArcher;

/**
 * This variant handles unit actions, that the settler can fortify and the archer can
 *
 * @author: Erik
 * Date: 09-11-12, 23:35
 */
public class GammaCiv implements GameFactory{
   /**
     * Returns a game instance that behaves according to the rules of GammaCiv.
     * @return The game.
     */
    public Game getGame() {
        return new GameBuilder()
        .setUnitFactoryStrategy(new GammaUnitFactory())
        .setUnitActionStrategy(new GammaUnitAction())
        .build();
    }
}
