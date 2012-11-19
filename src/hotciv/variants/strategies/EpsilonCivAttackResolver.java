package hotciv.variants.strategies;

import hotciv.common.AbstractUnit;
import hotciv.common.BaseGame;
import hotciv.common.GameWorld;
import hotciv.common.strategy.AttackResolver;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;
import hotciv.framework.Tile;

/**
 * //TODO: Doc
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
    public boolean doesAttackerWin(BaseGame game, AbstractUnit attacker, AbstractUnit defender) {
        int attackStrength = getCombinedBattleStrength(game, attacker);
        int defendingStrength = getCombinedBattleStrength(game, defender);

        return attackStrength * dice.getNext() > defendingStrength * dice.getNext();
    }

    private int getCombinedBattleStrength(BaseGame game, AbstractUnit unit) {
        int attackStrength = unit.getAttackingStrength();

        GameWorld gameWorld = game.getGameWorld();
        Position unitPosition = gameWorld.getUnitPosition(unit);
        if (unitPosition == null) {
            throw new RuntimeException("The unit couldn't be found on the map!: " + unit);
        }
        int counter = 0;
        for (Position position : unitPosition.getAroundIterable()) {
            counter++;
            if (counter == 9)
                break;
            AbstractUnit supportingUnit = game.getUnitAt(position);
            if (supportingUnit != null && supportingUnit.getOwner() == unit.getOwner()) {
                attackStrength++;
            }
        }

        return attackStrength * getTerrainFactor(game, unitPosition);
    }

    private int getTerrainFactor(BaseGame game, Position position) {
        int factor = 1;
        Tile tile = game.getTileAt(position);
        String tileType = tile.getTypeString();

        if (GameConstants.FOREST.equals(tileType) || GameConstants.HILLS.equals(tileType)) {
            factor = 2;
        }
        if (game.getCityAt(position) != null) {
            factor = 3;
        }
        return factor;
    }
}
