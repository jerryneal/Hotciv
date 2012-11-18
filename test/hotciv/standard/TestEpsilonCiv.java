package hotciv.standard;

import hotciv.common.BaseGame;
import hotciv.common.GameWorld;
import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.variants.EpsilonCiv;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * JUnit test class for the EpsilonCiv specification.
 *
 * @author Erik
 *         Created: 16-11-12, 15:50
 */
public class TestEpsilonCiv {
    private Game game;

    private Position redArcherPosition = new Position(2, 0);
    private Position redCityPosition = new Position(1, 1);
    private Position blueCityPosition = new Position(4, 1);
    private Position blueLegionPosition = new Position(3, 2);
    private Position redSettlerPosition = new Position(4, 3);

    @Before
    public void setUp() {
        game = new EpsilonCiv().getGame();
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
    public void attackerDoesNotAlwaysWin() {
        // This test fails, if it loops infinitely.
        Position redArcherPosition = new Position(10, 10);
        Position blueArcherPosition = new Position(10, 11);

        BaseGame game = (BaseGame) this.game;
        GameWorld gameWorld = game.getGameWorld();

        // TODO: Is a possible infinite loop ok?
        while (true) {
            gameWorld.removeUnit(redArcherPosition);
            gameWorld.placeNewUnit(redArcherPosition, GameConstants.ARCHER, Player.RED);
            gameWorld.removeUnit(blueArcherPosition);
            gameWorld.placeNewUnit(blueArcherPosition, GameConstants.ARCHER, Player.BLUE);
            game.moveUnit(redArcherPosition, blueArcherPosition);
            if (game.getUnitAt(blueArcherPosition).getOwner() == Player.BLUE) {
                return;
            }
        }
    }

    @Test
    public void tripleWinnerWinsRedWins() {
        Position redArcherPosition = new Position(10, 10);
        Position blueArcherPosition = new Position(10, 11);

        BaseGame game = (BaseGame) this.game;
        GameWorld gameWorld = game.getGameWorld();

        while (game.getAttacksWon(Player.RED) < 3) {
            assertNull(game.getWinner());
            gameWorld.removeUnit(redArcherPosition);
            gameWorld.placeNewUnit(redArcherPosition, GameConstants.ARCHER, Player.RED);
            gameWorld.removeUnit(blueArcherPosition);
            gameWorld.placeNewUnit(blueArcherPosition, GameConstants.ARCHER, Player.BLUE);
            game.moveUnit(redArcherPosition, blueArcherPosition);
        }
        assertEquals(Player.RED, game.getWinner());
    }

    @Test
    public void tripleWinnerWinsBlueWins() {
        Position redArcherPosition = new Position(10, 10);
        Position blueArcherPosition = new Position(10, 11);

        BaseGame game = (BaseGame) this.game;
        game.endOfTurn();
        GameWorld gameWorld = game.getGameWorld();

        while (game.getAttacksWon(Player.BLUE) < 3) {
            assertNull(game.getWinner());
            gameWorld.removeUnit(redArcherPosition);
            gameWorld.placeNewUnit(redArcherPosition, GameConstants.ARCHER, Player.RED);
            gameWorld.removeUnit(blueArcherPosition);
            gameWorld.placeNewUnit(blueArcherPosition, GameConstants.ARCHER, Player.BLUE);
            game.moveUnit(blueArcherPosition, redArcherPosition);
        }
        assertEquals(Player.BLUE, game.getWinner());
    }

    @Test
    public void attackerAlwaysWinWithEnoughSupport() {
        // TODO:
    }
}
