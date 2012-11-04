package hotciv.standard;

import java.util.HashMap;
import java.util.Map;

import hotciv.framework.*;
import hotciv.standard.units.*;

/**
 * Skeleton implementation of HotCiv.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */

public class AlphaCiv implements Game {
    private Map<Position, Tile> tileMap = new HashMap<Position, Tile>();
    private UnitMap unitMap = new UnitMap();
    private Map<Position, CityImpl> cityMap = new HashMap<Position, CityImpl>();

    private Player playerTurn;

    private int age = -4000;
    public AlphaCiv() {
        setupTiles();
        // Player starts
        playerTurn = Player.RED;
        // Red has a city at (1,1)
        cityMap.put(new Position(1,1), new CityImpl(Player.RED));
        // Red has a archer at (2,0)
        unitMap.place(new Position(2, 0), new Archer(Player.RED));
        // Blue has a legion at (3,2)
        unitMap.place(new Position(3, 2), new Legion(Player.BLUE));
        // Red has a settler at (4,3)
        unitMap.place(new Position(4, 3), new Settler(Player.RED));

        // Blue has a city at (4,1)
        cityMap.put(new Position(4,1), new CityImpl(Player.BLUE));
    }
    private void setupTiles() {
        // Ocean at 1,0
        tileMap.put(new Position(1,0), new TileConstant(new Position(1,0), GameConstants.OCEANS));
        // Hills at 0,1
        tileMap.put(new Position(0,1), new TileConstant(new Position(1,0), GameConstants.HILLS));
        // Mountain at 2,2
        tileMap.put(new Position(2,2), new TileConstant(new Position(2,2), GameConstants.MOUNTAINS));
    }
    public Tile getTileAt(Position p) {
        Tile result = tileMap.get(p);
        if (result == null) {
            return new TileConstant(p, GameConstants.PLAINS);
        }
        return tileMap.get(p);
    }

    public UnitImpl getUnitAt(Position p) {
        return unitMap.get(p);
    }

    public City getCityAt(Position p) {
        return cityMap.get(p);
    }

    public Player getPlayerInTurn() {
        return playerTurn;
    }

    public Player getWinner() {
        if (age >= -3000) {
            return Player.RED;
        }
        else {
            return null;
        }
    }

    public int getAge() {
        return age;
    }

    public boolean moveUnit(Position from, Position to) {
        UnitImpl unit = getUnitAt(from);
        UnitImpl unitAtTarget = getUnitAt(to);
        Tile targetTile = getTileAt(to);

        // Tests if we try to move to far.
        int distance = Position.getDistance(from, to);
        if (distance > unit.getMoveCount()) {
            return false;
        }

        // See if we try to move to an type of tile we cannot move to.
        if (targetTile.getTypeString().equals(GameConstants.MOUNTAINS)) {
            return false;
        }

        //If there is a unit at the target, if it is an enemy, attack it, if it is the players unit, reject move.
        if (unitAtTarget != null && unitAtTarget.getOwner() != unit.getOwner()) {
            unitMap.remove(to);
        }
        else if (unitAtTarget != null && unitAtTarget.getOwner() == unit.getOwner()) {
            return false;
        }

        unit.movedUnit(distance);

        // Moves the unit.
        unitMap.remove(from);
        unitMap.place(to, unit);
        return true;

    }

    public void endOfTurn() {
        if (playerTurn == Player.RED) {
            playerTurn = Player.BLUE;
        }
        else if (playerTurn == Player.BLUE) {
            playerTurn = Player.RED;
            endOfRound();
        }
        else {
            throw new RuntimeException("Unrecognized player: " + playerTurn);
        }
    }
    private void endOfRound() {
        // Aging the world.
        age += 100;

        // Telling all unites that the round has ended, so they can reset their moveCount.
        for (UnitImpl unit : unitMap.getUnits()) {
            unit.roundEnded();
        }

        // Producing some units.
        for (Map.Entry<Position, CityImpl> cityEntry : cityMap.entrySet()) {
            CityImpl city = cityEntry.getValue();
            Position cityPosition = cityEntry.getKey();

            city.increaseProductionAmount(6);
            int productionAmount = city.getProductionAmount();
            String produces = city.getProduction();

            UnitImpl unit = null;

            Player cityOwner = city.getOwner();

            if (produces.equals(GameConstants.SETTLER)) {
                if (productionAmount >= 30) {
                    city.decreaseProductionAmount(30);
                    unit = new Settler(city.getOwner());
                }
            }
            else if (produces.equals(GameConstants.ARCHER)) {
                if (productionAmount >= 10) {
                    city.decreaseProductionAmount(10);
                    unit = new Archer(city.getOwner());
                }
            }
            else if (produces.equals(GameConstants.LEGION)) {
                if (productionAmount >= 15) {
                    city.decreaseProductionAmount(15);
                    unit = new Legion(city.getOwner());
                }
            }
            if (unit != null) {
                unitMap.placeNear(cityPosition, unit);
            }
        }
    }

    public void changeWorkForceFocusInCityAt(Position p, String balance) {
    }

    public void changeProductionInCityAt(Position p, String unitType) {
    }

    public void performUnitActionAt(Position p) {
    }
}
