package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.variants.BetaCiv;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author : Erik
 *         Date: 09-11-12, 11:38
 */
public class TestBetaCiv {
    Game game;

    Position redArcherPosition = new Position(2, 0);
    private Position redCityPosition = new Position(1, 1);
    private Position blueCityPosition = new Position(4, 1);
    private Position blueLegionPosition = new Position(3, 2);
    private Position redSettlerPosition = new Position(4, 3);

    @Before
    public void setUp() {
        game = new BetaCiv().getGame();
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
    public void testWorldAging() {
        goToNextRound(39);
        assertEquals(-100, game.getAge());
        goToNextRound();
        assertEquals(-1, game.getAge());
        goToNextRound();
        assertEquals(1, game.getAge());
        goToNextRound();
        assertEquals(50, game.getAge());
        goToNextRound();
        assertEquals(100, game.getAge());
        goToNextRound(33);
        assertEquals(1750, game.getAge());
        goToNextRound();
        assertEquals(1775, game.getAge());
        goToNextRound();
        assertEquals(1800, game.getAge());
        goToNextRound(4);
        assertEquals(1900, game.getAge());
        goToNextRound();
        assertEquals(1905, game.getAge());
        goToNextRound();
        assertEquals(1910, game.getAge());
        goToNextRound(12);
        assertEquals(1970, game.getAge());
        goToNextRound(12);
        assertEquals(1982, game.getAge());

    }

    @Test
    public void winsAfterConqueringAllCitiesRedConquers() {
        assertNull(game.getWinner());
        goToNextRound();
        assertNull(game.getWinner());
        // Now red conquers the blue city.
        game.moveUnit(redArcherPosition, redArcherPosition.getSouthEast());
        goToNextRound();
        // Doesn't have it yet.
        assertNull(game.getWinner());
        // In this line, red wins.
        game.moveUnit(redArcherPosition.getSouthEast(), redArcherPosition.getSouthEast().getSouth());
        assertEquals(Player.RED, game.getWinner());
    }

    @Test
    public void winsAfterConqueringAllCitiesBlueConquers() {
        game.endOfTurn();
        assertNull(game.getWinner());
        game.moveUnit(blueLegionPosition, blueLegionPosition.getNorthWest());
        goToNextRound();
        game.endOfTurn();
        assertNull(game.getWinner());
        // Here blue conquers the red city.
        game.moveUnit(blueLegionPosition.getNorthWest(), blueLegionPosition.getNorthWest().getNorth());
        assertEquals(Player.BLUE, game.getWinner());
    }
}
