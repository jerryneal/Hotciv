package hotciv.common.units;

import hotciv.framework.*;
import hotciv.common.UnitImpl;

public class Settler extends UnitImpl {
	public Settler(Player owner) {
		super(1, owner);
	}

	@Override
	public String getTypeString() {
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
