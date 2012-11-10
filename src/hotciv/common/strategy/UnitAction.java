package hotciv.common.strategy;

import hotciv.common.BaseGame;
import hotciv.framework.Position;

/**
 * This strategy makes something happen when a unit has to perform an action.
 *
 * @author: Erik
 * Date: 10-11-12, 00:26
 */
public interface UnitAction {
    /**
     * Is called when the unit at the position is told to perform an action.
     * A precondition is that there is a unit at the position, and the current player is allowed to perform an action on that unit in the current turn.
     * @param game The game instance.
     * @param position The position where the unit is.
     */
    public void performAction(BaseGame game, Position position);
}
