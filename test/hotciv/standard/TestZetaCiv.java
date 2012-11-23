package hotciv.standard;

import hotciv.common.BaseGame;
import hotciv.common.GameWorld;
import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.variants.ZetaCiv;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * JUnit test class for the ZetaCiv specification.
 *
 * @author Erik
 *         Created: 16-11-12, 17:23
 */
public class TestZetaCiv {
    private Game game;

    private static Position redArcherPosition = new Position(2, 0);
    private static Position redCityPosition = new Position(1, 1);
    private static Position blueCityPosition = new Position(4, 1);
    private static Position blueLegionPosition = new Position(3, 2);
    private static Position redSettlerPosition = new Position(4, 3);

    @Before
    public void setUp() {
        game = new ZetaCiv().newGame();
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
    public void fastCityConquerWinsRed() {
        assertNull(game.getWinner());

        // Moving red archer to conquer blue city.
        game.moveUnit(redArcherPosition, redArcherPosition.getSouthEast());
        assertNull(game.getWinner());
        goToNextRound();
        game.moveUnit(redArcherPosition.getSouthEast(), redArcherPosition.getSouthEast().getSouth());
        assertEquals(Player.RED, game.getWinner());
    }

    @Test
    public void fastCityConquerWinsBlue() {
        game.endOfTurn();
        assertNull(game.getWinner());

        // Moving red archer to conquer blue city.
        game.moveUnit(blueLegionPosition, blueLegionPosition.getNorthWest());
        assertNull(game.getWinner());
        goToNextRound();
        game.endOfTurn();
        game.moveUnit(blueLegionPosition.getNorthWest(), blueLegionPosition.getNorthWest().getNorth());
        assertEquals(Player.BLUE, game.getWinner());
    }

    @Test
    public void noWinnerAfterManyRounds() {
        goToNextRound(100);
        assertNull(game.getWinner());
    }

    @Test
    public void cityConquerDoesntWinAfter20Rounds() {
        // Starting at round 1.
        goToNextRound(19);
        // Now at round 20. If he conquers a city
        assertNull(game.getWinner());

        // Moving red archer to conquer blue city.
        game.moveUnit(redArcherPosition, redArcherPosition.getSouthEast());
        assertNull(game.getWinner());
        goToNextRound();
        game.moveUnit(redArcherPosition.getSouthEast(), redArcherPosition.getSouthEast().getSouth());
        assertNull(game.getWinner());
    }

    @Test
    public void cityConquerDoesWinAfter19Rounds() {
        // Starting at round 1.
        goToNextRound(18);
        // Now at round 19. If he conquers a city
        assertNull(game.getWinner());

        // Moving red archer to conquer blue city.
        game.moveUnit(redArcherPosition, redArcherPosition.getSouthEast());
        assertNull(game.getWinner());
        goToNextRound();
        game.moveUnit(redArcherPosition.getSouthEast(), redArcherPosition.getSouthEast().getSouth());
        assertEquals(Player.RED, game.getWinner());
    }

    @Test
    public void tripleWinnerWinsAfter20RoundsBlueWins() {
        goToNextRound(20);
        game.endOfTurn();
        // Now at 21, the triple winner strategy is now in effect.

        Position redArcherPosition = new Position(10, 10);
        Position blueLegionPosition = new Position(10, 11);
        BaseGame game = (BaseGame) this.game;
        GameWorld gameWorld = game.getGameWorld();

        for (int i = 0; i < 3; i++) {
            assertNull(game.getWinner());
            gameWorld.removeUnit(redArcherPosition);
            gameWorld.placeNewUnit(redArcherPosition, GameConstants.ARCHER, Player.RED);
            gameWorld.removeUnit(blueLegionPosition);
            gameWorld.placeNewUnit(blueLegionPosition, GameConstants.LEGION, Player.BLUE);
            game.moveUnit(blueLegionPosition, redArcherPosition);
            goToNextRound();
            game.endOfTurn();
        }
        assertEquals(Player.BLUE, game.getWinner());
    }

    @Test
    public void tripleWinnerWinsAfter20RoundsRedWins() {
        goToNextRound(20);
        // Now at 21, the triple winner strategy is now in effect.

        Position redArcherPosition = new Position(10, 10);
        Position blueLegionPosition = new Position(10, 11);
        BaseGame game = (BaseGame) this.game;
        GameWorld gameWorld = game.getGameWorld();

        for (int i = 0; i < 3; i++) {
            assertNull(game.getWinner());
            gameWorld.removeUnit(redArcherPosition);
            gameWorld.placeNewUnit(redArcherPosition, GameConstants.ARCHER, Player.RED);
            gameWorld.removeUnit(blueLegionPosition);
            gameWorld.placeNewUnit(blueLegionPosition, GameConstants.LEGION, Player.BLUE);
            game.moveUnit(redArcherPosition, blueLegionPosition);
            goToNextRound();
        }
        assertEquals(Player.RED, game.getWinner());
    }
}
