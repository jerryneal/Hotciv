package hotciv.variants.strategies;

import hotciv.common.BaseGame;
import hotciv.common.GameWorld;
import hotciv.common.strategy.AttackResolver;
import hotciv.framework.*;

/**
 * This strategy implements the EpsilonCiv battle-outcome calculation algorithm, using a random dice to simulate dice rolls.
 *
 * @author Erik
 *         Created: 16-11-12, 15:43
 */
public class EpsilonCivAttackResolver implements AttackResolver {
    private Dice dice;
    private BaseGame game;

    public EpsilonCivAttackResolver(BaseGame game, Dice dice) {
        this.game = game;
        this.dice = dice;
    }

    @Override
    public boolean doesAttackerWin(Unit attacker, Unit defender) {
        int attackStrength = getCombinedBattleStrength(game, attacker, true);
        int defendingStrength = getCombinedBattleStrength(game, defender, false);

        int dice1 = dice.getNext();
        int dice2 = dice.getNext();
        return attackStrength * dice1 > defendingStrength * dice2;
    }

    public static int getCombinedBattleStrength(BaseGame game, Unit unit, boolean attacking) {
        int strength;
        if (attacking) {
            strength = unit.getAttackingStrength();
        } else {
            strength = unit.getDefensiveStrength();
        }

        GameWorld gameWorld = game.getGameWorld();
        Position unitPosition = gameWorld.getUnitPosition(unit);
        if (unitPosition == null) {
            throw new RuntimeException("The unit couldn't be found on the map!: " + unit);
        }

        int unitSupport = getFriendlySupport(game, unit.getOwner(), unitPosition);

        strength += unitSupport;

        return strength * getTerrainFactor(game, unitPosition);
    }

    public static int getFriendlySupport(BaseGame game, Player player, Position unitPosition) {
        int unitSupport = 0;
        for (Position position : unitPosition.getPositionsWithinDistance(1)) {
            Unit supportingUnit = game.getUnitAt(position);
            if (supportingUnit != null && supportingUnit.getOwner() == player) {
                unitSupport++;
            }
        }
        return unitSupport;
    }

    public static int getTerrainFactor(BaseGame game, Position position) {
        Tile tile = game.getTileAt(position);
        String tileType = tile.getTypeString();

        if (game.getCityAt(position) != null) {
            return 3;
        }
        if (GameConstants.FOREST.equals(tileType) || GameConstants.HILLS.equals(tileType)) {
            return 2;
        }
        return 1;
    }
}
