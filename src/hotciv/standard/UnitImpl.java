package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.Unit;

public abstract class UnitImpl implements Unit {
	int initialMoveCount;
	int currentMoveCount;
	
	Player owner;
	public UnitImpl(int initialMoveCount, Player owner) {
		this.initialMoveCount = initialMoveCount;
		this.currentMoveCount = initialMoveCount;
		
		this.owner = owner;
	}
	@Override
	public Player getOwner() {
		return this.owner;
	}

	@Override
	public int getMoveCount() {
		return currentMoveCount;
	}

}
