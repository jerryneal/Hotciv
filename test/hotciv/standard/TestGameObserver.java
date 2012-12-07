package hotciv.standard;

import hotciv.common.BaseGame;
import hotciv.framework.*;
import hotciv.variants.AlphaCiv;
import hotciv.variants.GameLogger;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

/**
 * //TODO: Doc
 *
 * @author Erik
 * Created: 07-12-12, 09:38
 */
public class TestGameObserver {
    private BaseGame game;

    private static Position redArcherPosition = new Position(2, 0);
    private static Position redCityPosition = new Position(1, 1);
    private static Position blueCityPosition = new Position(4, 1);
    private static Position blueLegionPosition = new Position(3, 2);
    private static Position redSettlerPosition = new Position(4, 3);

    @Before
    public void setUp() {
        // Just testing on AlphaCiv. Since i have to test on a concrete game.
        game = (BaseGame) new AlphaCiv().newGame();
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
    public void testMoveObserverMoveSuccessful() {
        final AtomicBoolean redArcherPositionCalled = new AtomicBoolean(false);
        final AtomicBoolean redArcherPositionSouthCalled = new AtomicBoolean(false);
        game.addObserver(new GameObserver() {
            @Override
            public void worldChangedAt(Position position) {
                if (position.equals(redArcherPosition)) {
                    redArcherPositionCalled.set(true);
                }
                if (position.equals(redArcherPosition.getSouth())) {
                    redArcherPositionSouthCalled.set(true);
                }
            }
            public void turnEnds(Player nextPlayer, int age) { }
            public void tileFocusChangedAt(Position position) { }
        });

        // Moving red archer.
        assertEquals(true, game.moveUnit(redArcherPosition, redArcherPosition.getSouth()));
        assertEquals(true, redArcherPositionCalled.get());
        assertEquals(true, redArcherPositionSouthCalled.get());
    }

    @Test
    public void testMoveObserverDoesntCallWhenNotMoved() {
        // Placing a friendly unit so we cannot move there.
        game.getGameWorld().placeNewUnit(redArcherPosition.getSouth(), GameConstants.ARCHER, Player.RED);
        final AtomicBoolean redArcherPositionCalled = new AtomicBoolean(false);
        final AtomicBoolean redArcherPositionSouthCalled = new AtomicBoolean(false);
        game.addObserver(new GameObserver() {
            @Override
            public void worldChangedAt(Position position) {
                if (position.equals(redArcherPosition)) {
                    redArcherPositionCalled.set(true);
                }
                if (position.equals(redArcherPosition.getSouth())) {
                    redArcherPositionSouthCalled.set(true);
                }
            }
            public void turnEnds(Player nextPlayer, int age) { }
            public void tileFocusChangedAt(Position position) { }
        });

        // Moving red archer.
        assertEquals(false, game.moveUnit(redArcherPosition, redArcherPosition.getSouth()));
        assertEquals(false, redArcherPositionCalled.get());
        assertEquals(false, redArcherPositionSouthCalled.get());
    }

    @Test
    public void gameObserverChangeCityProduction() {
        final AtomicBoolean calledOnRedCityPosition = new AtomicBoolean(false);
        game.addObserver(new GameObserver() {
            @Override
            public void worldChangedAt(Position position) {
                if (position.equals(redCityPosition)) {
                    calledOnRedCityPosition.set(true);
                }
            }
            public void turnEnds(Player nextPlayer, int age) { }
            public void tileFocusChangedAt(Position position) { }
        });

        game.changeProductionInCityAt(redCityPosition, GameConstants.LEGION);
        assertEquals(true, calledOnRedCityPosition.get());
    }

    @Test
    public void observerUnitAction() {
        final AtomicBoolean calledOnRedArcherPos = new AtomicBoolean(false);
        game.addObserver(new GameObserver() {
            @Override
            public void worldChangedAt(Position position) {
                if (position.equals(redArcherPosition)) {
                    calledOnRedArcherPos.set(true);
                }
            }
            public void turnEnds(Player nextPlayer, int age) {}
            public void tileFocusChangedAt(Position position) {}
        });

        game.performUnitActionAt(redArcherPosition);
        assertEquals(true, calledOnRedArcherPos.get());
    }
}
