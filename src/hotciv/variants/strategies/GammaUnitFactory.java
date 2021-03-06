package hotciv.variants.strategies;

import hotciv.common.AbstractUnit;
import hotciv.common.BaseGame;
import hotciv.common.strategy.UnitFactory;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;
import hotciv.variants.units.CityCreatingSettler;
import hotciv.variants.units.FortifyingArcher;

/**
 * This factory creates GammaArchers and GammaSettlers instead of the default units.
 * If asked to create something that is not an archer or a settler, it will call the default factory.
 *
 * @author Erik
 *         Created : 16-11-12, 10:22
 */
public class GammaUnitFactory implements UnitFactory {
    private BaseGame game;

    public GammaUnitFactory(BaseGame game) {
        this.game = game;
    }

    UnitFactory defaultFactory = new DefaultUnitFactory();

    public AbstractUnit makeUnit(String typeString, Player owner) {
        if (GameConstants.ARCHER.equals(typeString)) {
            return new FortifyingArcher(owner);
        } else if (GameConstants.SETTLER.equals(typeString)) {
            return new CityCreatingSettler(game, owner);
        } else {
            // Return default implementation.
            return defaultFactory.makeUnit(typeString, owner);
        }
    }
}
