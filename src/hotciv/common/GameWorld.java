package hotciv.common;

import hotciv.common.strategy.UnitFactory;
import hotciv.framework.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The UnitMap represents store all units on a map,
 * and is responsible for placing a unit when a city has produced one.
 *
 * @author : Erik
 *         Created : 04-11-12, 13:51
 */
public class GameWorld {
    private Map<Position, AbstractUnit> unitMap = new HashMap<Position, AbstractUnit>();
    private Map<Position, Tile> tileMap = new HashMap<Position, Tile>();
    private Map<Position, CityImpl> cityMap = new HashMap<Position, CityImpl>();
    private UnitFactory unitFactory;
    private Set<GameObserver> gameObservers;

    public GameWorld(UnitFactory unitFactory, Set<GameObserver> gameObservers) {
        this.unitFactory = unitFactory;
        this.gameObservers = gameObservers;
    }

    private void callWorldChangedAddObserver(Position position) {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.worldChangedAt(position);
        }
    }

    /**
     * Places a unit on the map, under the precondition that the spot where the unit is placed is empty.
     *
     * @param position The position
     * @param unit     The unit to place.
     */
    protected void placeUnit(Position position, AbstractUnit unit) {
        unitMap.put(position, unit);
        callWorldChangedAddObserver(position);
    }

    /**
     * Places a new unit on the map, under the precondition that the spot where the unit is places is empty.
     *
     * @param position   The position to place the unit.
     * @param typeString The string describing the unit. Valid strings are in GameConstants
     * @param owner      The owner of the new unit.
     */
    public void placeNewUnit(Position position, String typeString, Player owner) {
        placeUnit(position, unitFactory.makeUnit(typeString, owner));
    }

    /**
     * Places the given unit as near as possible to the given position.
     * It first tried to insert on the specified position, if its non-empty then it tried to insert it clockwise around the position starting north.
     *
     * @param position The position to insert the unit near.
     * @param unit     The unit.
     */
    public void placeUnitNear(Position position, AbstractUnit unit) {
        if (canPlaceUnitAt(position)) {
            unitMap.put(position, unit);
            callWorldChangedAddObserver(position);
            return;
        }
        for (Position aroundPosition : position.getAroundIterable()) {
            if (canPlaceUnitAt(aroundPosition)) {
                unitMap.put(aroundPosition, unit);
                callWorldChangedAddObserver(aroundPosition);
                return;
            }
            // Escaping infinite loops.
            if (Position.getDistance(position, aroundPosition) > GameConstants.WORLDSIZE) {
                return;
            }
        }
    }

    /**
     * Returns true if the given position is available for unit placement.
     * That means that the position is both free from other units an on a type of terrain that units can be placed on.
     *
     * @param position The position
     * @return Whether or not a unit can be placed on the without conflicts.
     */
    private boolean canPlaceUnitAt(Position position) {
        // First see if its a valid position.
        if (!isWithinGameworld(position))
            return false;


        // Checking for a unit conflicts.
        if (unitMap.get(position) != null) {
            return false;
        }

        // Now if a piece of terrain conflicts.
        Tile tile = getTile(position);
        if (tile != null) {
            String tileType = tile.getTypeString();
            if (tileType.equals(GameConstants.OCEANS) || tileType.equals(GameConstants.MOUNTAINS)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isWithinGameworld(Position position) {
        if (position.getColumn() < 0 || position.getRow() < 0 || position.getColumn() > 15 || position.getRow() > 15) {
            return false;
        }
        return true;
    }

    /**
     * Returns the unit on the specified position.
     * Or null if there's no unit.
     *
     * @param p The position.
     * @return The unit on the position.
     */
    public AbstractUnit getUnit(Position p) {
        return this.unitMap.get(p);
    }

    /**
     * Removes and returns the unit at a specified position.
     *
     * @param position The position.
     * @return The unit that was removed, or null if there wasn't a unit.
     */
    public Unit removeUnit(Position position) {
        Unit unit = this.unitMap.remove(position);
        callWorldChangedAddObserver(position);
        return unit;
    }

    /**
     * Gets an entrySet of all the units, with their position as the key.
     *
     * @return An entrySet of units.
     */
    public Set<Map.Entry<Position, AbstractUnit>> getUnitsEntrySet() {
        return unitMap.entrySet();
    }

    /**
     * Places a city on the specified position. This will replace any existing city on that position.
     * Precondition: The position is valid for placing a city.
     *
     * @param position The position to place the city.
     * @param city     The city.
     */
    public void placeCity(Position position, CityImpl city) {
        this.cityMap.put(position, city);
        callWorldChangedAddObserver(position);
    }

    /**
     * Places the tile on the specified position. Replacing any existing tile on that position.
     *
     * @param position The position to place the tile on.
     * @param tile     The tile.
     */
    public void placeTile(Position position, Tile tile) {
        this.tileMap.put(position, tile);
        callWorldChangedAddObserver(position);
    }

    /**
     * Gets the tile at the specified position. Or null if none specified.
     *
     * @param position The position.
     * @return The tile at the position.
     */
    public Tile getTile(Position position) {
        return tileMap.get(position);
    }

    /**
     * Gets the city at the specified position. Or null if no city.
     *
     * @param position The position.
     * @return The city at the position.
     */
    public CityImpl getCity(Position position) {
        return cityMap.get(position);
    }

    /**
     * Gets an entrySet of all the cities on the GameWorld, with their positions as keys.
     *
     * @return An entrySet of all cities in this GameWorld.
     */
    public Set<Map.Entry<Position, CityImpl>> getCityEntrySet() {
        return cityMap.entrySet();
    }

    /**
     * Populates the GameWorld based on the strings given. In a string 1 character equals 1 tile.
     * The options for tiles are O (ocean), P (plains), M (mountain), F (forest), H (hills).
     * If a character in the string does not equal one of the above the method will throw an IllegalArgumentException.
     *
     * @param worldLayout The layout described in a array of strings.
     */
    public void populateWorld(String[] worldLayout) {
        for (int i = 0; i < worldLayout.length; i++) {
            String line = worldLayout[i].toUpperCase();
            for (int j = 0; j < worldLayout.length; j++) {
                ShortLayoutType type = ShortLayoutType.valueOf("" + line.charAt(j));
                String typeString;
                switch (type) {
                    case O:
                        typeString = GameConstants.OCEANS;
                        break;
                    case P:
                        typeString = GameConstants.PLAINS;
                        break;
                    case M:
                        typeString = GameConstants.MOUNTAINS;
                        break;
                    case F:
                        typeString = GameConstants.FOREST;
                        break;
                    case H:
                        typeString = GameConstants.HILLS;
                        break;
                    default:
                        throw new RuntimeException("Unrecognized ShortLayoutType.");
                }
                Position p = new Position(i, j);
                this.placeTile(p, new StandardTile(p, typeString));
            }
        }
    }

    /**
     * Returns the position where the specified unit is located, or null if the unit was not found.
     *
     * @param unit The unit to find.
     * @return the position of the unit.
     */
    public Position getUnitPosition(Unit unit) {
        for (Map.Entry<Position, AbstractUnit> unitEntry : getUnitsEntrySet()) {
            if (unit == unitEntry.getValue()) {
                return unitEntry.getKey();
            }
        }
        return null;
    }

    /**
     * Returns the position where the specified city is located, or null if the city was not found.
     *
     * @param city The city to find.
     * @return the position of the city.
     */
    public Position getCityPosition(City city) {
        for (Map.Entry<Position, CityImpl> cityEntry : cityMap.entrySet()) {
            if (city == cityEntry.getValue()) {
                return cityEntry.getKey();
            }
        }
        return null;
    }

    /**
     * Returns an collection of all cities in the map.
     *
     * @return All cities.
     */
    public Collection<CityImpl> getCities() {
        return cityMap.values();
    }

    private static enum ShortLayoutType {
        // Ocean
        O,
        // Plains
        P,
        // Mountains
        M,
        // Forest
        F,
        // Hills
        H
    }
}
