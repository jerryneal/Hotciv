package hotciv.standard;

import hotciv.framework.*;
import hotciv.variants.AlphaCiv;
import hotciv.variants.GameLogger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit test class for the AlphaCiv specification.
 */

public class TestAlphaCiv {
    private Game game;

    private static Position redArcherPosition = new Position(2, 0);
    private static Position redCityPosition = new Position(1, 1);
    private static Position blueCityPosition = new Position(4, 1);
    private static Position blueLegionPosition = new Position(3, 2);
    private static Position redSettlerPosition = new Position(4, 3);

    /**
     * Fixture for alphaciv testing.
     */
    @Before
    public void setUp() {
        game = new GameLogger(new AlphaCiv().newGame());
    }

    private void goToNextRound() {
        // Goes to the next round in the game, or terminates after 100 tries.
        for (int i = 0; i < 4; i++) {
            game.endOfTurn();
            if (game.getPlayerInTurn() == Player.RED)
                break;
        }
        if (game.getPlayerInTurn() != Player.RED) {
            throw new RuntimeException("The player wasn't red when it was supposed to be, or endOfTurn() doesn't work");
        }
    }

    private void goToNextRound(int n) {
        for (int i = 0; i < n; i++) {
            goToNextRound();
        }
    }

    @Test
    public void shouldHaveRedCityAt1_1() {
        City c = game.getCityAt(redCityPosition);
        assertNotNull("There should be a city at (1,1)", c);
        Player p = c.getOwner();
        assertEquals("City at (1,1) should be owned by red", Player.RED, p);
    }

    @Test
    public void shouldHaveBlueCityAt4_1() {
        City c = game.getCityAt(blueCityPosition);
        assertNotNull("There should be a city at (4,1)", c);
        Player p = c.getOwner();
        assertEquals("City at (4,1) should be owned by blue", Player.BLUE, p);
    }

    @Test
    public void redStartsFirst() {
        assertEquals(Player.RED, game.getPlayerInTurn());
    }

    @Test
    public void allTilesProperlyInserted() {
        assertEquals(game.getTileAt(new Position(1, 0)).getTypeString(), GameConstants.OCEANS);
        assertEquals(game.getTileAt(new Position(0, 1)).getTypeString(), GameConstants.HILLS);
        assertEquals(game.getTileAt(new Position(2, 2)).getTypeString(), GameConstants.MOUNTAINS);
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
        goToNextRound(2);
        assertEquals(-3800, game.getAge());
    }

    @Test
    public void noWinnerWhenStartGame() {
        assertNull(game.getWinner());
    }

    @Test
    public void redWinsIn3000BC() {
        int breakCounter = 0;
        while (game.getAge() < -3000 && breakCounter++ < 1000) {
            game.endOfTurn();
        }
        assertEquals(Player.RED, game.getWinner());
    }

    @Test
    public void cityAlwaysHasPopulation1() {
        City city = game.getCityAt(redCityPosition);
        assertEquals(1, city.getSize());
        city = game.getCityAt(blueCityPosition);
        assertEquals(1, city.getSize());
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
    public void unitCannotMoveToOcean() {
        assertFalse(game.moveUnit(redArcherPosition, redArcherPosition.getNorth()));
    }

    @Test
    public void UnitsCannotMoveOverMountains() {
        // Mountain at (2,2)
        assertEquals(game.getTileAt(new Position(2, 2)).getTypeString(),
                GameConstants.MOUNTAINS);
        // Red archer at (2,0)
        Unit archer = game.getUnitAt(redArcherPosition);
        assertEquals(GameConstants.ARCHER, archer.getTypeString());
        assertEquals(Player.RED, archer.getOwner());
        // Move the archer 1 valid move.
        assertEquals(true, game.moveUnit(redArcherPosition, redArcherPosition.getEast()));
        // go to next round
        goToNextRound();
        // Move the archer to the mountain, should return false.
        assertEquals(false, game.moveUnit(redArcherPosition.getEast(), redArcherPosition.getEast().getEast()));
    }

    @Test
    public void archerCannotMove2TilesIn1Turn() {
        // Red archer at (2,0)
        Unit archer = game.getUnitAt(redArcherPosition);
        assertEquals(GameConstants.ARCHER, archer.getTypeString());
        assertEquals(Player.RED, archer.getOwner());
        // Try to move the archer 2 tiles.
        assertEquals(false, game.moveUnit(redArcherPosition, redArcherPosition.getEast().getEast()));
        // Try to move the archer 2 tiles in 2 steps.
        assertEquals(true, game.moveUnit(redArcherPosition, redArcherPosition.getSouth()));
        assertEquals(false, game.moveUnit(redArcherPosition.getSouth(), redArcherPosition.getSouth().getSouth()));
    }

    @Test
    public void archerCanMove2TilesIn2Turns() {
        // Red archer at (2,0)
        Unit archer = game.getUnitAt(redArcherPosition);
        assertEquals(GameConstants.ARCHER, archer.getTypeString());
        assertEquals(Player.RED, archer.getOwner());
        // Try to move the archer 2 tiles in 2 steps over 2 turns.
        assertEquals(true, game.moveUnit(redArcherPosition, redArcherPosition.getSouth()));
        goToNextRound();
        assertEquals(true, game.moveUnit(redArcherPosition.getSouth(), redArcherPosition.getSouth().getSouth()));
    }

    @Test
    public void unitCanMoveDiagonal() {
        // Red archer at (2,0)
        Unit archer = game.getUnitAt(redArcherPosition);
        assertEquals(GameConstants.ARCHER, archer.getTypeString());
        assertEquals(Player.RED, archer.getOwner());
        // Try to move the archer diagonal.
        assertEquals(true, game.moveUnit(redArcherPosition, redArcherPosition.getSouthEast()));
    }

    @Test
    public void PositionCalculatesDistanceCorrectly() {
        assertEquals(2, Position.getDistance(new Position(0, 0), new Position(2, 2)));
        assertEquals(3, Position.getDistance(redCityPosition, new Position(3, 4)));
        assertEquals(1, Position.getDistance(new Position(0, 0), new Position(1, 0)));
        assertEquals(1, Position.getDistance(new Position(2, 2), new Position(3, 3)));
        assertEquals(2, Position.getDistance(new Position(1, 10), new Position(3, 10)));
    }

    @Test
    public void allUnitsProperlyInserted() {
        //Red archer at (2,0)
        Unit archer = game.getUnitAt(redArcherPosition);
        assertEquals(GameConstants.ARCHER, archer.getTypeString());
        assertEquals(Player.RED, archer.getOwner());
        //Blue legion at (3,2)
        Unit legion = game.getUnitAt(blueLegionPosition);
        assertEquals(GameConstants.LEGION, legion.getTypeString());
        assertEquals(Player.BLUE, legion.getOwner());
        //Red Settler at (4,3)
        Unit settler = game.getUnitAt(redSettlerPosition);
        assertEquals(GameConstants.SETTLER, settler.getTypeString());
        assertEquals(Player.RED, settler.getOwner());
    }

    @Test
    public void battleEndsWithAttackersVictoryRedAttacks() {
        //Red archer from (2,0) moves to and attacks blue legion at (3,2)
        Unit archer = game.getUnitAt(redArcherPosition);
        assertEquals(GameConstants.ARCHER, archer.getTypeString());
        assertEquals(Player.RED, archer.getOwner());
        assertEquals(true, game.moveUnit(redArcherPosition, redArcherPosition.getSouthEast()));
        goToNextRound();
        //Attack occurs, red archer from (3,1) attacks blue legion (3,2)
        assertEquals(true, game.moveUnit(redArcherPosition.getSouthEast(), blueLegionPosition));
        //Blue legion is destroyed and red archer is now at (3,2)
        Unit archerAfterBattle = game.getUnitAt(blueLegionPosition);
        assertEquals(GameConstants.ARCHER, archerAfterBattle.getTypeString());
        assertEquals(Player.RED, archerAfterBattle.getOwner());
    }

    @Test
    public void battleEndsWithAttackersVictoryBlueAttacks() {
        //Red archer from (2,0) moves to and attacks blue legion at (3,2)
        Unit archer = game.getUnitAt(redArcherPosition);
        assertEquals(GameConstants.ARCHER, archer.getTypeString());
        assertEquals(Player.RED, archer.getOwner());
        assertEquals(true, game.moveUnit(redArcherPosition, redArcherPosition.getSouthEast()));
        goToNextRound();
        // Make it blue players turn.
        game.endOfTurn();
        //Attack occurs, blue legion (3,2) attacks red archer from (3,1)
        assertEquals(true, game.moveUnit(blueLegionPosition, redArcherPosition.getSouthEast()));
        //Red archer is destroyed and blue legion is now at (3,1)
        Unit archerAfterBattle = game.getUnitAt(redArcherPosition.getSouthEast());
        assertEquals(GameConstants.LEGION, archerAfterBattle.getTypeString());
        assertEquals(Player.BLUE, archerAfterBattle.getOwner());
    }

    @Test
    public void cannotMoveOtherPlayersUnit() {
        // Starts with red players turn, trying to move blue unit.
        assertFalse(game.moveUnit(blueLegionPosition, redArcherPosition.getSouthEast()));
        // Now red players turn.
        game.endOfTurn();
        assertFalse(game.moveUnit(redArcherPosition, redArcherPosition.getSouthEast()));
    }

    @Test
    public void movingToUnoccupiedEnemyCityGrantsMovingPlayerCity() {
        //Blue Legion attacks red city at (1,1)
        game.endOfTurn();
        Unit legion = game.getUnitAt(blueLegionPosition);
        game.moveUnit(blueLegionPosition, blueLegionPosition.getNorthWest());
        goToNextRound();
        game.endOfTurn();
        game.moveUnit(blueLegionPosition.getNorthWest(), blueLegionPosition.getNorthWest().getNorth());
        City city = game.getCityAt(redCityPosition);
        assertEquals(legion.getOwner(), city.getOwner());
    }

    @Test
    public void movingToOccupiedEnemyCityGrantsMovingPlayerCityAfterBattle() {
        //Red archer moves into city at (1,1)
        game.moveUnit(redArcherPosition, redArcherPosition.getNorthEast());
        game.endOfTurn();
        //Blue Legion attacks red city at (1,1)
        Unit legion = game.getUnitAt(blueLegionPosition);
        game.moveUnit(blueLegionPosition, blueLegionPosition.getNorthWest());
        goToNextRound();
        game.endOfTurn();
        game.moveUnit(blueLegionPosition.getNorthWest(), blueLegionPosition.getNorthWest().getNorth());
        City city = game.getCityAt(redCityPosition);
        assertEquals(legion.getOwner(), city.getOwner());
    }


    @Test
    public void testCityProductionGrows6() {
        City city = game.getCityAt(redCityPosition);
        assertEquals(0, city.getProductionAmount());
        goToNextRound();
        assertEquals(6, city.getProductionAmount());
        goToNextRound();
        assertEquals(12, city.getProductionAmount());
    }

    @Test
    public void testCityProductionBlueCity() {
        // This test assumes that a settler is produced after exactly 5 rounds. Again and again and again... And that is the case in AlphaCiv
        // We look at the city at (4,1), first we see that it produces an Settler after 6 rounds. Because we haven't specified that it should produce something else.
        City city = game.getCityAt(blueCityPosition);
        assertNull(game.getUnitAt(blueCityPosition));
        assertEquals(Player.BLUE, city.getOwner());
        goToNextRound(5);
        assertEquals("After 5 rounds there should be a settler on the city, when no production is specified",
                GameConstants.SETTLER, game.getUnitAt(blueCityPosition).getTypeString());
        assertNull("After 5 rounds there should not be a unit just north of the city",
                game.getUnitAt(redCityPosition.getNorth()));
        goToNextRound(5);
        assertEquals("After 5 more rounds there should be a settler just north of the city",
                GameConstants.SETTLER, game.getUnitAt(blueCityPosition.getNorth()).getTypeString());

        // There is already a legion at 3,2, so the next unit is placed at 4,2.
        goToNextRound(5);
        assertEquals("After 5 more rounds there should be a settler just east of the city",
                GameConstants.SETTLER, game.getUnitAt(blueCityPosition.getEast()).getTypeString());
        // A full test of the "clockWise" behaviour is in testCityProductionRedCity();
    }

    @Test
    public void testCityProductionRedCity() {
        // This test assumes that a settler is produced after exactly 5 rounds. Again and again and again...  And that is the case in AlphaCiv
        // We look at the city at (1,1), first we see that it produces an Settler after 6 rounds. Because we haven't specified that it should produce something else.
        // This test only works if the code correctly handles that a unit cannot be placed on a mountain or an ocean, and cannot be placed on top of another existing unit.
        City city = game.getCityAt(redCityPosition);
        assertNull(game.getUnitAt(redCityPosition));
        assertEquals(Player.RED, city.getOwner());
        goToNextRound(5);
        assertEquals("After 5 rounds there should be a settler on the city, when no other production is specified",
                GameConstants.SETTLER, game.getUnitAt(redCityPosition).getTypeString());
        assertEquals("Red should own the unit",
                Player.RED, game.getUnitAt(redCityPosition).getOwner());
        assertNull("After 5 rounds there should not be a unit just north of the city",
                game.getUnitAt(redCityPosition.getNorth()));
        goToNextRound(5);
        assertEquals("After 5 more rounds there should be a settler just north of the city",
                GameConstants.SETTLER, game.getUnitAt(redCityPosition.getNorth()).getTypeString());

        goToNextRound(5);
        assertEquals("After 5 more rounds there should be a settler just northEast of the city",
                GameConstants.SETTLER, game.getUnitAt(redCityPosition.getNorthEast()).getTypeString());
        assertNull("At this point there shouldn't be a unit at (1,2)",
                game.getUnitAt(new Position(1, 2)));

        goToNextRound(5);
        assertEquals("After 5 more rounds there should be a settler just East of the city",
                GameConstants.SETTLER, game.getUnitAt(redCityPosition.getEast()).getTypeString());

        goToNextRound(9);
        assertNull("At this point there shouldn't be a unit north west of the city",
                game.getUnitAt(new Position(0, 0)));
        goToNextRound();
        assertEquals("At this point there should be a settler at north west of the city",
                GameConstants.SETTLER, game.getUnitAt(redCityPosition.getNorthWest()).getTypeString());
        goToNextRound(5);
        assertEquals("At this point there should be a settler 1 north and 2 east of the city",
                GameConstants.SETTLER, game.getUnitAt(redCityPosition.getNorthEast().getEast()).getTypeString());
    }

    @Test
    public void citiesCanProduceArchers() {
        City city = game.getCityAt(redCityPosition);
        assertNull(game.getUnitAt(redCityPosition));
        assertEquals(Player.RED, city.getOwner());
        game.changeProductionInCityAt(redCityPosition, GameConstants.ARCHER);
        goToNextRound(5);
        assertEquals("After 5 rounds there should be a ARCHER on the city, because we specified it. ",
                GameConstants.ARCHER, game.getUnitAt(redCityPosition).getTypeString());
    }

    @Test
    public void citiesCanProduceLegions() {
        City city = game.getCityAt(redCityPosition);
        assertNull(game.getUnitAt(redCityPosition));
        game.changeProductionInCityAt(redCityPosition, GameConstants.LEGION);
        assertEquals(Player.RED, city.getOwner());
        goToNextRound(5);
        assertEquals("After 5 rounds there should be a LEGION on the city, because we specified it. ",
                GameConstants.LEGION, game.getUnitAt(redCityPosition).getTypeString());
    }

    @Test
    public void cannotMoveUnitToFriendlyUnit() {
        // Moving the red archer all the way to the red setler. Long way, but only way in AlphaCiv.
        assertEquals(GameConstants.ARCHER, game.getUnitAt(redArcherPosition).getTypeString());
        Position newPosition1 = redArcherPosition.getSouth();
        assertTrue(game.moveUnit(redArcherPosition, newPosition1));
        goToNextRound();
        Position newPosition2 = newPosition1.getSouth();
        assertTrue(game.moveUnit(newPosition1, newPosition2));
        goToNextRound();
        Position newPosition3 = newPosition2.getSouthEast();
        assertTrue(game.moveUnit(newPosition2, newPosition3));
        goToNextRound();
        Position newPosition4 = newPosition3.getEast();
        assertTrue(game.moveUnit(newPosition3, newPosition4));
        goToNextRound();
        // And now it should reject.
        Position newPosition5 = newPosition4.getNorthEast();
        assertFalse(game.moveUnit(newPosition4, newPosition5));
    }
}