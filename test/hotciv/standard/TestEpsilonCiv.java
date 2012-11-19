package hotciv.standard;

import hotciv.common.BaseGame;
import hotciv.common.GameBuilder;
import hotciv.common.GameWorld;
import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.strategies.FixedDice;
import hotciv.variants.strategies.EpsilonCivAttackResolver;
import hotciv.variants.strategies.TripleWinnerWins;
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
    private FixedDice fixedDice;

    private Position redArcherPosition = new Position(2, 0);
    private Position redCityPosition = new Position(1, 1);
    private Position blueCityPosition = new Position(4, 1);
    private Position blueLegionPosition = new Position(3, 2);
    private Position redSettlerPosition = new Position(4, 3);

    @Before
    public void setUp() {
        fixedDice = new FixedDice(1);
        game = new GameBuilder()
                .setAttackResolverStrategy(new EpsilonCivAttackResolver(fixedDice))
                .setWinnerStrategy(new TripleWinnerWins())
                .build();
    }

    /**
     * Replaces the standard setup game, with a version of EpsilonCiv all dice rolls have a fixed preset value.
     *
     * @param fixedDiceValue
     */
    private void setUpFixedEpsilonCiv(final int fixedDiceValue) {

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
    public void tripleWinnerWinsRedWins() {
        setUpFixedEpsilonCiv(1);

        Position redArcherPosition = new Position(10, 10);
        Position blueArcherPosition = new Position(10, 11);

        BaseGame game = (BaseGame) this.game;
        GameWorld gameWorld = game.getGameWorld();

        // Setting up support for red, so he always wins.
        gameWorld.placeNewUnit(redArcherPosition.getNorth(), GameConstants.ARCHER, Player.RED);

        for (int i = 0; i < 3; i++) {
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
        setUpFixedEpsilonCiv(1);

        game.endOfTurn();

        Position redArcherPosition = new Position(10, 10);
        Position blueArcherPosition = new Position(10, 11);

        BaseGame game = (BaseGame) this.game;
        GameWorld gameWorld = game.getGameWorld();

        // Setting up support for blue, so he always wins.
        gameWorld.placeNewUnit(blueArcherPosition.getNorth(), GameConstants.ARCHER, Player.BLUE);

        for (int i = 0; i < 3; i++) {
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
