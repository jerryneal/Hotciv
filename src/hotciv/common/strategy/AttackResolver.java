package hotciv.common.strategy;

import hotciv.common.AbstractUnit;
import hotciv.common.BaseGame;

/**
 * //TODO: Doc
 *
 * @author Erik
 *         Created: 16-11-12, 15:40
 */
public interface AttackResolver {
    public boolean doesAttackerWin(BaseGame game, AbstractUnit attacker, AbstractUnit defender);
}
