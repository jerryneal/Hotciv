package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Skeleton class for AlphaCiv test cases
 * 
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 * 
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
public class TestAlphaCiv {
	private Game game;

	/** Fixture for alphaciv testing. */
	@Before
	public void setUp() {
		game = new GameImpl();
	}

	private void goToNextRound() {
		// Goes to the next round in the game, or terminates after 100 tries.
		for (int i = 0; i < 100; i++) {
			game.endOfTurn();
			if (game.getPlayerInTurn() == Player.RED)
				break;
		}
        if (game.getPlayerInTurn() != Player.RED) {
            throw new RuntimeException("The player wasn't red when it was supposed to be, or endOfTurn() doesn't work");
        }
	}

	@Test
	public void shouldHaveRedCityAt1_1() {
		City c = game.getCityAt(new Position(1, 1));
		assertNotNull("There should be a city at (1,1)", c);
		Player p = c.getOwner();
		assertEquals("City at (1,1) should be owned by red", Player.RED, p);
	}

	@Test
	public void redStartsFirst() {
		assertEquals(Player.RED, game.getPlayerInTurn());
	}

	@Test
	public void tileContantWhatItsGiven() {
		TileConstant testTile = new TileConstant(new Position(0, 0),
				GameConstants.OCEANS);
		assertEquals("ocean", testTile.getTypeString());
		assertEquals(new Position(0, 0), testTile.getPosition());
	}

	@Test
	public void theresOceanAt1_0() {
		assertEquals(game.getTileAt(new Position(1, 0)).getTypeString(),
				GameConstants.OCEANS);
	}

	@Test
	public void endTurnResultsInOtherPlayerGettingTurn() {
		game.endOfTurn();
		assertEquals(Player.BLUE, game.getPlayerInTurn());
		game.endOfTurn();
		assertEquals(Player.RED, game.getPlayerInTurn());
	}

	@Test
	public void ageStartsAt4000BC() {
		assertEquals(-4000, game.getAge());
	}

	@Test
	public void whenRedHasSecondTurnAgeIs3900BC() {
		goToNextRound();
		assertEquals(-3900, game.getAge());
	}

	@Test
	public void ageIs3800BCafter2Rounds() {
		for (int j = 0; j < 2; j++) {
			goToNextRound();
		}
		assertEquals(-3800, game.getAge());
	}

	@Test
	public void noWinnerWhenStartGame() {
		assertNull(game.getWinner());
	}

	@Test
	public void redWinsIn3000BC() {
		while (game.getAge() < -3000) {
			game.endOfTurn();
		}
		assertEquals(Player.RED, game.getWinner());
	}

	@Test
	public void cityAlwaysHasPopulation1() {
		City city = new CityImpl(Player.RED);
		assertEquals(1, city.getSize());
		city = game.getCityAt(new Position(1, 1));
		assertEquals(1, city.getSize());
	}

	@Test
	public void cityReturnsRightOwner() {
		City city = new CityImpl(Player.BLUE);
		assertEquals(Player.BLUE, city.getOwner());
		city = new CityImpl(Player.RED);
		assertEquals(Player.RED, city.getOwner());
	}

	@Test
	public void allTilesPopulated() {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				assertNotNull(game.getTileAt(new Position(i, j)));
			}
		}
	}

	@Test
	public void UnitsCannotMoveOverMountains() {
		// Mountain at (2,2)
		assertEquals(game.getTileAt(new Position(2, 2)).getTypeString(),
				GameConstants.MOUNTAINS); 
		// Red archer at (2,0)
		Unit archer = game.getUnitAt(new Position(2, 0));
		assertEquals(GameConstants.ARCHER, archer.getTypeString());
		assertEquals(Player.RED, archer.getOwner());
		// Move the archer 1 valid move.
		assertEquals(true, game.moveUnit(new Position(2, 0), new Position(2, 1)));
		// go to next round
		goToNextRound();
		// Move the archer to the mountain, should return false.
		assertEquals(false, game.moveUnit(new Position(2, 1), new Position(2, 2)));
	}
	@Test 
	public void archerCannotMove2TilesIn1Turn() {
		// Red archer at (2,0)
		Unit archer = game.getUnitAt(new Position(2, 0));
		assertEquals(GameConstants.ARCHER, archer.getTypeString());
		assertEquals(Player.RED, archer.getOwner());
		// Try to move the archer 2 tiles. 
		assertEquals(false, game.moveUnit(new Position(2, 0), new Position(4, 0)));
		// Try to move the archer 2 tiles in 2 steps.
		assertEquals(true, game.moveUnit(new Position(2, 0), new Position(3, 0)));
		assertEquals(false, game.moveUnit(new Position(3, 0), new Position(4, 0)));
	}
	@Test
	public void archerCanMove2TilesIn2Turns() {
        // Red archer at (2,0)
        Unit archer = game.getUnitAt(new Position(2, 0));
        assertEquals(GameConstants.ARCHER, archer.getTypeString());
        assertEquals(Player.RED, archer.getOwner());
        // Try to move the archer 2 tiles in 2 steps over 2 turns.
        assertEquals(true, game.moveUnit(new Position(2, 0), new Position(3, 0)));
        goToNextRound();
        assertEquals(true, game.moveUnit(new Position(3, 0), new Position(4, 0)));
	}
	@Test
	public void unitCanMoveDiagonal() {
        // Red archer at (2,0)
        Unit archer = game.getUnitAt(new Position(2, 0));
        assertEquals(GameConstants.ARCHER, archer.getTypeString());
        assertEquals(Player.RED, archer.getOwner());
        // Try to move the archer diagonal.
        assertEquals(true, game.moveUnit(new Position(2, 0), new Position(3, 1)));
	}
	@Test
	public void PositionCalculatesDistanceCorrectly() {
		assertEquals(2, Position.getDistance(new Position(0,0), new Position(2,2)));
        assertEquals(3, Position.getDistance(new Position(1,1), new Position(3,4)));
        assertEquals(1, Position.getDistance(new Position(0,0), new Position(1,0)));
	}
	@Test
	public void allUnitsProperlyInserted() {
		//Red archer at (2,0)
		Unit archer = game.getUnitAt(new Position(2, 0));
        assertEquals(GameConstants.ARCHER, archer.getTypeString());
        assertEquals(Player.RED, archer.getOwner());
		//Blue legion at (3,2)
        Unit legion = game.getUnitAt(new Position(3, 2));
        assertEquals(GameConstants.LEGION, legion.getTypeString());
        assertEquals(Player.BLUE, legion.getOwner());
		//Red Settler at (4,3)
        Unit settler = game.getUnitAt(new Position(4, 3));
        assertEquals(GameConstants.SETTLER, settler.getTypeString());
        assertEquals(Player.RED, settler.getOwner());
	}
	@Test
	public void battleEndsWithAttackersVictoryRedAttacks() {
		//Red archer from (2,0) moves to and attacks blue legion at (3,2)
		Unit archer = game.getUnitAt(new Position(2, 0));
        assertEquals(GameConstants.ARCHER, archer.getTypeString());
        assertEquals(Player.RED, archer.getOwner());
        assertEquals(true, game.moveUnit(new Position(2, 0), new Position(3, 1)));
        goToNextRound();
        //Attack occurs, red archer from (3,1) attacks blue legion (3,2)
        assertEquals(true, game.moveUnit(new Position(3, 1), new Position(3, 2)));
        //Blue legion is destroyed and red archer is now at (3,2)
        Unit archerAfterBattle = game.getUnitAt(new Position(3, 2));
        assertEquals(GameConstants.ARCHER, archerAfterBattle.getTypeString());
        assertEquals(Player.RED, archerAfterBattle.getOwner());
	}

    @Test
    public void battleEndsWithAttackersVictoryBlueAttacks() {
        //Red archer from (2,0) moves to and attacks blue legion at (3,2)
        Unit archer = game.getUnitAt(new Position(2, 0));
        assertEquals(GameConstants.ARCHER, archer.getTypeString());
        assertEquals(Player.RED, archer.getOwner());
        assertEquals(true, game.moveUnit(new Position(2, 0), new Position(3, 1)));
        goToNextRound();
        //Attack occurs, blue legion (3,2) attacks red archer from (3,1)
        assertEquals(true, game.moveUnit(new Position(3, 2), new Position(3, 1)));
        //Red archer is destroyed and blue legion is now at (3,1)
        Unit archerAfterBattle = game.getUnitAt(new Position(3, 1));
        assertEquals(GameConstants.LEGION, archerAfterBattle.getTypeString());
        assertEquals(Player.BLUE, archerAfterBattle.getOwner());
    }
}