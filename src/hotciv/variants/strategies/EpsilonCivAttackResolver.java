package hotciv.variants.strategies;

import hotciv.common.BaseGame;
import hotciv.common.GameWorld;
import hotciv.common.strategy.AttackResolver;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Tile;
import hotciv.framework.Unit;

/**
 * This strategy implements the EpsilonCiv battle-outcome calculation algorithm, using SecureRandom to simulate die rolls.
 *
 * @author Erik
 *         Created: 16-11-12, 15:43
 */
public class EpsilonCivAttackResolver implements AttackResolver {
    private Dice dice;

    public EpsilonCivAttackResolver(Dice dice) {
        this.dice = dice;
    }

    @Override
    public boolean doesAttackerWin(BaseGame game, Unit attacker, Unit defender) {
        int attackStrength = getCombinedBattleStrength(game, attacker);
        int defendingStrength = getCombinedBattleStrength(game, defender);

        return attackStrength * dice.getNext() > defendingStrength * dice.getNext();
    }

    public static int getCombinedBattleStrength(BaseGame game, Unit unit) {
        int attackStrength = unit.getAttackingStrength();

        GameWorld gameWorld = game.getGameWorld();
        Position unitPosition = gameWorld.getUnitPosition(unit);
        if (unitPosition == null) {
            throw new RuntimeException("The unit couldn't be found on the map!: " + unit);
        }

        int unitSupport = getFriendlySupport(game, unit, unitPosition);

        attackStrength += unitSupport;

        return attackStrength * getTerrainFactor(game, unitPosition);
    }

    public static int getFriendlySupport(BaseGame game, Unit unit, Position unitPosition) {
        int unitSupport = 0;
        for (Position position : unitPosition.getPositionsWithinDistance(1)) {
            Unit supportingUnit = game.getUnitAt(position);
            if (supportingUnit != null && supportingUnit.getOwner() == unit.getOwner()) {
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
