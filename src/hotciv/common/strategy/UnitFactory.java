package hotciv.common.strategy;

import hotciv.common.UnitImpl;
import hotciv.framework.Player;

/**
 * This strategy specifies how to make a unit using the factory method pattern.
 *
 * @author: Erik
 * Date: 10-11-12, 10:36
 */
public interface UnitFactory {
    /**
     * Creates a new unit based on the typeString and the owner.
     * @param typeString The typeString for the unit.
     * @param owner The owner of the unit.
     * @return The created unit.
     */
    public UnitImpl makeUnit(String typeString, Player owner);
}
