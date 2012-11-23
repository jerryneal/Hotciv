package hotciv.common.strategy;

import hotciv.framework.Player;
import hotciv.framework.Unit;

/**
 * This strategy specifies how to make a unit using the factory method pattern.
 *
 * @author Erik
 *         Created: 10-11-12, 10:36
 */
public interface UnitFactory {
    /**
     * Creates a new unit based on the typeString and the owner.
     *
     * @param typeString The typeString for the unit.
     * @param owner      The owner of the unit.
     * @return The created unit.
     */
    public Unit makeUnit(String typeString, Player owner);
}
