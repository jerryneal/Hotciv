package hotciv.common.strategy;

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
     * @param attacker The attacking unit
     * @param defender The defending unit.
     * @return True if attacker wins, false if defender wins.
     */
    public boolean doesAttackerWin(Unit attacker, Unit defender);
}
