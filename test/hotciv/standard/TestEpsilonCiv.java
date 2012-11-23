package hotciv.standard;

import hotciv.common.BaseGame;
import hotciv.common.CityImpl;
import hotciv.common.GameWorld;
import hotciv.common.StandardTile;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Unit;
import hotciv.standard.strategies.FixedDice;
import hotciv.variants.EpsilonCiv;
import hotciv.variants.strategies.EpsilonCivAttackResolver;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit test class for the EpsilonCiv specification.
 *
 * @author Erik
 *         Created: 16-11-12, 15:50
 */
public class TestEpsilonCiv {
    private BaseGame game;
    private GameWorld gameWorld;
    private FixedDice fixedDice;

    @Before
    public void setUp() {
        // Setting up a version of EpsilonCiv where the dice is replaced with a dice that always returns a specified value.
        fixedDice = new FixedDice(1);
        game = (BaseGame) new EpsilonCiv(fixedDice).newGame();
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
    public void tripleWinnerWinsRedWins() {
        Position redLegionPosition = new Position(10, 10);
        Position blueArcherPosition = new Position(10, 11);

        BaseGame game = (BaseGame) this.game;
        GameWorld gameWorld = game.getGameWorld();


        for (int i = 0; i < 3; i++) {
            assertNull(game.getWinner());
            gameWorld.removeUnit(redLegionPosition);
            gameWorld.placeNewUnit(redLegionPosition, GameConstants.LEGION, Player.RED);
            gameWorld.removeUnit(blueArcherPosition);
            gameWorld.placeNewUnit(blueArcherPosition, GameConstants.ARCHER, Player.BLUE);
            game.moveUnit(redLegionPosition, blueArcherPosition);
        }
        assertEquals(Player.RED, game.getWinner());
    }

    @Test
    public void tripleWinnerWinsBlueWins() {
        game.endOfTurn();

        Position redArcherPosition = new Position(10, 10);
        Position blueLegionPosition = new Position(10, 11);

        BaseGame game = (BaseGame) this.game;
        GameWorld gameWorld = game.getGameWorld();

        // Setting up support for blue, so he always wins.
        gameWorld.placeNewUnit(blueLegionPosition.getNorth(), GameConstants.ARCHER, Player.BLUE);

        for (int i = 0; i < 3; i++) {
            assertNull(game.getWinner());
            gameWorld.removeUnit(redArcherPosition);
            gameWorld.placeNewUnit(redArcherPosition, GameConstants.ARCHER, Player.RED);
            gameWorld.removeUnit(blueLegionPosition);
            gameWorld.placeNewUnit(blueLegionPosition, GameConstants.LEGION, Player.BLUE);
            game.moveUnit(blueLegionPosition, redArcherPosition);
        }
        assertEquals(Player.BLUE, game.getWinner());
    }

    @Test
    public void attackerCanLooseBlueLoose() {
        Position redLegionPosition = new Position(10, 10);
        Position blueArcherPosition = new Position(10, 11);

        BaseGame game = (BaseGame) this.game;
        GameWorld gameWorld = game.getGameWorld();

        // Support
        gameWorld.placeNewUnit(redLegionPosition.getNorth(), GameConstants.ARCHER, Player.RED);

        gameWorld.placeNewUnit(redLegionPosition, GameConstants.LEGION, Player.RED);
        gameWorld.placeNewUnit(blueArcherPosition, GameConstants.ARCHER, Player.BLUE);

        game.moveUnit(redLegionPosition, blueArcherPosition);

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
        gameWorld.placeNewUnit(blueArcherPosition.getEast(), GameConstants.ARCHER, Player.BLUE);

        gameWorld.placeNewUnit(redArcherPosition, GameConstants.ARCHER, Player.RED);
        gameWorld.placeNewUnit(blueArcherPosition, GameConstants.ARCHER, Player.BLUE);

        game.moveUnit(blueArcherPosition, redArcherPosition);

        assertEquals(Player.BLUE, gameWorld.getUnit(redArcherPosition).getOwner());
    }

    @Test
    public void settlerCannotWin() {
        Position redSettlerPosition = new Position(10, 10);
        Position blueLegionPosition = new Position(10, 11);

        gameWorld.placeNewUnit(redSettlerPosition, GameConstants.SETTLER, Player.RED);
        gameWorld.placeNewUnit(blueLegionPosition, GameConstants.LEGION, Player.BLUE);

        BaseGame game = (BaseGame) this.game;
        GameWorld gameWorld = game.getGameWorld();

        // Placing him on a city.
        gameWorld.placeCity(redSettlerPosition, new CityImpl(Player.RED));

        // But still he shouldn't win.
        game.moveUnit(redSettlerPosition, blueLegionPosition);

        assertEquals(Player.BLUE, game.getUnitAt(blueLegionPosition).getOwner());
    }

    @Test
    public void legionBeatsSettler() {
        game.endOfTurn();
        Position redSettlerPosition = new Position(10, 10);
        Position blueLegionPosition = new Position(10, 11);

        gameWorld.placeNewUnit(redSettlerPosition, GameConstants.SETTLER, Player.RED);
        gameWorld.placeNewUnit(blueLegionPosition, GameConstants.LEGION, Player.BLUE);

        BaseGame game = (BaseGame) this.game;
        GameWorld gameWorld = game.getGameWorld();

        // But still he shouldn't win.
        game.moveUnit(blueLegionPosition, redSettlerPosition);

        assertEquals(Player.BLUE, game.getUnitAt(redSettlerPosition).getOwner());
    }

    @Test
    public void testFriendlyUnitSupport() {
        Position redArcherPosition = new Position(10, 10);
        assertEquals(0, EpsilonCivAttackResolver.getFriendlySupport(game, Player.RED, redArcherPosition));
        // Inserting support
        gameWorld.placeNewUnit(redArcherPosition.getNorth(), GameConstants.ARCHER, Player.RED);
        assertEquals(1, EpsilonCivAttackResolver.getFriendlySupport(game, Player.RED, redArcherPosition));
        // More support
        gameWorld.placeNewUnit(redArcherPosition.getSouthEast(), GameConstants.ARCHER, Player.RED);
        gameWorld.placeNewUnit(redArcherPosition.getWest(), GameConstants.ARCHER, Player.RED);
        gameWorld.placeNewUnit(redArcherPosition.getSouth(), GameConstants.ARCHER, Player.RED);
        assertEquals(4, EpsilonCivAttackResolver.getFriendlySupport(game, Player.RED, redArcherPosition));
    }

    @Test
    public void testTerrainFactor() {
        Position terrainTestPosition = new Position(10, 10);
        // Plains have multiplier 1.
        assertEquals(1, EpsilonCivAttackResolver.getTerrainFactor(game, terrainTestPosition));
        // hills have multiplier 2
        gameWorld.placeTile(terrainTestPosition, new StandardTile(terrainTestPosition, GameConstants.HILLS));
        assertEquals(2, EpsilonCivAttackResolver.getTerrainFactor(game, terrainTestPosition));
        // forest have multiplier 2
        gameWorld.placeTile(terrainTestPosition, new StandardTile(terrainTestPosition, GameConstants.FOREST));
        assertEquals(2, EpsilonCivAttackResolver.getTerrainFactor(game, terrainTestPosition));
        // cities have multiplier 3
        gameWorld.placeCity(terrainTestPosition, new CityImpl(Player.RED));
        assertEquals(3, EpsilonCivAttackResolver.getTerrainFactor(game, terrainTestPosition));
    }

    @Test
    public void testCombinedBattleStrengthAttack() {
        Position redArcherPosition = new Position(10, 10);
        gameWorld.placeNewUnit(redArcherPosition, GameConstants.ARCHER, Player.RED);
        Unit redArcher = game.getUnitAt(redArcherPosition);
        assertNotNull(redArcher);
        int baseAttackStrength = redArcher.getAttackingStrength();

        // Without anything we just have the baseAttackStrength
        assertEquals(1 * baseAttackStrength, EpsilonCivAttackResolver.getCombinedBattleStrength(game, redArcher, true));
        // Inserting support
        gameWorld.placeNewUnit(redArcherPosition.getNorth(), GameConstants.ARCHER, Player.RED);
        assertEquals(1 * (baseAttackStrength + 1), EpsilonCivAttackResolver.getCombinedBattleStrength(game, redArcher, true));
        // Placing terrain
        gameWorld.placeTile(redArcherPosition, new StandardTile(redArcherPosition, GameConstants.HILLS));
        assertEquals(2 * (baseAttackStrength + 1), EpsilonCivAttackResolver.getCombinedBattleStrength(game, redArcher, true));
        // More support
        gameWorld.placeNewUnit(redArcherPosition.getSouth(), GameConstants.ARCHER, Player.RED);
        assertEquals(2 * (baseAttackStrength + 2), EpsilonCivAttackResolver.getCombinedBattleStrength(game, redArcher, true));
        // Lastly a city.
        gameWorld.placeCity(redArcherPosition, new CityImpl(Player.RED));
        assertEquals(3 * (baseAttackStrength + 2), EpsilonCivAttackResolver.getCombinedBattleStrength(game, redArcher, true));
    }

    @Test
    public void testCombinedBattleStrengthDefensive() {
        Position redArcherPosition = new Position(10, 10);
        gameWorld.placeNewUnit(redArcherPosition, GameConstants.ARCHER, Player.RED);
        Unit redArcher = game.getUnitAt(redArcherPosition);
        assertNotNull(redArcher);
        int baseDefensiveStrength = redArcher.getDefensiveStrength();

        // Without anything we just have the baseAttackStrength
        assertEquals(1 * baseDefensiveStrength, EpsilonCivAttackResolver.getCombinedBattleStrength(game, redArcher, false));
        // Inserting support
        gameWorld.placeNewUnit(redArcherPosition.getNorth(), GameConstants.ARCHER, Player.RED);
        assertEquals(1 * (baseDefensiveStrength + 1), EpsilonCivAttackResolver.getCombinedBattleStrength(game, redArcher, false));
        // Placing terrain
        gameWorld.placeTile(redArcherPosition, new StandardTile(redArcherPosition, GameConstants.HILLS));
        assertEquals(2 * (baseDefensiveStrength + 1), EpsilonCivAttackResolver.getCombinedBattleStrength(game, redArcher, false));
        // More support
        gameWorld.placeNewUnit(redArcherPosition.getSouth(), GameConstants.ARCHER, Player.RED);
        assertEquals(2 * (baseDefensiveStrength + 2), EpsilonCivAttackResolver.getCombinedBattleStrength(game, redArcher, false));
        // Lastly a city.
        gameWorld.placeCity(redArcherPosition, new CityImpl(Player.RED));
        assertEquals(3 * (baseDefensiveStrength + 2), EpsilonCivAttackResolver.getCombinedBattleStrength(game, redArcher, false));
    }
}
