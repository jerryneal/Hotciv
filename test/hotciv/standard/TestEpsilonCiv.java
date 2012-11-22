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

    @Before
    public void setUp() {
        // Setting up a version of EpsilonCiv where the dice is replaced with a dice that always returns a specified value.
        fixedDice = new FixedDice(1);
        game = new GameBuilder()
                .setAttackResolverStrategy(new EpsilonCivAttackResolver(fixedDice))
                .setWinnerStrategy(new TripleWinnerWins())
                .build();
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
    public void attackerCanLooseBlueLoose() {
        Position redArcherPosition = new Position(10, 10);
        Position blueArcherPosition = new Position(10, 11);

        BaseGame game = (BaseGame) this.game;
        GameWorld gameWorld = game.getGameWorld();

        // Support
        gameWorld.placeNewUnit(redArcherPosition.getNorth(), GameConstants.ARCHER, Player.RED);

        gameWorld.placeNewUnit(redArcherPosition, GameConstants.ARCHER, Player.RED);
        gameWorld.placeNewUnit(blueArcherPosition, GameConstants.ARCHER, Player.BLUE);

        game.moveUnit(redArcherPosition, blueArcherPosition);

        assertEquals(Player.RED, gameWorld.getUnit(blueArcherPosition).getOwner());
    }

    @Test
    public void attackerCanLooseRedLoose() {
        game.endOfTurn();
        Position redArcherPosition = new Position(10, 10);
        Position blueArcherPosition = new Position(10, 11);

        BaseGame game = (BaseGame) this.game;
        GameWorld gameWorld = game.getGameWorld();

        // Support
        gameWorld.placeNewUnit(blueArcherPosition.getNorth(), GameConstants.ARCHER, Player.BLUE);

        gameWorld.placeNewUnit(redArcherPosition, GameConstants.ARCHER, Player.RED);
        gameWorld.placeNewUnit(blueArcherPosition, GameConstants.ARCHER, Player.BLUE);

        game.moveUnit(blueArcherPosition, redArcherPosition);

        assertEquals(Player.BLUE, gameWorld.getUnit(redArcherPosition).getOwner());
    }

    @Test
    public void attackerAlwaysWinWithEnoughSupport() {
        // TODO:
    }
}
