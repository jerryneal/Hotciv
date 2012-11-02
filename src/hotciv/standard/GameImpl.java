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

public class GameImpl implements Game {
	private Map<Position, Tile> tileMap = new HashMap<Position, Tile>();
	private Map<Position, Unit> unitMap = new HashMap<Position, Unit>();
	private Map<Position, City> cityMap = new HashMap<Position, City>();
	
	// A map telling us how much each unit has moved in this round. 
	private Map<Unit, Integer> movedUnits = new HashMap<Unit, Integer>();
	
	private Player playerTurn;
	
	private int age = -4000;
	public GameImpl() {
		setupTiles();
		// Player starts
		playerTurn = Player.RED;
		// Red has a city at (1,1).
		cityMap.put(new Position(1,1), new CityImpl(Player.RED));
		// Red has a archer at (2,0);
		unitMap.put(new Position(2,0), new Archer(Player.RED));
	}
	private void setupTiles() {
		// Ocean at 1,0
		tileMap.put(new Position(1,0), new TileConstant(new Position(1,0), GameConstants.OCEANS));
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

	public Unit getUnitAt(Position p) {
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
		Unit unit = getUnitAt(from);
		Tile targetTile = getTileAt(to);
		
		// See if we try to move to an type of tile we cannot move to.
		if (targetTile.getTypeString().equals(GameConstants.MOUNTAINS)) {
			return false;
		}
		
		// Tests if we try to move to far. 
		System.out.println("Move from : " + from + " to: " + to + " dis: " + Position.getDistance(from, to));
		int unitHasMoved = 0;
		if (movedUnits.get(unit) != null) {
			unitHasMoved = movedUnits.get(unit);
		}
		int distance = Position.getDistance(from, to);
		if (distance  + unitHasMoved > unit.getMoveCount()) {
			System.out.println(unit.getMoveCount());
			return false;
		}
		movedUnits.put(unit, unitHasMoved + distance);
		
		// Moves the unit. 
		unitMap.remove(from);
		unitMap.put(to, unit);
		System.out.println("Returns true");
		return true;
	
	}

	public void endOfTurn() {
		if (playerTurn == Player.RED) {
			playerTurn = Player.BLUE;
		}
		else if (playerTurn == Player.BLUE) {
			playerTurn = Player.RED;
			age += 100;
		}
		else {
			throw new RuntimeException("Unrecognized player: " + playerTurn);
		}
	}

	public void changeWorkForceFocusInCityAt(Position p, String balance) {
	}

	public void changeProductionInCityAt(Position p, String unitType) {
	}

	public void performUnitActionAt(Position p) {
	}
}
