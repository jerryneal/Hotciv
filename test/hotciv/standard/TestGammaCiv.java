package hotciv.standard;


import hotciv.common.BaseGame;
import hotciv.framework.*;

import hotciv.variants.GammaCiv;
import org.junit.*;
import static org.junit.Assert.*;


/**
 * JUnit test class for the GammaCiv specification.
 * 
 * @author : Erik
 * Date: 09-11-12, 11:38
 */
public class TestGammaCiv {
    private Game game;

    private Position redArcherPosition = new Position(2,0);
    private Position redCityPosition = new Position(1,1);
    private Position blueCityPosition = new Position(4,1);
    private Position blueLegionPosition= new Position(3, 2);
    private Position redSettlerPosition = new Position(4, 3);

    @Before
    public void setUp() {
        game = new GammaCiv().getGame();
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
    public void settlerAction() {
        Unit settler = game.getUnitAt(redSettlerPosition);
        assertNotNull(settler);
        assertEquals(Player.RED, settler.getOwner());
        assertNull(game.getCityAt(redSettlerPosition));

        // Making the action
        game.performUnitActionAt(redSettlerPosition);

        assertNull(game.getUnitAt(redSettlerPosition));
        City city = game.getCityAt(redSettlerPosition);
        assertNotNull(city);
        assertEquals(Player.RED, city.getOwner());
    }

    @Test
    public void settlerActionDoesNothingWhenPlayerNotInTurn() {
        game.endOfTurn();
        Unit settler = game.getUnitAt(redSettlerPosition);
        assertNotNull(settler);
        assertEquals(Player.RED, settler.getOwner());
        assertNull(game.getCityAt(redSettlerPosition));

        // Trying to make the action, this should do nothing.
        game.performUnitActionAt(redSettlerPosition);

        assertNotNull(game.getUnitAt(redSettlerPosition));
        assertNull(game.getCityAt(redSettlerPosition));
    }

    @Test
    public void archerAction() {
        Unit archer = game.getUnitAt(redArcherPosition);
        assertNotNull(archer);
        assertEquals(Player.RED, archer.getOwner());
        int beforeDefensive = archer.getDefensiveStrength();

        // Making the action.
        game.performUnitActionAt(redArcherPosition);

        assertEquals(beforeDefensive * 2, game.getUnitAt(redArcherPosition).getDefensiveStrength());
        assertFalse(game.moveUnit(redArcherPosition, redArcherPosition.getEast()));
    }

    @Test
    public void archerActionDoesNothingWhenPlayerNotInTurn() {
        game.endOfTurn();
        Unit archer = game.getUnitAt(redArcherPosition);
        assertNotNull(archer);
        assertEquals(Player.RED, archer.getOwner());
        int beforeDefensive = archer.getDefensiveStrength();

        // Trying to make the action, this should do nothing.
        game.performUnitActionAt(redArcherPosition);

        assertEquals(beforeDefensive, game.getUnitAt(redArcherPosition).getDefensiveStrength());

        // Making sure its red's turn again.
        goToNextRound();
        assertTrue(game.moveUnit(redArcherPosition, redArcherPosition.getEast()));
    }

    @Test
    public void archerActionOnProducedArcher() {
        City city = game.getCityAt(redCityPosition);
        assertNull(game.getUnitAt(redCityPosition));
        assertEquals(Player.RED, city.getOwner());
        game.changeProductionInCityAt(redCityPosition, GameConstants.ARCHER);
        goToNextRound(5);
        assertEquals("After 5 rounds there should be a ARCHER on the city, because we specified it. ",
                GameConstants.ARCHER, game.getUnitAt(redCityPosition).getTypeString());

        Position redArcherPosition = redCityPosition;
        Unit archer = game.getUnitAt(redArcherPosition);
        assertNotNull(archer);
        assertEquals(Player.RED, archer.getOwner());
        int beforeDefensive = archer.getDefensiveStrength();

        // Making the action.
        game.performUnitActionAt(redArcherPosition);

        assertEquals(beforeDefensive * 2, game.getUnitAt(redArcherPosition).getDefensiveStrength());
        assertFalse(game.moveUnit(redArcherPosition, redArcherPosition.getEast()));
    }

    @Test
    public void settlerActionOnProducedSettler()  {
        City city = game.getCityAt(redCityPosition);
        assertNull(game.getUnitAt(redCityPosition));
        assertEquals(Player.RED, city.getOwner());
        goToNextRound(5);
        assertEquals("After 5 rounds there should be a SETTLER on the city",
                GameConstants.SETTLER, game.getUnitAt(redCityPosition).getTypeString());

        game.moveUnit(redCityPosition, redCityPosition.getEast());
        Position redSettlerPosition = redCityPosition.getEast();

        Unit settler = game.getUnitAt(redSettlerPosition);
        assertNotNull(settler);
        assertEquals(Player.RED, settler.getOwner());
        assertNull(game.getCityAt(redSettlerPosition));

        // Making the action
        game.performUnitActionAt(redSettlerPosition);

        assertNull(game.getUnitAt(redSettlerPosition));
        City settlerCity = game.getCityAt(redSettlerPosition);
        assertNotNull(settlerCity);
        assertEquals(Player.RED, settlerCity.getOwner());

    }

    @Test
    public void settlerCannotBuildCityOnACity() {
        City city = game.getCityAt(redCityPosition);
        assertNull(game.getUnitAt(redCityPosition));
        assertEquals(Player.RED, city.getOwner());
        goToNextRound(5);
        assertEquals("After 5 rounds there should be a SETTLER on the city",
                GameConstants.SETTLER, game.getUnitAt(redCityPosition).getTypeString());

        Position redSettlerPosition = redCityPosition;

        Unit settler = game.getUnitAt(redSettlerPosition);
        assertNotNull(settler);
        assertEquals(Player.RED, settler.getOwner());

        // Trying to make a city, where the already is a city. Resulting in the settler staying put.
        game.performUnitActionAt(redSettlerPosition);

        settler = game.getUnitAt(redSettlerPosition);
        assertNotNull(settler);
        assertEquals(Player.RED, settler.getOwner());
    }
}
