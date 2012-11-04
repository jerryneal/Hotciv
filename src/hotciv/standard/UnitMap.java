package hotciv.standard;

import hotciv.framework.Position;
import hotciv.framework.Unit;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The UnitMap represents store all units on a map,
 * and is responsible for placing a unit when a city has produced one.
 * @author : Erik
 * Date: 04-11-12, 13:51
 */
public class UnitMap {
    Map<Position, UnitImpl> map = new HashMap<Position, UnitImpl>();

    /**
     * Places a unit on the map, under the precondition that the spot where the unit is placed is empty.
     * @param position
     * @param unit
     */
    public void place(Position position, UnitImpl unit) {
        map.put(position, unit);
    }

    /**
     * Places the given unit as near as possible to the given position.
     * It first tried to insert on the specified position, if its non-empty then it tried to insert it clockwise around the position starting north.
     * @param position The position to insert the unit near.
     * @param unit The unit.
     */
    public void placeNear(Position position, UnitImpl unit) {
        if (map.get(position) == null) {
            map.put(position, unit);
        }
        else {
            int orgRow = position.getRow();
            int orgCol = position.getColumn();
            Position north = new Position(orgRow - 1, orgCol);
            Position northEast = new Position(orgRow - 1, orgCol + 1);
            Position East = new Position(orgRow, orgCol + 1);
            Position southEast = new Position(orgRow + 1, orgCol + 1);
            Position south = new Position(orgRow + 1, orgCol);
            Position southWest = new Position(orgRow + 1, orgCol - 1);
            Position west = new Position(orgRow, orgCol - 1);
            Position northWest = new Position(orgRow - 1, orgCol - 1);
            if (map.get(north) == null) {
                map.put(north, unit);
            }
            else if (map.get(northEast) == null) {
                map.put(northEast, unit);
            }
            else if (map.get(East) == null) {
                map.put(East, unit);
            }
            else if (map.get(southEast) == null) {
                map.put(southEast, unit);
            }
            else if (map.get(south) == null) {
                map.put(south, unit);
            }
            else if (map.get(southWest) == null) {
                map.put(southWest, unit);
            }
            else if (map.get(west) == null) {
                map.put(west, unit);
            }
            else if (map.get(northWest) == null) {
                map.put(northWest, unit);
            }
            else {
                // TODO: Do something else when a unit cannot be placed.
                throw new RuntimeException("Could not place a new unit near: " + position);
            }
        }
    }

    /**
     * Returns the unit on the specified position.
     * Or null if there's no unit.
     * @param p The position.
     * @return The unit on the position.
     */
    public UnitImpl get(Position p) {
        return this.map.get(p);
    }

    /**
     * Removes and returns the unit at a specified position.
     * @param position The position.
     * @return The unit that was removed, or null if there wasn't a unit.
     */
    public UnitImpl remove(Position position) {
        return this.map.remove(position);
    }

    /**
     * @return A collection of all units in this UnitMap.
     */
    public Collection<UnitImpl> getUnits() {
        return map.values();
    }
}
