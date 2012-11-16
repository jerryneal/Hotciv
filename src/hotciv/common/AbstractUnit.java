package hotciv.common;

import hotciv.framework.Player;
import hotciv.framework.Unit;

public abstract class AbstractUnit implements Unit {
	int moveCount;
	
	Player owner;
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
