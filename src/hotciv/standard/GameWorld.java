package hotciv.standard;

import hotciv.framework.*;

import java.util.*;

/**
 * The UnitMap represents store all units on a map,
 * and is responsible for placing a unit when a city has produced one.
 * @author : Erik
 * Date: 04-11-12, 13:51
 */
public class GameWorld<UnitImpl extends Unit, TileImpl extends Tile, CityImpl extends City> {
    Map<Position, UnitImpl> unitMap = new HashMap<Position, UnitImpl>();
    Map<Position, TileImpl> tileMap = new HashMap<Position, TileImpl>();
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
            if (canPlaceUnitAt(north)) {
                unitMap.put(north, unit);
            }
            else if (canPlaceUnitAt(northEast)) {
                unitMap.put(northEast, unit);
            }
            else if (canPlaceUnitAt(East)) {
                unitMap.put(East, unit);
            }
            else if (canPlaceUnitAt(southEast)) {
                unitMap.put(southEast, unit);
            }
            else if (canPlaceUnitAt(south)) {
                unitMap.put(south, unit);
            }
            else if (canPlaceUnitAt(southWest)) {
                unitMap.put(southWest, unit);
            }
            else if (canPlaceUnitAt(west)) {
                unitMap.put(west, unit);
            }
            else if (canPlaceUnitAt(northWest)) {
                unitMap.put(northWest, unit);
            }
            else {
                // TODO: Do something else when a unit cannot be placed.
                throw new RuntimeException("Could not place a new unit near: " + position);
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
        // First seeing if a unit conflicts.
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

    public City getCity(Position p) {
        return cityMap.get(p);
    }

    public Set<Map.Entry<Position, CityImpl>> getCityEntrySet() {
        return cityMap.entrySet();
    }
}
