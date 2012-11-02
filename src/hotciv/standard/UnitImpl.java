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
	
	/**
	 * Is called when the unit has moved a specified distance.
	 * It makes sure that the getMoveCount() returns the correct decreased value.  
	 * @param distance The distance, must be a positive number. 
	 */
	protected void movedUnit(int distance) {
		if (currentMoveCount - distance < 0)
			throw new RuntimeException("The unit has been moved more than its movecount specifies.");
		
		currentMoveCount = currentMoveCount - distance;
	}
	
	/**
	 * Is called when the round ends. 
	 * This method makes sure the getMoveCount() is reset.  
	 */
	protected void roundEnded() {
		this.currentMoveCount = initialMoveCount;
	}
}
