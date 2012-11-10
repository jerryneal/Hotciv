package hotciv.common;

import hotciv.framework.*;

import java.util.*;

/**
 * The UnitMap represents store all units on a map,
 * and is responsible for placing a unit when a city has produced one.
 * @author : Erik
 * Date: 04-11-12, 13:51
 */
public class GameWorld<UnitImpl extends Unit, TileImpl extends Tile, CityImpl extends City> {
    private Map<Position, UnitImpl> unitMap = new HashMap<Position, UnitImpl>();
    private Map<Position, TileImpl> tileMap = new HashMap<Position, TileImpl>();
    private Map<Position, CityImpl> cityMap = new HashMap<Position, CityImpl>();

    /**
     * Places a unit on the map, under the precondition that the spot where the unit is placed is empty.
     * @param position The position
     * @param unit The unit to place.
     */
    public void placeUnit(Position position, UnitImpl unit) {
        unitMap.put(position, unit);
    }

    /**
     * Places the given unit as near as possible to the given position.
     * It first tried to insert on the specified position, if its non-empty then it tried to insert it clockwise around the position starting north.
     * @param position The position to insert the unit near.
     * @param unit The unit.
     */
    public void placeUnitNear(Position position, UnitImpl unit) {
        if (unitMap.get(position) == null) {
            unitMap.put(position, unit);
        }
        else {
            for (Position aroundPosition : position.getAroundIterable()) {
                if (canPlaceUnitAt(aroundPosition)) {
                    unitMap.put(aroundPosition, unit);
                    break;
                }
            }
        }
    }

    /**
     * Returns true if the given position is available for unit placement.
     * That means that the position is both free from other units an on a type of terrain that units can be placed on.
     * @param position The position
     * @return Whether or not a unit can be placed on the without conflicts.
     */
    private boolean canPlaceUnitAt(Position position) {
        // First see if its a valid position.
        if (position.getColumn() < 0 || position.getRow() < 0 || position.getColumn() > 15 || position.getRow() > 15) {
            return false;
        }


        // Checking for a unit conflicts.
        if (unitMap.get(position) != null)
        {
            return false;
        }

        // Now if a piece of terrain conflicts.
        TileImpl tile = getTile(position);
        if (tile != null) {
            String tileType = tile.getTypeString();
            if (tileType.equals(GameConstants.OCEANS) || tileType.equals(GameConstants.MOUNTAINS)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the unit on the specified position.
     * Or null if there's no unit.
     * @param p The position.
     * @return The unit on the position.
     */
    public UnitImpl getUnit(Position p) {
        return this.unitMap.get(p);
    }

    /**
     * Removes and returns the unit at a specified position.
     * @param position The position.
     * @return The unit that was removed, or null if there wasn't a unit.
     */
    public UnitImpl removeUnit(Position position) {
        return this.unitMap.remove(position);
    }

    /**
     * @return A collection of all units in this UnitMap.
     */
    public Set<Map.Entry<Position, UnitImpl>> getUnitsEntrySet() {
        return unitMap.entrySet();
    }

    public void placeCity(Position position, CityImpl city) {
        this.cityMap.put(position, city);
    }

    public void placeTile(Position position, TileImpl tile) {
        this.tileMap.put(position, tile);
    }

    public TileImpl getTile(Position p) {
        return tileMap.get(p);
    }

    public CityImpl getCity(Position p) {
        return cityMap.get(p);
    }

    public Set<Map.Entry<Position, CityImpl>> getCityEntrySet() {
        return cityMap.entrySet();
    }
}
