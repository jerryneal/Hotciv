package hotciv.common;

import hotciv.framework.Player;
import hotciv.framework.Unit;

/**
 * An abstract unit handles moveCount and owner.
 */
public abstract class AbstractUnit implements Unit {
    int moveCount;

    Player owner;
    private int initialMoveCount;

    /**
     * The default and only constructor for AbstractUnit.
     * It simply stores moveCount and owner for use in the getters.
     * And it also has setters to decrease and reset the movecount.
     *
     * @param initialMoveCount
     * @param owner
     */
    public AbstractUnit(int initialMoveCount, Player owner) {
        this.initialMoveCount = initialMoveCount;
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

    public void decreaseMoveCount(int moved) {
        this.moveCount -= moved;
    }

    public void resetMoveCount() {
        this.moveCount = initialMoveCount;
    }

    @Override
    public void performAction() {
        // Empty
    }
}
