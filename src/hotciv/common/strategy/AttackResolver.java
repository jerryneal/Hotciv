package hotciv.common.strategy;

import hotciv.common.BaseGame;
import hotciv.framework.Unit;

/**
 * This strategy allows different algorithms for the outcome of battles.
 *
 * @author Erik
 *         Created: 16-11-12, 15:40
 */
public interface AttackResolver {
    /**
     * Calculates which unit wins a battle.
     *
     * @param game The game, attacker The attacking unit, defender The defending unit.
     * @return True if attacker wins, false if defender wins.
     */
    public boolean doesAttackerWin(BaseGame game, Unit attacker, Unit defender);
}