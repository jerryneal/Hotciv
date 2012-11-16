package hotciv.variants.strategies;

import hotciv.common.BaseGame;
import hotciv.common.UnitImpl;
import hotciv.common.strategy.UnitFactory;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.variants.units.GammaArcher;
import hotciv.variants.units.GammaSettler;

/**
 * // TODO: Doc.
 *
 * @author: Erik
 * Date: 16-11-12, 10:22
 */
public class GammaUnitFactory implements UnitFactory {
    UnitFactory defaultFactory = BaseGame.DefaultStrategies.getUnitFactory();

    public UnitImpl makeUnit(BaseGame game, String typeString, Player owner) {
        if (GameConstants.ARCHER.equals(typeString)) {
            return new GammaArcher(owner);
        } else if (GameConstants.SETTLER.equals(typeString)) {
            return new GammaSettler(owner);
        } else {
            // Return default implementation.
            return defaultFactory.makeUnit(game, typeString, owner);
        }
    }
}
