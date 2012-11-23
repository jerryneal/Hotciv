package hotciv.common;

import hotciv.framework.Player;
import hotciv.framework.Unit;

/**
 * An abstract unit handles moveCount and owner.
 */
public abstract class AbstractUnit implements Unit {
    int moveCount;

    Player owner;

    /**
     * The default and only constructor for AbstractUnit.
     * It simply stores moveCount and owner for use in the getters.
     *
     * @param initialMoveCount
     * @param owner
     */
    public AbstractUnit(int initialMoveCount, Player owner) {
        this.moveCount = initialMoveCount;
        this.owner = owner;
    }

    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public int getMoveCount() {
        return moveCount;
    }

    @Override
    public void performAction() {
        // Empty
    }
}
