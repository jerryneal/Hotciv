package hotciv.variants.strategies;

import hotciv.common.AbstractUnit;
import hotciv.common.strategy.UnitFactory;
import hotciv.common.units.Archer;
import hotciv.common.units.Legion;
import hotciv.common.units.Settler;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 22-11-12, 15:42
 */
public class DefaultUnitFactory implements UnitFactory {
    public AbstractUnit makeUnit(String typeString, Player owner) {
        if (GameConstants.ARCHER.equals(typeString)) {
            return new Archer(owner);
        } else if (GameConstants.SETTLER.equals(typeString)) {
            return new Settler(owner);
        } else if (GameConstants.LEGION.equals(typeString)) {
            return new Legion(owner);
        } else {
            throw new RuntimeException("Unrecognized unit type: " + typeString);
        }
    }
}
