package hotciv.standard;

import hotciv.common.BaseGame;
import hotciv.common.CityImpl;
import hotciv.common.GameWorld;
import hotciv.common.StandardTile;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.variants.EtaCiv;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * //TODO: Document!
 *
 * @author : Erik
 *         Date: 25-11-12, 18:23
 */
public class TestEtaCiv {
    private BaseGame game;
    private GameWorld gameWorld;

    @Before
    public void setUp() {
        // Setting up a version of EpsilonCiv where the dice is replaced with a dice that always returns a specified value.
        game = (BaseGame) new EtaCiv().newGame();
        gameWorld = game.getGameWorld();
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
    public void changeWorkforceFocusInCity() {
        Position redCityPosition = new Position(10, 10);

        gameWorld.placeCity(redCityPosition, new CityImpl(Player.RED));

        assertEquals(GameConstants.productionFocus, game.getCityAt(redCityPosition).getWorkforceFocus());

        game.changeWorkForceFocusInCityAt(redCityPosition, GameConstants.foodFocus);

        assertEquals(GameConstants.foodFocus, game.getCityAt(redCityPosition).getWorkforceFocus());
    }

    @Test
    public void testProduction() {
        Position redCityPosition = new Position(10, 10);

        gameWorld.placeCity(redCityPosition, new CityImpl(Player.RED));

        gameWorld.placeTile(redCityPosition.getNorth(), new StandardTile(redCityPosition.getNorth(), GameConstants.OCEANS));
        gameWorld.placeTile(redCityPosition.getNorthEast(), new StandardTile(redCityPosition.getNorthEast(), GameConstants.FOREST));
        gameWorld.placeTile(redCityPosition.getEast(), new StandardTile(redCityPosition.getEast(), GameConstants.MOUNTAINS));
        gameWorld.placeTile(redCityPosition.getSouthEast(), new StandardTile(redCityPosition.getSouthEast(), GameConstants.HILLS));

        goToNextRound(8);
        assertNull(game.getUnitAt(redCityPosition));

        // With all production considered, and considering that the city size increases. Next round there should be an archer on the city.
        goToNextRound();
        assertNotNull(game.getTileAt(redCityPosition));
    }

    @Test
    public void testFood() {
        Position redCityPosition = new Position(10, 10);

        gameWorld.placeCity(redCityPosition, new CityImpl(Player.RED));

        assertEquals(1, game.getCityAt(redCityPosition).getSize());
        goToNextRound(2);
        assertEquals(1, game.getCityAt(redCityPosition).getSize());
        goToNextRound();
        assertEquals(2, game.getCityAt(redCityPosition).getSize());
    }

    @Test
    public void testFoodOnMountain() {
        Position redCityPosition = new Position(10, 10);

        gameWorld.placeCity(redCityPosition, new CityImpl(Player.RED));

        gameWorld.placeTile(redCityPosition, new StandardTile(redCityPosition, GameConstants.MOUNTAINS));

        assertEquals(1, game.getCityAt(redCityPosition).getSize());
        goToNextRound(8);
        assertEquals(1, game.getCityAt(redCityPosition).getSize());
        goToNextRound();
        assertEquals(2, game.getCityAt(redCityPosition).getSize());
    }
}
