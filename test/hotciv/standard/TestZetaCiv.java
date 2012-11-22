package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.variants.ZetaCiv;
import org.junit.Before;
import org.junit.Test;

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
    public void testWinnerStuff() {
        // TODO:
    }
}
