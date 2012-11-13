package hotciv.common.units;

import hotciv.framework.*;
import hotciv.common.UnitImpl;

public class Settler extends UnitImpl {
    /**
     * The constructor for a settler.
     * This constructor is only to be called through a UnitFactory!
     * @param owner The owner of the archer.
     */
	public Settler(Player owner) {
		super(1, owner);
	}

	@Override
	public final String getTypeString() {
		return GameConstants.SETTLER;
	}
	
	@Override
	public int getDefensiveStrength() {
		return 3;
	}

	@Override
	public int getAttackingStrength() {
		return 0;
	}

}
