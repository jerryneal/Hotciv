package hotciv.standard;

import hotciv.framework.*;
import hotciv.variants.DeltaCiv;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * JUnit test class for the DeltaCiv specification.
 */
public class TestDeltaCiv {
    private Game game;

    @Before
    public void setUp() {
        game = new DeltaCiv().newGame();
    }

    @Test
    public void testSomeTilePositions() {
        // randomly chosen tiles to test
        assertEquals(game.getTileAt(new Position(3, 7)).getTypeString(), GameConstants.PLAINS);
        assertEquals(game.getTileAt(new Position(15, 13)).getTypeString(), GameConstants.PLAINS);
        assertEquals(game.getTileAt(new Position(6, 6)).getTypeString(), GameConstants.OCEANS);
        assertEquals(game.getTileAt(new Position(0, 15)).getTypeString(), GameConstants.OCEANS);
        assertEquals(game.getTileAt(new Position(11, 4)).getTypeString(), GameConstants.MOUNTAINS);
        assertEquals(game.getTileAt(new Position(1, 9)).getTypeString(), GameConstants.FOREST);
        assertEquals(game.getTileAt(new Position(8, 9)).getTypeString(), GameConstants.HILLS);

        // Red city at (8, 12) and blue city at (4, 5)
        City cr = game.getCityAt(new Position(8, 12));
        assertNotNull("There should be a city at (8, 12)", cr);
        Player pr = cr.getOwner();
        assertEquals("City at (8, 12) should be owned by red", Player.RED, pr);
        City cb = game.getCityAt(new Position(4, 5));
        assertNotNull("There should be a city at (4, 5)", cb);
        Player pb = cb.getOwner();
        assertEquals("City at (4, 5) should be owned by blue", Player.BLUE, pb);
    }
}
