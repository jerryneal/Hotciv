package hotciv.variants.strategies;

import hotciv.common.BaseGame;
import hotciv.common.strategy.AttackResolver;
import hotciv.framework.Unit;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 22-11-12, 15:41
 */
public class AttackerWinsAttackResolver implements AttackResolver {
    public boolean doesAttackerWin(BaseGame game, Unit attacker, Unit defender) {
        return true;
    }
}
