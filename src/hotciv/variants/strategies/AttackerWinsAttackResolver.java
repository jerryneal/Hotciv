package hotciv.variants.strategies;

import hotciv.common.strategy.AttackResolver;
import hotciv.framework.Unit;

/**
 * This attack resolver always returns that the attacker does win.
 *
 * @author Erik
 *         Created: 22-11-12, 15:41
 */
public class AttackerWinsAttackResolver implements AttackResolver {
    public boolean doesAttackerWin(Unit attacker, Unit defender) {
        return true;
    }
}
